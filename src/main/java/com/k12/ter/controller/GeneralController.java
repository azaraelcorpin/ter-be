package com.k12.ter.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k12.ter.DataSource_TER.Service.DbGeneralService;
import com.k12.ter.DataSource_TER.Service.UtilityService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/")
public class GeneralController {

    @Autowired 
    private DbGeneralService dbGeneralService;

    @PostMapping(path = "getCORByEmail")
    public ResponseEntity<Object> TestCOR(@RequestBody Map<String,Object> params){
        //insert here for authentication from request header! value=authorization.
        return dbGeneralService.getCORByEmail(params);
    }
}
