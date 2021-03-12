package com.udacity.pricing.repository;

import com.udacity.pricing.entity.Price;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.udacity.pricing.repository.PriceRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceRepositoryTests {

	@Autowired
	private PriceRepository prices;

	@Test
	public void sampleDataLoaded() {
		Assert.assertTrue(prices.count() > 0);
	}

	@Test
	public void retrieveCurrency() {
		Optional<Price> price = prices.findById(1L);
		Assert.assertSame(price.orElseThrow().getCurrency(), "USD");
	}

	@Test
	public void retrievePrice() {
		Optional<Price> price = prices.findById(1L);
		Assert.assertNotNull(price.orElseThrow().getPrice());
		Assert.assertNotSame(price.get().getPrice(), BigDecimal.valueOf(0));
	}

}
