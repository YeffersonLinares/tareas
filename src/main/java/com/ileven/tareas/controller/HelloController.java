package com.ileven.tareas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("hello")
    public String gretting() {
        return "Hello wordl 223";
    }

}
