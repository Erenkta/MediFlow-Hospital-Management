package com.hospital.mediflow.Common.Helpers.Schedulers;

import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import com.hospital.mediflow.Common.Configuration.Properties.SchedulerProperties;
import com.hospital.mediflow.Common.Dto.InvoicePdfProjection;
import com.hospital.mediflow.Common.Events.EventType;
import com.hospital.mediflow.Common.Exceptions.BaseException;
import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Common.Helpers.Notification.NotificationPipeline;
import com.hospital.mediflow.Common.Helpers.Notification.ObjectType;
import com.hospital.mediflow.Common.Helpers.PDFService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class BillingScheduler {
    private final BillingService service;
    private final PDFService pdfService;
    private final NotificationPipeline notificationPipeline;

    @Value("${mediflow.pdf.path}")
    private String pdfSavePath;

    @Scheduled(cron = "${mediflow.scheduler.invoice.pdf}")
    public void generateInvoicePdf(){
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        List<InvoicePdfProjection> pendingInvoices = service.findBillingsByDateRanged(startOfDay,endOfDay);
        pendingInvoices.parallelStream().forEach(invoice ->{
            byte[] pdf = pdfService.generateInvoicePDF(invoice);
            pdfService.saveToFile(pdfSavePath+"/invoice-" + invoice.getId(),pdf);
        });
        String message = String.format("Invoice PDFs created at %s",LocalDateTime.now().toString());
        log.info(message);
    }

    @Scheduled(cron = "${mediflow.scheduler.overdue.payment}")
    @Transactional
    public void markOverduePayments(){
      int markedPaymentCount =  service.markOverduePayments();
      String message = String.format("%d payments marked as OVERDUE",markedPaymentCount);
      log.info(message);

      List<Billing> overduePayments = service.getOverduePayments();
      overduePayments.parallelStream().forEach((billing -> notificationPipeline.processAndNotify(billing, ObjectType.BILLING,EventType.BILLING_OVERDUE)));
    }
}
