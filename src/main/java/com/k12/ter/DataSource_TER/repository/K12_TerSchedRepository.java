package com.k12.ter.DataSource_TER.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.k12.ter.DataSource_TER.model.K12_TerSched;


public interface K12_TerSchedRepository extends JpaRepository<K12_TerSched,Long>{

        Optional<K12_TerSched> findTopByOrderByIdDesc();  
        Optional<K12_TerSched> findTopByStatusOrderByIdDesc(String status);       
        void deleteById(Long id);
        List<K12_TerSched> findAll();
}
