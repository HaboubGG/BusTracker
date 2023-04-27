package com.example.clevervision.repository;

import com.example.clevervision.model.UsersModel;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersModel,Integer> {
    Optional<UsersModel> findByLoginAndPassword(String login , String password );

    //UsersModel findFirstByLogin(String login);
    UsersModel findFirstByEmail(String email);
}