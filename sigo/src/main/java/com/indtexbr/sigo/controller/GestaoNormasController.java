package com.indtexbr.sigo.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/gestaonormas")
public class GestaoNormasController {

    public static final String HTTP_LOCALHOST_8080 = "http://localhost:8080";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity retornaNormas(){
        return restTemplate.getForEntity(HTTP_LOCALHOST_8080, ResponseEntity.class);
    }

    @GetMapping
    @RequestMapping({"id"})
    public ResponseEntity retornaNorma(@RequestParam String id){
        return restTemplate.getForEntity(HTTP_LOCALHOST_8080+id, ResponseEntity.class);
    }

    @PostMapping
    public ResponseEntity cadastraNorma(@RequestBody String body){
        return restTemplate.postForEntity(HTTP_LOCALHOST_8080, body, ResponseEntity.class);
    }

    @PutMapping
    public ResponseEntity atualizaNorma(@RequestBody String body){
        restTemplate.put(HTTP_LOCALHOST_8080, body);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deletaNorma(@RequestParam String id){
        try{
            restTemplate.delete(HTTP_LOCALHOST_8080 + id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
