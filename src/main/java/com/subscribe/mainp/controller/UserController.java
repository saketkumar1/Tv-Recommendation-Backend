package com.subscribe.mainp.controller;
import java.util.List;

import javax.validation.Valid;

import com.subscribe.mainp.entity.User;
import com.subscribe.mainp.service.EmailService;
import org.springframework.web.bind.annotation.*;

import com.subscribe.mainp.dto.UserDto;
import com.subscribe.mainp.payloads.ApiResponse;
import com.subscribe.mainp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping ("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    //PUT - update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){
        UserDto updateUser = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updateUser);
    }

    //DELETE - delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse>deleteUser(@PathVariable Integer userId){
        this.userService.deleteUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
    }

    //GET - get user
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto>getUserById(@PathVariable Integer userId){
        UserDto userDto = this.userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>>getAllUsers(){
        List<UserDto> userDtos = this.userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/getUserId/{username}")
    public Integer getUserIdByUsername(@PathVariable String username){
        Integer userId = this.userService.getUserIdByUsername(username);
        return userId;
    }

    @GetMapping("/getUserId/email")
    public ResponseEntity<Integer> getUserIdByEmail(@RequestBody String email){
        Integer userId = this.userService.getUserIdByEmail(email);
        if(userId == 0)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.ok(userId);
    }

    @PutMapping("/updatePassword/{mail}")
    public ResponseEntity<Boolean> updatePassword(@PathVariable String mail, @RequestBody String password){
        Boolean res = this.userService.updatePassword(mail,password);

        if(res)
            return ResponseEntity.ok(res);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
     @PostMapping("/sendOTP")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody String email)
    {
        Integer userId = this.userService.getUserIdByEmail(email);

        if(userId == 0)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        Integer otp = emailService.sendMail(email);

        return ResponseEntity.ok(otp+"");
    }

}
