package dev.arjunsharma.ecommerce.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService implements PaymentService{
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Override
    public String makePayment(long id, long amount) throws StripeException {

        Stripe.apiKey = stripeApiKey;

        // make price params
        PriceCreateParams params = PriceCreateParams.builder()
                .setCurrency("inr")
                .setUnitAmount(amount)
                .setProductData(
                        PriceCreateParams.ProductData.builder().setName(""+id).build()
                )
                // passing this because we want to have order id at the time of webhook event
                .putMetadata("orderId", String.valueOf(id))
                .build();

        // create price object of stripe
        Price price = Price.create(params);

        // next pass id returned by price to the createPaymentLink
        PaymentLinkCreateParams paymentLinkParams =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setAfterCompletion(
                                PaymentLinkCreateParams.AfterCompletion.builder()
                                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                        .setRedirect(
                                                PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                        .setUrl("https://arjunsharma.dev")
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();

        PaymentLink paymentLink;
        paymentLink = PaymentLink.create(paymentLinkParams);

        return paymentLink.getUrl();
    }

}
