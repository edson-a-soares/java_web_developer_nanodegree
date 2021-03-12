package com.udacity.vehicles.infrastructure.client.maps;

import org.slf4j.Logger;
import java.util.Objects;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import com.udacity.vehicles.domain.Location;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Implements a class to interface with the Maps Client for location data.
 */
@Component
public class MapsClient {

    private static final Logger log = LoggerFactory.getLogger(MapsClient.class);

    private final WebClient client;
    private final ModelMapper mapper;

    public MapsClient(WebClient maps, ModelMapper mapper) {
        this.client = maps;
        this.mapper = mapper;
    }

    /**
     * Gets an address from the Maps client, given latitude and longitude.
     * @param location An object containing "lat" and "lon" of location
     * @return An updated location including street, city, state and zip,
     *   or an exception message noting the Maps service is down
     */
    public Location getAddress(Location location) {
        try {
            Address address = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                        .path("/maps/")
                        .queryParam("lat", location.getLat())
                        .queryParam("lon", location.getLon())
                        .build()
                    )
                    .retrieve()
                    .bodyToMono(Address.class)
                    .block();

            mapper.map(Objects.requireNonNull(address), location);
            return location;

        } catch (Exception e) {
            log.warn("Map service is down");
            return location;
        }
    }
}
