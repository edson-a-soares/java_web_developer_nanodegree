package com.udacity.pricing;

import com.udacity.pricing.entity.Price;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import com.udacity.pricing.repository.PriceRepository;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableEurekaClient
public class PricingServiceApplication {

    public static void main (String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init (PriceRepository repository){
        return args -> {
            for (int i = 0; i < 20; i++)
                repository.save(new Price());
        };
    }
}
