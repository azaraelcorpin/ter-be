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

import com.k12.ter.DataSource_TER.Service.K12_QuestionnaireService;
import com.k12.ter.DataSource_TER.Service.K12_UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/questionnaire/")
public class K12_QuestionnaireController {
    
    @Autowired 
    private K12_QuestionnaireService k12_QuestionnaireService;

    @PostMapping(path = "all")
    public ResponseEntity<Object> getAllQuestionnaire(@RequestHeader("Authorization") String authorization){
        //insert here for authentication from request header! value=authorization.
        return k12_QuestionnaireService.getAll();
    }

    @PostMapping(path = "create")
    public ResponseEntity<Object> createQuestionnaire(@RequestHeader("Authorization") String authorization,@RequestBody Map<String,Object> params){
        //insert here for authentication from request header! value=authorization.
        return k12_QuestionnaireService.createQuestionnaire(params);
    }

    @PostMapping(path = "update")
    public ResponseEntity<Object> UpdateQuestionnaire(@RequestHeader("Authorization") String authorization,@RequestBody Map<String,Object> params){
        //insert here for authentication from request header! value=authorization.
        return k12_QuestionnaireService.UpdateQuestionnaire(params);
    }

    @PostMapping(path = "getItemsTypeAndSy")
    public ResponseEntity<Object> getQuestionnaireItemsByEvalTypeAndSy(@RequestBody Map<String,Object> params){
        //insert here for authentication from request header! value=authorization.
        return k12_QuestionnaireService.getQuestionnaireItemsByEvalTypeAndSy(params);
    }
}
