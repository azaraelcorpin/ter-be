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

import com.k12.ter.DataSource_TER.Service.K12_TerSchedService;
import com.k12.ter.DataSource_TER.Service.K12_UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/TerSched/")
public class K12_TerSchedController {
    
    @Autowired 
    private K12_TerSchedService k12_TerSchedService;

    @PostMapping(path = "get")
    public ResponseEntity<Object> get(@RequestHeader("Authorization") String authorization){
        //insert here for authentication from request header! value=authorization.
        return k12_TerSchedService.getSched();
    }

    @PostMapping(path = "setSched")
    public ResponseEntity<Object> setSched(@RequestHeader("Authorization") String authorization,@RequestBody Map<String,Object> params){
        //insert here for authentication from request header! value=authorization.
        return k12_TerSchedService.setTerSched(params);
    }

    @PostMapping(path = "setClosed")
    public ResponseEntity<Object> SetClosed(@RequestHeader("Authorization") String authorization){
        //insert here for authentication from request header! value=authorization.
        return k12_TerSchedService.setClosed();
    }

    @PostMapping(path = "setOpen")
    public ResponseEntity<Object> setOpen(@RequestHeader("Authorization") String authorization){
        //insert here for authentication from request header! value=authorization.
        return k12_TerSchedService.setOpen();
    }
}
