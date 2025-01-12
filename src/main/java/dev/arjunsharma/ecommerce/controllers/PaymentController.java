package dev.arjunsharma.ecommerce.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import dev.arjunsharma.ecommerce.dto.PaymentRequestDTO;
import dev.arjunsharma.ecommerce.models.Order;
import dev.arjunsharma.ecommerce.services.OrderService;
import dev.arjunsharma.ecommerce.services.PaymentService;
import dev.arjunsharma.ecommerce.services.StripePaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Value("${stripe.webhookSecret}")
    private String stripeWebhookSecret;

    PaymentService paymentService;
    OrderService orderService;

    public PaymentController(PaymentService paymentService, OrderService orderService){
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    @PostMapping("/payment")
    public ResponseEntity<String> createPaymentLink(@RequestBody Order orderBody) throws StripeException {

        // first make a call to orderService to get the order details, like amount, name etc...
        Order order = orderService.getSingleOrder(orderBody.getId());

        String paymentLink;
        paymentLink = paymentService.makePayment(order.getId(), Math.round(order.getAmount())*100);

        return new ResponseEntity<>(paymentLink, HttpStatus.OK);
    }

    @PostMapping("/payment/webhook")
    public void handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signatureHeader
    ){
        try{
            Event event = Webhook.constructEvent(payload, signatureHeader, stripeWebhookSecret);
            System.out.println(event.getType());
        }
        catch (Exception e){
            System.out.println("Error " + e.getMessage());
        }
    }
}

