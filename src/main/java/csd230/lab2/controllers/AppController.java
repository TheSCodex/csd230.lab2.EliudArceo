package csd230.lab2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/home")
    public String home(){
        return "index";
    }


}
