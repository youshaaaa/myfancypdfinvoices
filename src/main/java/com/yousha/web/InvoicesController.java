package com.yousha.web;

import com.yousha.model.Invoice;
import com.yousha.dto.InvoiceDto;
import com.yousha.service.InvoiceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated //this is for request params
public class InvoicesController {

    private InvoiceService invoiceService;

    public InvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

//    @GetMapping("/")
//    public String home() {
//        return "<b>Here we are</b>";
//    }

    @GetMapping("/invoices")
//    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<Invoice> invoices() {
        return invoiceService.findAll();
    }

    @PostMapping("/invoicesRequestParam")
    public Invoice invoicesRequestParam(@RequestParam("user_id") @NotBlank String userId, @RequestParam("amount") @Min(5) @Max(10) Integer amount) {
        //@PathVariables can also be used instead of @RequestParam, like this: /invoices/{userId}/{amount}
        return invoiceService.create(userId, amount);
    }

    @PostMapping("/invoices")
    public Invoice createInvoice(@RequestBody @Valid InvoiceDto invoiceDto) {
        return invoiceService.create(invoiceDto.getUserId(), invoiceDto.getAmount());
    }
}
