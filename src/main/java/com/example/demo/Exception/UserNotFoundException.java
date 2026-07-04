package com.example.demo.Exception;

public class UserNotFoundException extends RuntimeException{
    public  UserNotFoundException(String Message){
        super(Message);
    }
}
