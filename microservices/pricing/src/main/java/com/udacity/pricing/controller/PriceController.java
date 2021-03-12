package com.udacity.pricing.controller;

import java.util.Optional;
import com.udacity.pricing.entity.Price;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import com.udacity.pricing.repository.PriceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implements a REST-based controller for the pricing service.
 */
@RestController
@RequestMapping("/services/price")
public class PriceController {

    private final PriceRepository prices;

    PriceController(PriceRepository repository) {
        prices = repository;
    }

    /**
     * Gets the price for a requested vehicle.
     * @param vehicleId ID number of the vehicle for which the price is requested
     * @return price of the vehicle, or error that it was not found.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Price get(@RequestParam Long vehicleId) {
        Optional<Price> price = prices.findById(vehicleId);
        if (price.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Price Not Found");

        return price.get();
    }

}
