package com.rangoli.dto;

public class ForgotPasswordRequest {
    private String usernameOrEmail; 
    public String getUsernameOrEmail()
    {
    	return usernameOrEmail;
    }
    public void setUsrnameOrEmail(String usernameOrEmail) {
    	this.usernameOrEmail =usernameOrEmail;
    }
}