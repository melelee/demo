package com.melelee.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "server")
public interface ServerClient {

    @GetMapping("/test")
    String test(@RequestParam(value = "name") String name);
}
