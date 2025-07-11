package com.micorservice.users.infrastructure.feign.clients;

import com.micorservice.users.infrastructure.feign.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "foodcourt",
        url = "http://localhost:8081/api/v1/restaurant",
        configuration = FeignClientConfig.class
)
public interface RestaurantClient {

    @GetMapping("/{restaurantId}/belongs-to/{ownerId}")
    void isOwnerOfRestaurant(@PathVariable Long restaurantId, @PathVariable Long ownerId);

}
