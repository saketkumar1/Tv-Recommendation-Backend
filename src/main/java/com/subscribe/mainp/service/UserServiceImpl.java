package com.subscribe.mainp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.subscribe.mainp.dto.UserDto;
import com.subscribe.mainp.entity.User;
import com.subscribe.mainp.exceptions.ResourceNotFoundException;
import com.subscribe.mainp.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Integer getUserIdByUsername(String username) {
//        int i = 0;
        for(User u:userRepo.findAll()){
            if(u.getUsername().equals(username))
                return u.getId();
        }
        return 0;

    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if(userRepo.findByUsername(userDto.getUsername())!=null) throw new UsernameNotFoundException(userDto.getUsername());

        User user = this.modelMapper.map(userDto, User.class);
        User savedUser = this.userRepo.save(user);
        return this.modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        User updatedUser = this.userRepo.save(user);
        return this.modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        this.userRepo.delete(user);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos;
        List<User> users = this.userRepo.findAll();
        userDtos = users.stream().map(user->this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(userName);
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<>());
    }

    @Override
    public Integer getUserIdByEmail(String email) {
        for(User u:userRepo.findAll()){
            String str = u.getEmail();

            int i=0;
            for(i=0;i<str.length();i++) {
                if(email.charAt(i) != str.charAt(i))
                    break;
            }

            if(i == str.length())
                return u.getId();
        }
        return 0;

    }

    @Override
    public Boolean updatePassword(String mail, String password)
    {
        for(User u:userRepo.findAll()){
            String str = u.getEmail();

            int i=0;
            for(i=0;i<str.length();i++) {
                if(mail.charAt(i) != str.charAt(i))
                    break;
            }

            if(i == str.length()) {
                u.setPassword(password);
                userRepo.save(u);
                System.out.println((u.getUsername() + " " + u.getPassword()));
                return true;
            }
        }
        return false;
    }

}
