package com.udacity.pricing.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "prices")
public class Price {

    @Id
    @Column(name = "vehicleid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    @Column(name = "currency")
    private String currency;

    @Column(name = "price")
    private BigDecimal price;

    public Price() {
        currency = "USD";
    }

    public Price(String currency, BigDecimal price, Long vehicleId) {
        this.price = price;
        this.currency = currency;
        this.vehicleId = vehicleId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    @PrePersist
    public void setRandomPrice() {
        if (price != null)
            return;

        price = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
    }

}
