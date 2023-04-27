package com.example.clevervision.service;

import com.example.clevervision.model.UsersModel;
import com.example.clevervision.repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public UsersModel registerUser(String login , String password , String email)
    {
        if(usersRepository.findFirstByEmail(email)!=null)
        {
            System.out.println("Email is already used by another account");
            return null;
        }
        else if(login !=null && password !=null){
            UsersModel newUser = new UsersModel();
            newUser.setLogin(login);
//            newUser.setPassword(password);
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
            newUser.setEmail(email);
            newUser.setRole(1);
            return usersRepository.save(newUser);
        }
        else
        {
            return null;
        }
    }
    public UsersModel authenticate(String email , String password)
    {
        UsersModel user = usersRepository.findFirstByEmail(email);
        if (user != null) {
            String encodedPassword = user.getPassword();
            if( passwordEncoder.matches(password, encodedPassword))
            {
                return user;
            }
        }
        return null;
    }

    public UsersModel UpdateProfile(String ReferenceEmail,String NewLogin , String NewEmail)
    {

        UsersModel Existinguser = usersRepository.findFirstByEmail(ReferenceEmail);
        if(Existinguser!=null && NewEmail != ReferenceEmail) {
            UsersModel checkNewEmail = usersRepository.findFirstByEmail(NewEmail);
            if(checkNewEmail == null) {
                Existinguser.setEmail(NewEmail);
            }
            Existinguser.setLogin(NewLogin);
            UsersModel UpdatedUser = usersRepository.save(Existinguser);
            return UpdatedUser;
        }
        else{
            return null;
        }
    }


}
