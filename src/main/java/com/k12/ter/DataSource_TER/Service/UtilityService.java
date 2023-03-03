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
public class UtilityService {

    public String renderJsonResponse(String statusCode,String message){
        JSONObject resp = new JSONObject();
        try {
            resp.put("statusCode", statusCode);
            resp.put("message", message);
            return resp.toString();
        } catch (JSONException e) {                       
           return null;
        }
    }


    public String renderJsonResponse(String statusCode,String message,String ObjectName,String entityIdentifier) throws JSONException{
        JSONObject resp = new JSONObject();

            resp.put("statusCode", statusCode);
            resp.put("message", message);
            resp.put(ObjectName,entityIdentifier);
            log.info(resp.toString());
            return resp.toString();

    }

    public String renderJsonResponse(String statusCode,String message,Object obj) throws JSONException{
        JSONObject resp = new JSONObject();

            resp.put("statusCode", statusCode);
            resp.put("message", message);
            resp.put(obj.getClass().getSimpleName(),new JSONObject(new Gson().toJson(obj)));
            log.info(resp.toString());
            return resp.toString();

    }

    public String renderJsonResponse(String statusCode,String message,String itemsName,List iterable) throws JSONException{
        JSONObject resp = new JSONObject();
            
            resp.put("statusCode", statusCode);
            resp.put("message", message);
            resp.put(itemsName,toJSONArray(iterable));
 
            return resp.toString();

    }
    public String renderJsonResponse(String statusCode,String message,String itemsName,Page page) throws JSONException{
        JSONObject resp = new JSONObject();
            
            resp.put("statusCode", statusCode);
            resp.put("message", message);
            resp.put(itemsName,toJSONArray(page.toList()));
            resp.put("totalPages",page.getTotalPages());
            resp.put("numberOfElements",page.getNumberOfElements());
            resp.put("totalElements",page.getTotalElements());

            return resp.toString();

    }
    public JSONArray toJSONArray(List iterable) throws JSONException{
        JSONArray list = new JSONArray();
        for (Object o : iterable) {
               try{
                list.put(new JSONObject(new Gson().toJson(o)));
               }
               catch(Exception e){
                list.put(o);
               }
        }
        return list;
    }

    public JSONObject toJSONObject(Object obj) throws JSONException{
        return new JSONObject(new Gson().toJson(obj));
    } 
    
    public String getString(Object value){
        return value!=null?value.toString().replace(" ", "").isEmpty()?null:value.toString():null;
    }

    public Integer toInteger(String value){
        return value!=null?value.isEmpty()?null:(Integer.parseInt(value)):null;
    }

    public Integer toInteger(Object value){
        return value!=null?value.toString().isEmpty()?null:(Integer.parseInt(value.toString())):null;
    }

    public Long toLong(Object value){
        return value!=null?value.toString().isEmpty()?null:(Long.valueOf(value.toString())):null;
    }

    // public int generateTrn(String empId){
    //         if(value == null)
    //             return null;
    //         String dateString = value.toString();
    //         if(dateString.isEmpty())
    //             return null;
        
                
        
    // }

    // public Date getDate(Object value){
    //     if(value == null)
    //         return null;
    //     String dateString = value.toString();
    //     if(dateString.isEmpty())
    //         return null;
    
            
    
    // }

    // @Autowired
    // private RegistrationRepository generalRepo;

    // @Transactional("TerTransactionManager")
    // public ResponseEntity<Object> TestCOR(){
    //     try{
                        
    //         List<Map<String,Object>> items = generalRepo.TestCOR();
    //         ArrayList<Registration> regList = new ArrayList<>();
    //         JSONArray list = toJSONArray(items);
    //         for (int i = 0; i<list.length();i++) {
    //             JSONObject o = list.getJSONObject(i);
    //             Registration registration = new Gson().fromJson(o.toString(), Registration.class);
    //             regList.add(registration);
    //         }
    //         for (Registration registration : regList) {
    //             System.out.println(registration.getId());
    //             System.out.println(registration.getFacultyid());
    //         }
    //         // for (Registration registration : items) {
    //         //     registration.setId(null);
    //         // }
    //         return new ResponseEntity<Object>(renderJsonResponse("200", "Success","leaveApplications",items),
    //         HttpStatus.OK);

    //     }catch(Exception e){
    //         log.error(e.getMessage());
    //         return new ResponseEntity<Object>(renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
}
