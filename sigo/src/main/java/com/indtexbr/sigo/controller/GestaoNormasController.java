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

    @PostMapping//(consumes = { "multipart/form-data" })
    public ResponseEntity cadastraNorma(@RequestParam ("file") MultipartFile file,
                                        @RequestParam ("name") String name,
                                        @RequestParam ("obs") String obs) throws IOException {

        File convertedFile = convert(file);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("file",new FileSystemResource(convertedFile));
        body.add("name",name);
        body.add("obs",obs);

        convertedFile.delete();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "multipart/form-data;");

//        Norma body = new Norma();
//        body.setData(file.getBytes());
//        body.setName(name);
//        body.setObs(obs);

        HttpEntity<MultiValueMap<String,Object>> request = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(HTTP_LOCALHOST_3003, request, String.class);
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

    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();

        return convFile;
    }
}
