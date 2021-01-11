package com.indtexbr.sigo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/gestaonormas")
public class GestaoNormasController {

    public static final String HTTP_LOCALHOST_3003 = "http://localhost:3003/gestaonormas";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity retornaNormas(){
        return restTemplate.getForEntity(HTTP_LOCALHOST_3003, ResponseEntity.class);
    }

    @GetMapping
    @RequestMapping({"id"})
    public ResponseEntity retornaNorma(@RequestParam String id){
        return restTemplate.getForEntity(HTTP_LOCALHOST_3003 +id, ResponseEntity.class);
    }

    @PostMapping
    public ResponseEntity cadastraNorma(@RequestBody String body){
        return restTemplate.postForEntity(HTTP_LOCALHOST_3003, body, ResponseEntity.class);
    }

    @PutMapping
    public ResponseEntity atualizaNorma(@RequestBody String body){
        restTemplate.put(HTTP_LOCALHOST_3003, body);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deletaNorma(@RequestParam String id){
        try{
            restTemplate.delete(HTTP_LOCALHOST_3003 + id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
