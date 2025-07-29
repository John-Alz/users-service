package com.micorservice.users.infrastructure.feign.clients;

import com.micorservice.users.infrastructure.feign.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "foodcourt",
        url = "http://localhost:8081/api/v1/restaurant",
        configuration = FeignClientConfig.class
)
public interface RestaurantClient {


    @GetMapping("/{restaurantId}/belongs-to/{ownerId}")
    void isOwnerOfRestaurant(@PathVariable Long restaurantId, @PathVariable Long ownerId);

    @PostMapping("create-employee")
    void createEmployee(@RequestParam("userId") Long userId, @RequestParam("restaurantId") Long restaurantId);


    }
