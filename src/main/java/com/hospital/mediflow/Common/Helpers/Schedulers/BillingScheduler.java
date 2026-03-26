package com.hospital.mediflow.Common.Helpers.Schedulers;

import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Common.Dto.InvoicePdfProjection;
import com.hospital.mediflow.Common.Exceptions.BaseException;
import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Common.Helpers.PDFService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BillingScheduler {
    private final BillingDataService dataService;
    private final PDFService pdfService;

    @Value("${mediflow.pdf.path}")
    private String pdfSavePath;

    @Scheduled(cron = "0 0 02 * * * ")
    public void generateInvoicePdf(){
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        List<InvoicePdfProjection> pendingInvoices = dataService.findBillingsByDateRanged(startOfDay,endOfDay);
        pendingInvoices.parallelStream().forEach(invoice ->{
            byte[] pdf = pdfService.generateInvoicePDF(invoice);
            saveToFile(invoice.getId(),pdf);
        });
    }

    private void saveToFile(Long billingId, byte[] pdf) {
        try {
            Files.write(
                    Path.of(pdfSavePath+"/invoice-" + billingId + ".pdf"),
                    pdf
            );
        } catch (IOException e) {
            throw new BaseException(e.getMessage(), ErrorCode.IO_ERROR);
        }
    }
}
