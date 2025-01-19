package com.caner.thycase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private static final String REDIRECT = "redirect:";
    private static final String SWAGGER_HOME_PAGE = "swagger-ui.html";

    @RequestMapping(value = {"/", ""})
    public String swaggerHomePage() {
        return REDIRECT + SWAGGER_HOME_PAGE;
    }
}