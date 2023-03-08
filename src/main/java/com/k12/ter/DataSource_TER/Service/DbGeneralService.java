package com.k12.ter.DataSource_TER.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;
import com.k12.ter.DataSource_TER.model.K12_TerSched;
import com.k12.ter.DataSource_TER.model.K12_User;
import com.k12.ter.DataSource_TER.repository.K12_TerSchedRepository;
import com.k12.ter.DataSource_TER.repository.K12_UserRepository;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DbGeneralService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private K12_TerSchedRepository k12_TerSchedRepository;

    @Autowired
    private K12_UserRepository k12_UserRepository;

    
    public ResponseEntity<Object> getCORByEmail(@RequestBody Map<String,Object> params) {
        try{
            Optional<K12_TerSched> optSched = k12_TerSchedRepository.findTopByOrderByIdDesc();
            K12_TerSched sched = optSched.orElse(null);
            String sy = sched.getSy();
            String email = params.get("email").toString();
        //get sy from ter_schedule table
        String sql = "SELECT trim(r.subjcode)as subjCode, trim(r.section)as section, trim(s.days)as days,trim(s.time)as time,trim(s.room)as room, f.facultyid,f.fullname as facultyName, ter.hasevaluation(r.subjcode,r.section,r.sy,r.sem,r.email) "
        + "FROM ter.k12_registration r, ter.k12_semsubject s, ter.k12_faculty f "
        + "WHERE r.email = '"+email+"' "
        + "and r.sy = '"+sy+"'"
        + "and r.subjcode =s.subjcode and r.section =s.section and r.sy =s.sy "
        + "and s.facultyid = f.facultyid ";        
            List<Map<String,Object>> items = jdbcTemplate.queryForList(sql);
        if(!items.isEmpty())
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","COR",items),
                HttpStatus.OK);

        return new ResponseEntity<Object>(utilityService.renderJsonResponse("404", "Not Found"),
        HttpStatus.NOT_FOUND);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> checkAccount(@RequestBody Map<String,Object> params) {
        try{
            String email = params.get("email").toString();
            //check if AdminUser or Head
            K12_User user = k12_UserRepository.findByEmail(email).orElse(null);
            if(user != null){
                JSONObject _user = new JSONObject();
                _user.put("email", email);
                _user.put("position", user.getPosition());
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","user",_user),
                HttpStatus.OK);
            }
            //check if faculty
            String sql = "SELECT x.* FROM ter.k12_faculty x WHERE emailaddress = '"+email+"'";  
            List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
            if(!rows.isEmpty()){
                JSONObject _user = new JSONObject();
                _user.put("email", email);
                _user.put("position", "faculty");
                
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","user",_user),
                HttpStatus.OK);
            }
            //check if enrolled student
            K12_TerSched sched = k12_TerSchedRepository.findTopByOrderByIdDesc().orElse(null);
            sql = "SELECT x.email  FROM ter.k12_registration x WHERE (sy = '"+sched.getSy()+"') AND (email = '"+email+"') limit 1";
            rows = jdbcTemplate.queryForList(sql);
            if(!rows.isEmpty()){
                JSONObject _user = new JSONObject();
                _user.put("email", email);
                _user.put("position", "student");
                
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","user",_user),
                HttpStatus.OK);
            }
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("404", "Not Found"),
                HttpStatus.NOT_FOUND);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}