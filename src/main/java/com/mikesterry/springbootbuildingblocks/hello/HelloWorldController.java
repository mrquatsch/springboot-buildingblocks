package com.mikesterry.springbootbuildingblocks.hello;

import com.mikesterry.springbootbuildingblocks.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @GetMapping("/helloworld")
    public String helloWorld() {
        return Constants.HELLO_WORLD;
    }

    @GetMapping("/helloworld-bean")
    public UserDetails helloWorldBean() {
        return new UserDetails("Mike", "Sterry", "Burnsville");
    }

    @GetMapping("/hello-int")
    public String getMessagesInI18nFormat(@RequestHeader(name = "Accept-Language", required = false) String locale) {
        return messageSource.getMessage("label.hello", null, new Locale(locale));
    }

    @GetMapping("/hello-int2")
    public String getMessagesInI18nFormat2() {
        return messageSource.getMessage("label.hello", null, LocaleContextHolder.getLocale());
    }
}
