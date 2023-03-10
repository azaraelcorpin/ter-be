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

import com.k12.ter.DataSource_TER.model.K12_Questionnaire;
import com.k12.ter.DataSource_TER.repository.K12_QuestionnaireRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class K12_QuestionnaireService {

    @Autowired
    private K12_QuestionnaireRepository k12_QuestionnaireRepository;

    @Autowired
    private UtilityService utilityService;

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> getAll() {
        try{
            List<K12_Questionnaire> list = k12_QuestionnaireRepository.findAll();
        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","items",list),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> createQuestionnaire(@RequestBody Map<String,Object> params) {
        try{
            String sy = params.get("sy").toString();
            String evalType = params.get("eval_type").toString();
            String question = params.get("question").toString();
            int qNumber = utilityService.toInteger(params.get("q_number"));
            K12_Questionnaire questionnaire = k12_QuestionnaireRepository.findTopByEvalTypeAndSyAndQstnNumberOrderByIdDesc(evalType, sy, qNumber).orElse(null);
            
            if(questionnaire != null){
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("402", "Item Duplication"),HttpStatus.BAD_REQUEST);
            }else{
                questionnaire = new K12_Questionnaire();
                questionnaire.setEvalType(evalType);
                questionnaire.setQstnNumber(qNumber);
                questionnaire.setQuestion(question);
                questionnaire.setSy(sy);              
            }
                k12_QuestionnaireRepository.save(questionnaire);
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success"),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> UpdateQuestionnaire(@RequestBody Map<String,Object> params) {
        try{
            String sy = params.get("sy").toString();
            String evalType = params.get("eval_type").toString();
            String question = params.get("question").toString();
            int qNumber = utilityService.toInteger(params.get("q_number"));
            Long id = utilityService.toLong(params.get("id"));
            K12_Questionnaire questionnaire = k12_QuestionnaireRepository.findTopByEvalTypeAndSyAndQstnNumberOrderByIdDesc(evalType, sy, qNumber).orElse(null);
            K12_Questionnaire qitem = k12_QuestionnaireRepository.findById(id).orElse(null);
            
            if(qitem == null){
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("404", "Not Found"),HttpStatus.BAD_REQUEST);
            }else if(!questionnaire.getId().equals(id) && qitem != null){
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("402", "Item Duplication"),HttpStatus.BAD_REQUEST);
            }else{                
                qitem.setEvalType(evalType);
                qitem.setQstnNumber(qNumber);
                qitem.setQuestion(question);
                qitem.setSy(sy);              
            }
                k12_QuestionnaireRepository.save(qitem);
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success"),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("TerTransactionManager")
    public ResponseEntity<Object> getQuestionnaireItemsByEvalTypeAndSy(@RequestBody Map<String,Object> params) {
        try{
            String sy = params.get("sy").toString();
            String evalType = params.get("eval_type").toString();
            List<K12_Questionnaire> list = k12_QuestionnaireRepository.findByEvalTypeAndSy(evalType,sy);
        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","items",list),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
