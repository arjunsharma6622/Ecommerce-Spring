package dev.arjunsharma.ecommerce.dto;

public class PaymentRequestDTO {
    private long id;
    private long amount;

    public PaymentRequestDTO(long id, long amount) {
        this.id = id;
        this.amount = amount;
    }

    public PaymentRequestDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
