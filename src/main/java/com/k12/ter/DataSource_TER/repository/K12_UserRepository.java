package com.k12.ter.DataSource_TER.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.k12.ter.DataSource_TER.model.K12_User;


public interface K12_UserRepository extends JpaRepository<K12_User,Long>{

        Optional<K12_User> findById(Long id);
        //sample Usage
        // Optional<K12_User> optionalUser = k12_userRepository.findById(id);
        // K12_User user = optionalUser.orElse(null);

        Optional<K12_User> findByEmail(String email); 
        
        void deleteByPositionAndIdNot(String position,Long Id);

        void deleteByPosition(String position);

        void deleteByEmail(String email);

        List<K12_User> findAll();
}
