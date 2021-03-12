package com.udacity.boogle.maps.controller;

import com.udacity.boogle.maps.domain.Address;
import org.springframework.web.bind.annotation.GetMapping;
import com.udacity.boogle.maps.domain.MockAddressRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maps")
public class MapsController {

    @GetMapping
    public Address get(@RequestParam Double lat, @RequestParam Double lon) {
        return MockAddressRepository.getRandom();
    }

}
