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
            //check if AdminUser
            K12_User user = k12_UserRepository.findByEmail(email).orElse(null);
            JSONObject _user = new JSONObject();
            if(user != null){                
                _user.put("AdminUser", true);
            }
            //get the email of heads
            String sql ="SELECT x.*,COALESCE(kf.emailaddress,ef.email_address) as email FROM ter.k12_head x "
                   + "left join pis.employee_frdb ef on ef.empid = x.facultyid "
                   + "left join ter.k12_faculty kf on CONCAT(kf.facultyid) = x.facultyid "
                   + "where x.department in ('CED','JUNIOR') and x.designation in ('Dean','Director') "
                   + "and '"+email+"' in (kf.emailaddress,ef.email_address)";
            List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
            if(!rows.isEmpty()){
                    Map<String,Object> map = rows.get(0);                    
                        _user.put("position", map.get("designation").toString().trim());
                        _user.put("id", map.get("facultyid").toString().trim());
                        _user.put("email", email);
                        _user.put("eval_type","H");
                        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","user",_user),
                        HttpStatus.OK);                                                 
            }
            
            //check if faculty
            sql = "SELECT x.* FROM ter.k12_faculty x WHERE emailaddress = '"+email+"'";
            rows = jdbcTemplate.queryForList(sql);
            if(!rows.isEmpty()){
                Map<String,Object> map = rows.get(0); 
                    _user.put("email", email);
                    _user.put("id",map.get("facultyid"));
                    _user.put("position", "faculty");
                    _user.put("eval_type","P");
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","user",_user),
                HttpStatus.OK);
            }


            //check if enrolled student
            K12_TerSched sched = k12_TerSchedRepository.findTopByStatusOrderByIdDesc("open").orElse(null);
            if(sched == null)
                new Exception("No record of open for TER evaluation");

            sql = "SELECT CONCAT(s.id)as id  FROM ter.k12_registration x, ter.k12_student s "
                + "WHERE s.email = x.email and (x.sy = '"+sched.getSy()+"') AND (x.email = '"+email+"') limit 1";
            rows = jdbcTemplate.queryForList(sql);
            if(!rows.isEmpty()){
                _user.put("email", email);
                _user.put("position", "student");
                _user.put("eval_type","S");
                _user.put("id",rows.get(0).get("id").toString());
                
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
    

    public ResponseEntity<Object> getFacultyList() {
        try{
            // K12_TerSched sched = k12_TerSchedRepository.findTopByStatusOrderByIdDesc("open").orElse(null);
            // String sy = sched.getSy();
        //get sy from ter_schedule table
        String sql = "SELECT x.facultyid, trim(x.lastname)as lastname,trim(x.firstname)as firstname,x.emailaddress  FROM ter.k12_faculty x";       
            List<Map<String,Object>> items = jdbcTemplate.queryForList(sql);
        if(!items.isEmpty())
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","FacultyList",items),
                HttpStatus.OK);

        return new ResponseEntity<Object>(utilityService.renderJsonResponse("404", "Not Found"),
        HttpStatus.NOT_FOUND);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
