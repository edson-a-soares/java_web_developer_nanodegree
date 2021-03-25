package com.udacity.vehicles.application.service;

import java.util.List;
import java.util.Optional;
import com.udacity.vehicles.domain.car.Car;
import org.springframework.stereotype.Service;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.infrastructure.client.maps.MapsClient;
import com.udacity.vehicles.infrastructure.client.prices.PriceClient;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final MapsClient maps;
    private final PriceClient prices;
    private final CarRepository repository;

    public CarService(CarRepository repository, MapsClient mapsClient, PriceClient priceClient) {
        this.maps = mapsClient;
        this.prices = priceClient;
        this.repository = repository;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        Optional<Car> carOptional = repository.findById(id);
        if (carOptional.isEmpty())
            throw new CarNotFoundException();

        Car car = carOptional.get();

        car.setPrice(prices.getPrice(car.getId()));
        car.setLocation(maps.getAddress(car.getLocation()));

        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {

            return repository.findById(car.getId())
                .map(carToBeUpdated -> {
                    carToBeUpdated.setDetails(car.getDetails());
                    carToBeUpdated.setLocation(car.getLocation());
                    carToBeUpdated.setCondition(car.getCondition());

                    return repository.save(carToBeUpdated);
                }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Car car = findById(id);
        repository.delete(car);
    }

}
