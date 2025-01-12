package dev.arjunsharma.ecommerce.services;

import com.stripe.exception.StripeException;

public interface PaymentService {
    String makePayment(long id, long amount) throws StripeException;
}
