package com.k12.ter.DataSource_TER.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.k12.ter.DataSource_TER.model.K12_PolicyAuth;
import com.k12.ter.DataSource_TER.model.K12_User;
import com.k12.ter.DataSource_TER.repository.K12_PolicyAuthRepository;
import com.k12.ter.DataSource_TER.repository.K12_UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class K12_PolicyAuthService {

    @Autowired
    private K12_PolicyAuthRepository k12_PolicyAuthRepository;

    @Autowired
    private UtilityService utilityService;

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> getAllAuth() {
        try{
            List<K12_PolicyAuth> list = k12_PolicyAuthRepository.findAll();
        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","AuthList",list),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> hasAuth(String email) {
        try{
            K12_PolicyAuth user = k12_PolicyAuthRepository.findByEmail(email).orElse(null);
            if(user != null)
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","User_Auth",user),
                HttpStatus.OK);
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
            HttpStatus.UNAUTHORIZED);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> setAuthorized(String email) {
        try{
            K12_PolicyAuth user = k12_PolicyAuthRepository.findByEmail(email).orElse(null);
            if(user == null){
                user = new K12_PolicyAuth();
                user.setEmail(email);
                user.setDate_clicked(new Date());
            }
                k12_PolicyAuthRepository.save(user);
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success"),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
