package com.indtexbr.sigo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
        ResponseEntity responseEntity = restTemplate.postForEntity(HTTP_LOCALHOST_3003, request, String.class);
        convertedFile.delete();
        return responseEntity;

    }

    @PutMapping
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

        restTemplate.put(HTTP_LOCALHOST_3003, request, String.class);
        if(convertedFile != null)
        {
            convertedFile.delete();
        }

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

    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();

        return convFile;
    }
}
