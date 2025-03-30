package com.ileven.tareas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("hello")
    public Map<String, Object> gretting() {
        List<Map<String, Object>> tareas = List.of(
                Map.of(
                        "id", 1,
                        "titulo", "Aprender Spring Boot",
                        "descripcion", "Estudiar la documentación y crear una API",
                        "completado", false),
                Map.of(
                        "id", 2,
                        "titulo", "Construir una aplicación",
                        "descripcion", "Desarrollar una aplicación web usando Spring Boot",
                        "completado", true));

        return Map.of(
                "tareas", tareas,
                "mensaje", "Hola desde Spring Boot",
                "status", 200);
    }

}
