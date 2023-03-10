package com.k12.ter.DataSource_TER.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.k12.ter.DataSource_TER.model.K12_Questionnaire;


public interface K12_QuestionnaireRepository extends JpaRepository<K12_Questionnaire,Long>{

        Optional<K12_Questionnaire> findById(Long id);
        //sample Usage
        // Optional<K12_User> optionalUser = k12_userRepository.findById(id);
        // K12_User user = optionalUser.orElse(null);

        List<K12_Questionnaire> findByEvalTypeAndSy(String eval_type,String sy); 
        
        void deleteById(Long Id);

        List<K12_Questionnaire> findAll();

        Optional<K12_Questionnaire> findTopByEvalTypeAndSyAndQstnNumberOrderByIdDesc(String eval_type, String sy, int qNumber);
}
