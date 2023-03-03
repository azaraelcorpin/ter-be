package com.k12.ter.DataSource_TER.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DbGeneralService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UtilityService utilityService;
    
    public ResponseEntity<Object> getCORByEmail(@RequestBody Map<String,Object> params) {
        try{
            String email = params.get("email").toString();
        //get sy from ter_schedule table
        String sql = "SELECT trim(r.subjcode)as subjCode, trim(r.section)as section, trim(s.days)as days,trim(s.time)as time,trim(s.room)as room, f.facultyid,f.fullname as facultyName, ter.hasevaluation(r.subjcode,r.section,r.sy,r.sem,r.email) "
        + "FROM ter.k12_registration r, ter.k12_semsubject s, ter.k12_faculty f "
        + "WHERE r.email = '"+email+"' "
        + "and r.sy = '2022-2023'"
        + "and r.subjcode =s.subjcode and r.section =s.section and r.sy =s.sy "
        + "and s.facultyid = f.facultyid ";        
            List<Map<String,Object>> items = jdbcTemplate.queryForList(sql);
        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","COR",items),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
