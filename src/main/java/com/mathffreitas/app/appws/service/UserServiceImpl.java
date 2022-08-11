package com.mathffreitas.app.appws.service;

import com.mathffreitas.app.appws.dto.UserDto;
import com.mathffreitas.app.appws.entity.UserEntity;
import com.mathffreitas.app.appws.repository.UserRepository;
import com.mathffreitas.app.appws.shared.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    Utils utils;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto user) {


        if(userRepository.findUserByEmail(user.getEmail()) != null) throw new RuntimeException("Record already exists");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserId = utils.generateUserId(30);
        userEntity.setEncryptedPassword("test");
        userEntity.setUserId(publicUserId);

        UserEntity storedUserDetail = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetail, returnValue);

        return returnValue;
    }
}
