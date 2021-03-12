package com.udacity.vehicles.api;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.net.URI;
import org.junit.Test;
import org.junit.Before;
import java.util.Collections;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.Condition;
import org.springframework.hateoas.MediaTypes;
import com.udacity.vehicles.domain.car.Details;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.json.JacksonTester;
import com.udacity.vehicles.application.service.CarService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import com.udacity.vehicles.infrastructure.client.maps.MapsClient;
import com.udacity.vehicles.infrastructure.client.prices.PriceClient;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

/**
 * Implements testing of the CarController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService cars;

    @MockBean
    private MapsClient mapsClient;

    @MockBean
    private PriceClient priceClient;


    @Autowired
    private JacksonTester<Car> carAsJSON;

    /**
     * Creates pre-requisites for testing, such as an example car.
     */
    @Before
    public void setup() {
        Car car = getCar();
        car.setId(1L);
        given(cars.save(any())).willReturn(car);
        given(cars.findById(any())).willReturn(car);
        given(cars.list()).willReturn(Collections.singletonList(car));
    }

    /**
     * Tests for successful creation of new car in the system
     * @throws Exception when car creation fails in the system
     */
    @Test
    public void createCar() throws Exception {
        Car car = getCar();
        mvc.perform(post(new URI("/cars"))
            .content(carAsJSON.write(car).getJson())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated());
    }

    /**
     * Tests if the read operation appropriately returns a list of vehicles.
     * @throws Exception if the read operation of the vehicle list fails
     */
    @Test
    public void listCars() throws Exception {
        Car car = getCar();

        mvc.perform(
            get("/cars"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
            .andExpect(content().json("{}"));

        verify(cars, times(1)).list();
    }

    /**
     * Tests the read operation for a single car by ID.
     * @throws Exception if the read operation for a single car fails
     */
    @Test
    public void findCar() throws Exception {
        Car car = getCar();
        car.setId(1L);

        mvc.perform(
            get("/cars/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
            .andExpect(content().json("{}"));

        verify(cars, times(1)).findById(1L);
    }

    /**
     * Tests the deletion of a single car by ID.
     * @throws Exception if the delete operation of a vehicle fails
     */
    @Test
    public void deleteCar() throws Exception {
        Car car = getCar();
        car.setId(1L);
        mvc.perform(delete("/cars/1"))
            .andExpect(status().isNoContent());

        verify(cars, times(1)).delete(1L);
    }

    @Test
    public void updateCar() throws Exception {
        Car car = getCar();
        car.setId(1L);

        mvc.perform(
            put(new URI("/cars/1"))
            .content(carAsJSON.write(car).getJson())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
    }

    /**
     * Creates an example Car object for use in testing.
     * @return an example Car object
     */
    private Car getCar() {
        Car car = new Car();
        car.setCondition(Condition.USED);

        Details details = new Details();
        car.setDetails(details);
        details.setBody("sedan");
        details.setMileage(32280);
        details.setModel("Impala");
        details.setModelYear(2018);
        details.setNumberOfDoors(4);
        details.setEngine("3.6L V6");
        details.setProductionYear(2018);
        details.setFuelType("Gasoline");
        details.setExternalColor("white");

        car.setLocation(new Location(40.730610, -73.935242));
        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        details.setManufacturer(manufacturer);

        return car;
    }

}