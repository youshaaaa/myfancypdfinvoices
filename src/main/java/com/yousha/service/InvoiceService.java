package com.yousha.service;

import com.yousha.model.Invoice;
import com.yousha.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class InvoiceService {

    /*
    3 types of injection:
    Constructor -
    Field - One of the drawbacks of field injection, is that it basically hides what dependencies your class needs and you cannot easily instantiate your class outside of a Spring context anymore.
    Setter -  the advantage of setter injection vs field injection, is when you are using your class outside of a  Spring context
     */

    List<Invoice> invoices = new CopyOnWriteArrayList<>();
    //CopyOnWriteArrayList is thread-safe, whereas ArrayList would not be.

//    @Autowired
    private final UserService userService;
    private final String cdnUrl;

    public InvoiceService(UserService userService, @Value("${cdn.url}") String cdnUrl) {
        this.userService = userService;
        this.cdnUrl = cdnUrl;
    }

    @PostConstruct
    public void init() {
        System.out.println("Fetching PDF Template from S3...");
        // TODO download from s3 and save locally
    }

    public List<Invoice> findAll() {
        return invoices;
    }

    public Invoice create(String userId, Integer amount){
        User user = userService.findById(userId);
        if(user == null){
            throw new IllegalStateException();
        }

        //TODO real pdf creation and storing it on network server
        Invoice invoice = new Invoice(userId, amount, cdnUrl+"/images/default/sample.pdf");
        invoices.add(invoice);
        return invoice;
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("Deleting downloaded templates...");
        // TODO actual deletion of PDFs
    }

//    setter injection
//    @Autowired
//    public void setUserService(UserService userService){
//        this.userService = userService;
//    }
}
