package com.k12.ter.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k12.ter.DataSource_TER.Service.K12_PolicyAuthService;
import com.k12.ter.DataSource_TER.Service.K12_UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/PolicyAuth/")
public class K12_PolicyAuthController {
    
    @Autowired 
    private K12_PolicyAuthService k12_PolicyAuthService;

    @PostMapping(path = "all")
    public ResponseEntity<Object> getAllAuth(@RequestHeader("Authorization") String authorization){
        //insert here for authentication from request header! value=authorization.
        return k12_PolicyAuthService.getAllAuth();
    }

    @PostMapping(path = "setAuthorized")
    public ResponseEntity<Object> setAuthorized(@RequestHeader("Authorization") String authorization){
        //insert here for authentication from request header! value=authorization.
        String email = "";//email extracted from authHeader
        return k12_PolicyAuthService.setAuthorized(email);
    }

    @PostMapping(path = "hasAuth")
    public ResponseEntity<Object> hasAuth(@RequestHeader("Authorization") String authorization){
        //insert here for authentication from request header! value=authorization.
        String email = "";//email extracted from authHeader
        return k12_PolicyAuthService.hasAuth(email);
    }

}
