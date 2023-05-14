package com.example.clevervision.service;

import com.example.clevervision.model.UsersModel;
import com.example.clevervision.repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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
        //Verification Email unique
        if(usersRepository.findFirstByEmail(email)!=null)
        {
            System.out.println("Email is already used by another account");
            return null;
        }
        //Verification des champs
        else if(login !=null && password !=null){
            UsersModel newUser = new UsersModel();
            newUser.setLogin(login);
//            newUser.setPassword(password);
            //Spring security pour le cryptage de mot de passe
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
        // 1 - verification de l"email
        UsersModel user = usersRepository.findFirstByEmail(email);
        if (user != null) {
            String encodedPassword = user.getPassword();
            //spring security
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

    public UsersModel ChangePassword(String ReferenceEmail ,String currentPassword , String newPassword)
    {
        UsersModel Existinguser = usersRepository.findFirstByEmail(ReferenceEmail);
        if(Existinguser!=null)
        {
            String encodedPassword =Existinguser.getPassword();
            if( passwordEncoder.matches(currentPassword, encodedPassword))
            {
                Existinguser.setPassword(passwordEncoder.encode(newPassword));
                UsersModel UpdatedUser = usersRepository.save(Existinguser);
                return UpdatedUser;
            }
            else return  null;
        }
        else return null;
    }
    public List<UsersModel> listUsers()
    {
        List<UsersModel> Users = usersRepository.findAll();
     return Users;
    }
    public Page<UsersModel> getAllUsersParPage(int page, int size) {
        // TODO Auto-generated method stub
        return usersRepository.findAll(PageRequest.of(page, size));
    }

    public List<UsersModel> listDrivers()
    {
        List<UsersModel> Users = usersRepository.findAllByRoleEquals(2);
        return Users;
    }


public void deleteUser(int id)
{
    usersRepository.deleteById(id);
}
public void EditRole(String role,int id)
{
    UsersModel user = usersRepository.findFirstById(id);
    if (role.equals("User"))
    {
    user.setRole(1);
    }
    else if(role.equals("Driver"))
    {
        user.setRole(2);
    }
    else{
        user.setRole(3);
    }
    usersRepository.save(user);
}

    public Page<UsersModel> searchUsersContainingName(String name, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return usersRepository.findAllByLoginContaining(name,pageable);

    }


}
