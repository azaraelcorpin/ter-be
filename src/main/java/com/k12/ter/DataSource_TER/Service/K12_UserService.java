package com.k12.ter.DataSource_TER.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.k12.ter.DataSource_TER.model.K12_User;
import com.k12.ter.DataSource_TER.repository.K12_UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class K12_UserService {

    @Autowired
    private K12_UserRepository k12_UserRepository;

    @Autowired
    private UtilityService utilityService;

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> getAllUsers() {
        try{
            List<K12_User> list = k12_UserRepository.findAll();
        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","USERS",list),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> setAsAdmin(@RequestBody Map<String,Object> params) {
        try{
            String email = params.get("email").toString();
            Optional<K12_User> optionalUser = k12_UserRepository.findByEmail(email);
            K12_User user = optionalUser.orElse(null);
            if(user != null){
                user.setPosition(K12_User.POSITION_ADMIN);
            }else{
                user = new K12_User();
                user.setEmail(email);
                user.setPosition(K12_User.POSITION_ADMIN);
            }
                k12_UserRepository.save(user);
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success"),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> setAsDean(@RequestBody Map<String,Object> params) {
        try{
            String email = params.get("email").toString();
            Optional<K12_User> optionalUser = k12_UserRepository.findByEmail(email);
            K12_User user = optionalUser.orElse(null);
            if(user != null){
                user.setPosition(K12_User.POSITION_DEAN);
                k12_UserRepository.deleteByPositionAndIdNot(K12_User.POSITION_DEAN,user.getId());
            }else{
                user = new K12_User();
                user.setEmail(email);
                user.setPosition(K12_User.POSITION_DEAN);
                k12_UserRepository.deleteByPosition(K12_User.POSITION_DEAN);
            }                
                k12_UserRepository.save(user);
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success"),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> setAsChairPerson(@RequestBody Map<String,Object> params) {
        try{
            String email = params.get("email").toString();
            Optional<K12_User> optionalUser = k12_UserRepository.findByEmail(email);
            K12_User user = optionalUser.orElse(null);
            if(user != null){
                user.setPosition(K12_User.POSITION_CHAIRPERSON);
                k12_UserRepository.deleteByPositionAndIdNot(K12_User.POSITION_CHAIRPERSON,user.getId());
            }else{
                user = new K12_User();
                user.setEmail(email);
                user.setPosition(K12_User.POSITION_CHAIRPERSON);
                k12_UserRepository.deleteByPosition(K12_User.POSITION_CHAIRPERSON);
            }
                k12_UserRepository.save(user);
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success"),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> removeUser(@RequestBody Map<String,Object> params) {
        try{
            String email = params.get("email").toString();
            k12_UserRepository.deleteByEmail(email);                
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success"),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
