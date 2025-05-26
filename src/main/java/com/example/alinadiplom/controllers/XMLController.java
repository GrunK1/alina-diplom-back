package com.example.alinadiplom.controllers;

import com.example.alinadiplom.DTO.RouteListDTO;
import com.example.alinadiplom.model.RouteList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.ws.Response;
import org.apache.catalina.connector.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/xml")
public class XMLController {
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE // Явное указание типа контента
    )
    public ResponseEntity<String> uploadFile(
            @RequestPart("file") MultipartFile file) throws IOException, JAXBException
    {
        System.out.println(file);
        System.out.println("size: "+file.getSize());
        System.out.println("content_type: "+file.getContentType());
        System.out.println("filename: "+file.getName());
        System.out.println("orig_filename: "+file.getOriginalFilename());
        System.out.println("input_stream: "+file.getInputStream());
        System.out.println("resource: "+file.getResource());
        if (!Objects.equals(file.getContentType(), "text/xml")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("content type "+file.getContentType()+" not supported. try text/xml");
        }
//        System.out.println("bytes: "+ Arrays.toString(file.getBytes()));
        // Правильное чтение XML
        JAXBContext context = JAXBContext.newInstance(RouteListDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        RouteListDTO routeList = (RouteListDTO) unmarshaller.unmarshal(file.getInputStream());
        System.out.println("route list DTO: "+routeList);

        // логика из enriched

        return ResponseEntity.ok("Файл получен. Размер: " + file.getSize());
    }
}
