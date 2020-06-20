package de.fim.wad.eventplanning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController

public class EventplanningApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventplanningApplication.class, args);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam(value = "name", defaultValue = "Depp") String name){
        return String.format("Hello %s!", name);
    }
}
