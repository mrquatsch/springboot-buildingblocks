package com.mikesterry.springbootbuildingblocks.hello;

import com.mikesterry.springbootbuildingblocks.util.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/helloworld")
    public String helloWorld() {
        return Constants.HELLO_WORLD;
    }

    @GetMapping("/helloworld-bean")
    public UserDetails helloWorldBean() {
        return new UserDetails("Mike", "Sterry", "Burnsville");
    }
}
