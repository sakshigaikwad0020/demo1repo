package com.rangoli.dto;

import javax.validation.constraints.*;

public class RegisterRequest {

    @NotBlank(message = "First name is mandatory")
    private String fname;

    @NotBlank(message = "Last name is mandatory")
    private String lname;

    @NotBlank(message = "Mobile is mandatory")
    @Pattern(regexp = "\\d{10}", message = "Mobile must be 10 digits")
    private String mobile;

    @NotBlank(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    // 🔥 ADD THIS BELOW 👇

    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }

    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}