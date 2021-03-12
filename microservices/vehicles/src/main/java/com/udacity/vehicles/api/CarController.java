package com.udacity.vehicles.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import java.util.stream.Collectors;
import java.net.URISyntaxException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import com.udacity.vehicles.domain.car.Car;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.udacity.vehicles.application.service.CarService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.udacity.vehicles.application.service.CarNotFoundException;

/**
 * Implements a REST-based controller for the Vehicles API.
 */
@RestController
@RequestMapping("/cars")
class CarController {

    private final CarService cars;
    private final CarResourceAssembler assembler;

    CarController(CarService carService, CarResourceAssembler assembler) {
        this.cars = carService;
        this.assembler = assembler;
    }

    /**
     * Creates a list to store any vehicles.
     * @return list of vehicles
     */
    @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    Resources<Resource<Car>> list() {
        List<Resource<Car>> resources =
            cars.list()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(resources, linkTo(
                methodOn(CarController.class).list())
                    .withSelfRel()
        );
    }

    /**
     * Gets information of a specific car by ID.
     * @param id the id number of the given vehicle
     * @return all information for the requested vehicle
     */
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiResponses(value =
        {
            @ApiResponse(code = 404, message = "The resource is not found."),
        })
    Resource<Car> get(@PathVariable Long id) {
        Car car = cars.findById(id);
        return assembler.toResource(car);
    }

    /**
     * Posts information to create a new vehicle in the system.
     * @param car A new vehicle to add to the system.
     * @return response that the new vehicle was added to the system
     * @throws URISyntaxException if the request contains invalid fields or syntax
     */
    @PostMapping
    ResponseEntity<?> post(@Valid @RequestBody Car car) throws URISyntaxException {
        Car savedCar = cars.save(car);
        Resource<Car> resource = assembler.toResource(savedCar);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    /**
     * Updates the information of a vehicle in the system.
     * @param id The ID number for which to update vehicle information.
     * @param car The updated information about the related vehicle.
     * @return response that the vehicle was updated in the system
     */
    @PutMapping("/{id}")
    ResponseEntity<?> put(@PathVariable Long id, @Valid @RequestBody Car car) {
        car.setId(id);
        Car updatedCar = cars.save(car);
        Resource<Car> resource = assembler.toResource(updatedCar);
        return ResponseEntity.ok(resource);
    }

    /**
     * Removes a vehicle from the system.
     * @param id The ID number of the vehicle to remove.
     * @return response that the related vehicle is no longer in the system
     */
    @DeleteMapping("/{id}")
    @ApiResponses(value =
        {
            @ApiResponse(code = 404, message = "The resource is not found."),
            @ApiResponse(code = 204, message = "The resource was successfully deleted.")

        })
    ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cars.delete(id);

        } catch (CarNotFoundException exception) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(204).build();
    }

}
