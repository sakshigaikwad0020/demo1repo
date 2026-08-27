package com.rangoli.service;

import com.rangoli.dto.*;
import java.util.List;
import com.rangoli.entity.User;
public interface UserService {
 void register(User user);
 
    ApiResponse register(RegisterRequest req);

    ApiResponse login(LoginRequest req);
    ApiResponse adminLogin(LoginRequest req);

    ApiResponse getProfile(String username);
    ApiResponse logout();

    ApiResponse forgotPassword(ForgotPasswordRequest req);

    ApiResponse resetPassword(ResetPasswordRequest req);
 ApiResponse unlockUser(String username); 
 List<User>getAllUsers();
 //List<User>searchUser(String firstName,String lastName,String username,String email,String mobile, String status);

List<User> searchUsers(String fname, String lname, String username, String email, String mobile, String status);
User getUserById(Long userId);

User updateUser(Long userId, User user);
//User updateUserStatus(Long userId, User status);

User updateUserStatus(Long userId, String status);
void deleteUser(Long usersId);
}