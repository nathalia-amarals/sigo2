package com.indtexbr.sigo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consultassessor")
public class ConsultoriasAssessoriasController {

    public static final String HTTP_LOCALHOST_8080 = "http://localhost:8080";
    @Autowired
    RestTemplate restTemplate;

    @GetMapping
    @RequestMapping("/empresa/{id}")
    public ResponseEntity retornaEmpresa(@RequestParam String id){
        return restTemplate.getForEntity(HTTP_LOCALHOST_8080 + id, ResponseEntity.class);
    }

    @PostMapping
    @RequestMapping("/empresa")
    public ResponseEntity cadastraEmpresa(@RequestBody String body){
        return restTemplate.postForEntity(HTTP_LOCALHOST_8080, body, ResponseEntity.class);
    }

    @PutMapping
    @RequestMapping("/empresa")
    public ResponseEntity atualizaEmpresa(@RequestBody String body){
        restTemplate.put(HTTP_LOCALHOST_8080, body);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    @RequestMapping("/empresa")
    public ResponseEntity deletaEmpresa(@RequestParam String id){
        try{
            restTemplate.delete(HTTP_LOCALHOST_8080 + id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @RequestMapping("/contrato/{id}")
    public ResponseEntity retornaContrato(@RequestParam String id){
        return restTemplate.getForEntity(HTTP_LOCALHOST_8080+id, ResponseEntity.class);
    }

    @PostMapping
    @RequestMapping("/contrato")
    public ResponseEntity cadastraContrato(@RequestBody String body){
        return restTemplate.postForEntity(HTTP_LOCALHOST_8080, body, ResponseEntity.class);
    }

    @PutMapping
    @RequestMapping("/contrato")
    public ResponseEntity atualizaContrato(@RequestBody String body){
        restTemplate.put(HTTP_LOCALHOST_8080, body);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    @RequestMapping("/contrato")
    public ResponseEntity deletaContrato(@RequestParam String id){
        try{
            restTemplate.delete(HTTP_LOCALHOST_8080 + id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
