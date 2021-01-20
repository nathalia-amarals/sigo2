package com.indtexbr.sigo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consultassessor")
public class ConsultoriasAssessoriasController {

    public static final String HTTP_LOCALHOST_3002 = "http://localhost:3002/consultassesso/";
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/empresa/{id}")
    public ResponseEntity retornaEmpresa(@RequestParam String id){
        return restTemplate.getForEntity(HTTP_LOCALHOST_3002 + id, ResponseEntity.class);
    }

    @PostMapping("/empresa")
    public ResponseEntity cadastraEmpresa(@RequestBody String body){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<String>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(HTTP_LOCALHOST_3002 + "empresa", request, String.class);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/empresa")
    public ResponseEntity atualizaEmpresa(@RequestBody String body){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<String>(body, headers);
        try {
            restTemplate.put(HTTP_LOCALHOST_3002 + "empresa/", request);
            return new ResponseEntity("Empresa Atualizada",HttpStatus.OK);
        } catch (Exception e){

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/empresa/{id}")
    public ResponseEntity deletaEmpresa(@PathVariable Long id){
        try{
            restTemplate.delete(HTTP_LOCALHOST_3002 + "empresa/" + id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/contrato/{id}")
    public ResponseEntity retornaContrato(@PathVariable String id){
        return restTemplate.getForEntity(HTTP_LOCALHOST_3002 + "contrato/" +id, String.class);
    }

    @PostMapping("/contrato")
    public ResponseEntity cadastraContrato(@RequestBody String body){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<String>(body, headers);
        return restTemplate.postForEntity(HTTP_LOCALHOST_3002 + "contrato/", request, String.class);
    }

    @PutMapping("/contrato")
    public ResponseEntity atualizaContrato(@RequestBody String body){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<String>(body, headers);
        restTemplate.put(HTTP_LOCALHOST_3002 + "contrato/", request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/contrato/{id}")
    public ResponseEntity deletaContrato(@PathVariable String id){
        try{
            restTemplate.delete(HTTP_LOCALHOST_3002 + "contrato/" + id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
