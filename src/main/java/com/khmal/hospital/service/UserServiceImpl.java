package com.khmal.hospital.service;

import com.khmal.hospital.dto.UserDto;
import com.khmal.hospital.entity.User;
import com.khmal.hospital.mapper.UserMapper;
import com.khmal.hospital.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserDto userDto){
        User user = UserMapper.INSTANCE.toEntity(userDto);
        System.out.println(user);
        userRepository.save(user);
    }
}
