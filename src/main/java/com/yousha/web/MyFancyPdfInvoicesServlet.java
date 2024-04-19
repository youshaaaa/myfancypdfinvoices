package com.yousha.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yousha.context.ApplicationConfiguration;
import com.yousha.model.Invoice;
import com.yousha.service.InvoiceService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class MyFancyPdfInvoicesServlet extends HttpServlet {

    private InvoiceService invoiceService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        ctx.registerShutdownHook();
        
        this.invoiceService = ctx.getBean(InvoiceService.class);
        this.objectMapper = ctx.getBean(ObjectMapper.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getRequestURI().equalsIgnoreCase("/")) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().print(
                    "<html>\n" +
                            "<body>\n" +
                            "<h1>Hello World</h1>\n" +
                            "<p>This is my very first, embedded Tomcat, HTML Page!</p>\n" +
                            "</body>\n" +
                            "</html>");
        } else if (request.getRequestURI().equalsIgnoreCase("/invoices")) {
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().print(objectMapper.writeValueAsString(invoiceService.findAll()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getRequestURI().equalsIgnoreCase("/invoices")) {
            String userId = req.getParameter("user_id");
            Integer amount = Integer.parseInt(req.getParameter("amount"));
            Invoice invoice = invoiceService.create(userId, amount);
            String json = objectMapper.writeValueAsString(invoice);
            resp.getWriter().print(json);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
