package com.hospital.mediflow.Common.Helpers;

import com.hospital.mediflow.Common.Dto.InvoicePdfProjection;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;


@Service
@RequiredArgsConstructor
public class PDFService {

    private final TemplateEngine templateEngine;

    public byte[] generateInvoicePDF(InvoicePdfProjection billing) {

        Context context = new Context();
        context.setVariable("customerName", billing.getCustomerName());
        context.setVariable("billingDate", billing.getBillingDate());
        context.setVariable("appointmentDate", billing.getAppointmentDate());
        context.setVariable("appointmentDescription", billing.getAppointmentDescription());
        context.setVariable("amount", billing.getAmount());

        String html = templateEngine.process("invoice", context);

        ByteArrayOutputStream target = new ByteArrayOutputStream();

        ConverterProperties props = new ConverterProperties();
        props.setCharset("UTF-8");

        HtmlConverter.convertToPdf(html, target, props);

        return target.toByteArray();
    }
}
