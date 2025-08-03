package com.example.hotel.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/staff")
public class staffController {

    @GetMapping
    public ResponseEntity<List<String>> getStaff(){
        List<String> staffList = Arrays.asList("Staff1","Staff2","Staff3","Staff4");
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }

}
