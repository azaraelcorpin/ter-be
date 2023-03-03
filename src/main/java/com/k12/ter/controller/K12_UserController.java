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

import com.k12.ter.DataSource_TER.Service.K12_UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/user/")
public class K12_UserController {
    
    @Autowired 
    private K12_UserService k12_UserService;

    @PostMapping(path = "all")
    public ResponseEntity<Object> getAllUser(@RequestHeader("Authorization") String authorization){
        //insert here for authentication from request header! value=authorization.
        return k12_UserService.getAllUsers();
    }

    @PostMapping(path = "setAsAdmin")
    public ResponseEntity<Object> setAsAdmin(@RequestHeader("Authorization") String authorization,@RequestBody Map<String,Object> params){
        //insert here for authentication from request header! value=authorization.
        return k12_UserService.setAsAdmin(params);
    }

    @PostMapping(path = "setAsDean")
    public ResponseEntity<Object> SeatAsDean(@RequestHeader("Authorization") String authorization,@RequestBody Map<String,Object> params){
        //insert here for authentication from request header! value=authorization.
        return k12_UserService.setAsDean(params);
    }

    @PostMapping(path = "setAsChairperson")
    public ResponseEntity<Object> setAsChairPerson(@RequestHeader("Authorization") String authorization,@RequestBody Map<String,Object> params){
        //insert here for authentication from request header! value=authorization.
        return k12_UserService.setAsChairPerson(params);
    }

    @PostMapping(path = "removeUser")
    public ResponseEntity<Object> removeUser(@RequestHeader("Authorization") String authorization,@RequestBody Map<String,Object> params){
        //insert here for authentication from request header! value=authorization.
        return k12_UserService.removeUser(params);
    }
}
