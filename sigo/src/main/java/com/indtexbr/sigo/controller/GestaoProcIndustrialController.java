package com.indtexbr.sigo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/gestaoprocind")
public class GestaoProcIndustrialController {

    @Value("${services.gestaoprocind.url}")
    public String HTTP_LOCALHOST_3004;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity retornaProdutos(){
        return restTemplate.getForEntity(HTTP_LOCALHOST_3004, ResponseEntity.class);
    }

    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity retornaProduto(@PathVariable("id") String id){
        return restTemplate.getForEntity(HTTP_LOCALHOST_3004 +id, ResponseEntity.class);
    }

    @PostMapping
    public ResponseEntity<String> cadastraProduto(@RequestBody String body){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<String>(body, headers);
        return restTemplate.postForEntity(HTTP_LOCALHOST_3004, request, String.class);
    }

    @PutMapping
    public ResponseEntity atualizaProduto(@RequestBody String body){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<String>(body, headers);
        restTemplate.put(HTTP_LOCALHOST_3004, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletaProduto(@PathVariable("id") Long id){
        try{
            restTemplate.delete(HTTP_LOCALHOST_3004 + id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
