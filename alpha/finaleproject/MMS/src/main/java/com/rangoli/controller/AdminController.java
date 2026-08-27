package com.rangoli.controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.rangoli.dto.LoginRequest;
import com.rangoli.dto.ApiResponse;
import com.rangoli.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.rangoli.entity.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {
@Autowired
private UserService service;

@PostMapping("/login")
public ApiResponse adminLogin(@RequestBody LoginRequest req) {
	System.out.println(req.getUsernameOrEmail());
	return service.adminLogin(req);
}
@GetMapping("/users")
public List<User>getAllUsers(){
	return service.getAllUsers();
}
@GetMapping("/users/search")
public List<User> searchUsers(
        @RequestParam(required = false) String fname,
        @RequestParam(required = false) String lname,
        @RequestParam(required = false) String username,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String mobile,
        @RequestParam(required = false) String status) {

    return service.searchUsers(fname, lname, username, email, mobile, status);
}
@GetMapping("/users/{usersId}")
	public ResponseEntity<?>getUserById(@PathVariable Long usersId) {
	User users =service.getUserById(usersId);
	if(users ==null) {
		return ResponseEntity.status(404).body("user not found");
	}
		return ResponseEntity.ok(users);
}
@PutMapping("/users/{userId}")
public User updateUser(@PathVariable Long userId,@RequestBody User users) {
	return service.updateUser(userId,users);
	
}
@PutMapping("/users/{usersId}/status")
public User updateUserStatus(@PathVariable Long usersId,
                             @RequestParam String status) {

    return service.updateUserStatus(usersId, status);
}
@DeleteMapping("/users/{usersId}")
public String deleteUser(@PathVariable Long usersId) {

service.deleteUser(usersId);
return "User deleted successfully";
}
}

