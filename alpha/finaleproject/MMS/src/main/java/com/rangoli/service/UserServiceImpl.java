package com.rangoli.service;

import java.time.LocalDateTime;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rangoli.dto.ApiResponse;
import com.rangoli.dto.ForgotPasswordRequest;
import com.rangoli.dto.LoginRequest;
import com.rangoli.dto.RegisterRequest;
import com.rangoli.dto.ResetPasswordRequest;
import com.rangoli.entity.User;
import com.rangoli.repository.UserRepository;
import com.rangoli.config.JwtUtil;
import java.util.List;
import com.rangoli.entity.User;
import com.rangoli.service.EmailService;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;
    @Autowired
    private EmailService emailService;
     private String status;
     @Autowired
 	private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
    
    
    @Override
    public ApiResponse register(RegisterRequest req) {

        // Email check
        if (repo.findByEmail(req.getEmail()).isPresent()) {
            return new ApiResponse(false, "Email already exists",null);
        }

       
        if (repo.findByUsername(req.getUsername()).isPresent()) {
            return new ApiResponse(false, "Username already exists",null);
        }

        // Create User
        User u = new User();
        u.setFname(req.getFname());
        u.setLname(req.getLname());
        u.setMobile(req.getMobile());
        u.setEmail(req.getEmail());
        u.setLocation(req.getLocation());
        u.setUsername(req.getUsername());
        //u.setPassword(req.getPassword());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setStatus("ACTIVE");
        u.setLastModifiedDate(LocalDateTime.now());

        User savedUser = repo.save(u);

        return new ApiResponse(true, "User Registered Successfully",savedUser);
    }

	@Override
	public ApiResponse login(LoginRequest req) {
		// TODO Auto-generated method stub
		
		    User user = repo.findByUsername(req.getUsernameOrEmail())
		            .orElse(repo.findByEmail(req.getUsernameOrEmail()).orElse(null));

		    if (user == null) {
		        return new ApiResponse(false, "Invalid Username/Email", null);
		    }

		    // Account locked check
		    if (!user.getStatus().equalsIgnoreCase("ACTIVE")) {
		        return new ApiResponse(false,
		                "Your account is locked. Please contact the administrator.",
		                null);
		    }

		    // Password check
		    if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {

		        user.setFailedAttempts(user.getFailedAttempts() + 1);

		        if (user.getFailedAttempts() >= 3) {
		            user.setStatus("INACTIVE");
		            repo.save(user);

		            return new ApiResponse(false,
		                    "Your account has been locked due to 3 consecutive failed login attempts. Please contact the administrator.",
		                    null);
		        }

		        repo.save(user);

		        return new ApiResponse(false,"Invalid credentials: ",3 - user.getFailedAttempts());
		    }

		    // Successful login
		    user.setFailedAttempts(0);
		    repo.save(user);

		    String token = JwtUtil.generateToken(user.getUsername());

		    return new ApiResponse(true, "Login Successful", token);
		}
	@Override
	public ApiResponse adminLogin(LoginRequest req) {
		ApiResponse response =login(req);
		User user =repo.findByUsername(req.getUsernameOrEmail()).orElse(repo.findByEmail(req.getUsernameOrEmail()).orElse(null));
		
		if(user == null) {
			System.out.println(user.getUsername());
			return new ApiResponse(false,"only admin can login",null);
		}
		if(!"ROLE_ADMIN".equals(user.getRole())) {
			System.out.println(user.getRole());
			return new ApiResponse(false,"only admin can login",null);
		}
		return response;
	}
	private Object getRole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse getProfile(String username) {
		// TODO Auto-generated method stub
		User user =repo.findByUsername(username).orElse(null);
		if(user ==null) {
			return new ApiResponse(false,"User Not Found",null);
		}
		
		return new ApiResponse(true,"welcome"+user.getFname()+""+user.getLname(),user);
	}

	@Override
	public ApiResponse forgotPassword(ForgotPasswordRequest req) {
		// TODO Auto-generated method stub
		
		User user = repo.findByUsername(req.getUsernameOrEmail())
		            .orElse(
		                    repo.findByEmail(req.getUsernameOrEmail())
		                            .orElse(null)
		            );

		    if (user == null) {
		        return new ApiResponse(false, "User Not Found", null);
		    }

		    String token = UUID.randomUUID().toString();

		    user.setResetToken(token);
		    user.setTokenExpiry(LocalDateTime.now().plusMinutes(15));

		    repo.save(user);

		    System.out.println(
		            "http://localhost:8081/api/users/reset-password?token=" + token
		    );

		    return new ApiResponse(
		            true,
		            "Password reset link generated successfully.",
		            null
		    );
		}

	

	@Override
	public ApiResponse resetPassword(ResetPasswordRequest req) {
		// TODO Auto-generated method stub@Override
		

		    User user = repo.findByResetToken(req.getToken()).orElse(null);

		    if (user == null) {
		        return new ApiResponse(false, "Invalid Token", null);
		    }

		    if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
		        return new ApiResponse(false, "Token Expired", null);
		    }

		    if (!req.getNewPassword().equals(req.getConfirmPassword())) {
		        return new ApiResponse(false, "Passwords do not match", null);
		    }

		    user.setPassword(passwordEncoder.encode(req.getNewPassword()));
		    user.setResetToken(null);
		    user.setTokenExpiry(null);
		    user.setLastModifiedDate(LocalDateTime.now());

		    repo.save(user);

		    return new ApiResponse(true,
		            "Password updated successfully.",
		            null);
		}

	@Override
	public ApiResponse logout() {
				return new ApiResponse(true,"Logout Successful",null);
	}
	public ApiResponse unlockUser(String username) {
		User user = repo.findByUsername(username).orElse(null);
		if(user == null) {
			return new ApiResponse(false,"User not found",null);
		}
		user.setStatus("ACTIVE");
		user.setFailedAttempts(0);
		repo.save(user);
		return new ApiResponse(true,"user account unlocked successfully",user);
	}
	@Override
	public List<User>getAllUsers(){
		return repo.findAll();
	}
	@Override
	public List<User> searchUsers(String firstName,String lastName, String username,String email,String mobile,
	                              String status) {

	    if (firstName != null && !firstName.isBlank()) {
	        return repo.findByFnameContainingIgnoreCase(firstName);
	    }

	    if (lastName != null && !lastName.isBlank()) {
	        return repo.findByLnameContainingIgnoreCase(lastName);
	    }

	    if (username != null && !username.isBlank()) {
	        return repo.findByUsernameContainingIgnoreCase(username);
	    }

	    if (email != null && !email.isBlank()) {
	        return repo.findByEmailContainingIgnoreCase(email);
	    }

	    if (mobile != null && !mobile.isBlank()) {
	        return repo.findByMobileContaining(mobile);
	    }

	    if (status != null && !status.isBlank()) {
	        return repo.findByStatusIgnoreCase(status);
	    }

	    return repo.findAll();
	}
	@Override
	public User getUserById(Long userId) {
		return repo.findById(userId).orElse(null);
	}
	@Override
	public User updateUser(Long userId,User user) {
		User existingUser =repo.findById(userId).orElse(null);
		User updatedUser =repo.save(user);
		emailService.sendEmail(updatedUser.getEmail(),"MMS notification","your account status has been changed to"+status);
		return updatedUser;
	}
	@Override
	public User updateUserStatus(Long userId, String status) {

	    User user = repo.findById(userId).orElse(null);

	    if (user == null) {
	        return null;
	    }

	    user.setStatus(status);

	    return repo.save(user);
	}
	@Override
	public void deleteUser(Long usersId) {
		User user =repo.findById(usersId).orElse(null);
		if(user !=null) {
			String email = user.getEmail();
			repo.delete(user);
			emailService.sendEmail(email,"MMS notification","your request has been processed successfully");
		}
	
		
		
	}
	
	@Override
	public void register(User user) {
		userRepository.save(user);
	}
		
	@Override
	public Object login(LoginRequest request) {
		User user =userRepository.findByEmail(request.getEmail);
		if(user ==null) {
			throw new RuntimeException("User not found");
		}
		return "login successfuflly";
	}
}