package com.indtexbr.sigo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/gestaonormas")
@Slf4j
public class GestaoNormasController {

    @Value("${services.genormas.url}")
    public String HTTP_LOCALHOST_3003;

    public static final String PLANEJA = "planeja/";
    public static final String NORMA = "norma/";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity retornaNormas(){
        return restTemplate.getForEntity(HTTP_LOCALHOST_3003, ResponseEntity.class);
    }

    @GetMapping("norma/{id}")
    public ResponseEntity retornaNorma(@PathVariable("id") String id){
        return restTemplate.getForEntity(HTTP_LOCALHOST_3003 + NORMA +id, String.class);
    }

    @PostMapping("norma")
    public ResponseEntity cadastraNorma(@RequestParam ("file") MultipartFile file,
                                        @RequestParam ("name") String name,
                                        @RequestParam ("obs") String obs) throws IOException {

        File convertedFile = convert(file);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("file",new FileSystemResource(convertedFile));
        body.add("name",name);
        body.add("obs",obs);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "multipart/form-data;");

        HttpEntity<MultiValueMap<String,Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity responseEntity = restTemplate.postForEntity(HTTP_LOCALHOST_3003 + NORMA, request, String.class);
        convertedFile.delete();
        return responseEntity;

    }

    @PutMapping("norma")
    public ResponseEntity atualizaNorma(@RequestParam ("id") String id,
                                        @RequestParam (name = "file", required = false) MultipartFile file,
                                        @RequestParam (name = "name", required = false) String name,
                                        @RequestParam (name = "obs", required = false) String obs) throws IOException {
        File convertedFile = null;
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("id", id);
        if(file != null)
        {   convertedFile = convert(file);
            body.add("file",new FileSystemResource(convertedFile));
        }
        body.add("name",name);
        body.add("obs",obs);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "multipart/form-data;");

        HttpEntity<MultiValueMap<String,Object>> request = new HttpEntity<>(body, headers);

        restTemplate.put(HTTP_LOCALHOST_3003 + NORMA, request, String.class);
        if(convertedFile != null)
        {
            convertedFile.delete();
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("norma/{id}")
    public ResponseEntity deletaNorma(@PathVariable("id") String id){
        try{
            restTemplate.delete(HTTP_LOCALHOST_3003 + NORMA + id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @RequestMapping("planeja/{id}")
    public ResponseEntity retornaPlanejamento(@PathVariable("id") Long id){
        return restTemplate.getForEntity(HTTP_LOCALHOST_3003 + PLANEJA +id, String.class);
    }

    @PostMapping("planeja")
    public ResponseEntity cadastraPlanejamento(@RequestBody String body) throws IOException {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json;");

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        log.info("logging GestaoNormasController: " + HTTP_LOCALHOST_3003);
        log.info("payload: "+ body);
        log.info("request: "+ request.getBody());

        ResponseEntity responseEntity = restTemplate.postForEntity(HTTP_LOCALHOST_3003 + PLANEJA, request, String.class);
        return responseEntity;

    }

    @PutMapping("planeja")
    public ResponseEntity atualizaPlanejamento(@RequestBody String body) throws IOException {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json;");

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        restTemplate.put(HTTP_LOCALHOST_3003 + PLANEJA, request, String.class);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("planeja/{id}")
    public ResponseEntity deletaPlanejamento(@PathVariable("id") String id){
        try{
            restTemplate.delete(HTTP_LOCALHOST_3003 + PLANEJA + id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            log.info("Exception sigo: " + e);
            if(e.toString().contains("nomeDoPlan"))
            {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();

        return convFile;
    }
}
