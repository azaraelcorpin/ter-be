package com.k12.ter.DataSource_TER.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.k12.ter.DataSource_TER.model.K12_PolicyAuth;


public interface K12_PolicyAuthRepository extends JpaRepository<K12_PolicyAuth,Long>{

        Optional<K12_PolicyAuth> findById(Long id);
        //sample Usage
        // Optional<K12_User> optionalUser = k12_userRepository.findById(id);
        // K12_User user = optionalUser.orElse(null);

        Optional<K12_PolicyAuth> findByEmail(String email); 

        List<K12_PolicyAuth> findAll();
}
