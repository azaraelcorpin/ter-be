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

import com.k12.ter.DataSource_TER.model.K12_TerSched;
import com.k12.ter.DataSource_TER.model.K12_User;
import com.k12.ter.DataSource_TER.repository.K12_TerSchedRepository;
import com.k12.ter.DataSource_TER.repository.K12_UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class K12_TerSchedService {

    @Autowired
    private K12_TerSchedRepository k12_TerSchedRepository;

    @Autowired
    private UtilityService utilityService;

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> getSched() {
        try{
            Optional<K12_TerSched> optionalSched = k12_TerSchedRepository.findTopByOrderByIdDesc();
            K12_TerSched terSched = optionalSched.orElse(null);
        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success",terSched),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> setTerSched(@RequestBody Map<String,Object> params) {
        try{
            Date dateStart = utilityService.getDate(params.get("dateStart"));
            Date dateEnd = utilityService.getDate(params.get("dateEnd"));
            String sy = params.get("sy").toString();

            Optional<K12_TerSched> optionalSched = k12_TerSchedRepository.findTopByOrderByIdDesc();
            K12_TerSched terSched = optionalSched.orElse(null);
            if(terSched != null){
                terSched.setDateStart(dateStart);
                terSched.setDateEnd(dateEnd);
                terSched.setSy(sy);
                terSched.setStatus(K12_TerSched.STATUS_CLOSED);
            }else{
                terSched = new K12_TerSched();
                terSched.setDateStart(dateStart);
                terSched.setDateEnd(dateEnd);
                terSched.setSy(sy);
                terSched.setStatus(K12_TerSched.STATUS_CLOSED);
            }
                k12_TerSchedRepository.save(terSched);
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success"),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> setClosed() {
        try{
            Optional<K12_TerSched> optionalSched = k12_TerSchedRepository.findTopByOrderByIdDesc();
            K12_TerSched terSched = optionalSched.orElse(null);
            if(terSched != null){
                terSched.setStatus(K12_TerSched.STATUS_CLOSED);
            }else{
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("404", "No Record Found"),HttpStatus.NOT_FOUND);
            }
                k12_TerSchedRepository.save(terSched);
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success"),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> setOpen() {
        try{
            Optional<K12_TerSched> optionalSched = k12_TerSchedRepository.findTopByOrderByIdDesc();
            K12_TerSched terSched = optionalSched.orElse(null);
            if(terSched != null){
                terSched.setStatus(K12_TerSched.STATUS_OPEN);
            }else{
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("404", "No Record Found"),HttpStatus.NOT_FOUND);
            }
                k12_TerSchedRepository.save(terSched);
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success"),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
