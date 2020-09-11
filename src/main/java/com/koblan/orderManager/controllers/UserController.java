package com.koblan.orderManager.controllers;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.transaction.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;


import com.koblan.orderManager.models.Login;
import com.koblan.orderManager.models.Message;
import com.koblan.orderManager.models.Role;
import com.koblan.orderManager.models.SignupData;
import com.koblan.orderManager.models.User;
import com.koblan.orderManager.repositories.UserRepository;
import com.koblan.orderManager.repositories.RoleRepository;
import com.koblan.orderManager.exceptions.NoSuchUserException;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
	
	    @Autowired
            AuthenticationManager authenticationManager;
	
	    @Autowired
	    UserRepository userRepository;

            @Autowired
	    RoleRepository roleRepository;

	    @Autowired
	    PasswordEncoder encoder;

            @GetMapping(value = "/users")
	    public ResponseEntity<List<User>> getAllUsers() {
	        List<User> userList = userRepository.findAll();
	        return new ResponseEntity<>(userList, HttpStatus.OK);
	    }
	 

	    @GetMapping(value = "/users/{id}")
	    public ResponseEntity<User> getUser(@PathVariable Long id) throws NoSuchUserException {
	        User user=userRepository.findById(id).get();
                if (user == null) throw new NoSuchUserException();
	        return new ResponseEntity<>(user, HttpStatus.OK);
	    }
	    
            @Transactional
	    @PostMapping("/auth")
	    public ResponseEntity<?> authenticateUser(@Valid @RequestBody Login loginRequest) {	
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginRequest.getUsername(),
	                        loginRequest.getPassword()
	                )
	        );
	        if (authentication==null) throw new BadCredentialsException("Login failed - Check username/password");
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        return ResponseEntity.ok(new Message(userDetails.getUsername()+ ": Authentication succesful - Roles: "+userDetails.getAuthorities()+""));
	        //return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
                //return ResponseEntity.ok().body("User logged successfully!");
	    }
	    
            @Transactional
	    @PostMapping("/newuser")
	    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupData signUpRequest) {

	    	
	    	if(userRepository.existsByUsername(signUpRequest.getUsername())) {
	            return new ResponseEntity<String>("Username already exists",
	                    HttpStatus.BAD_REQUEST);
	        }
	       
	        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
	            return new ResponseEntity<String>("Email already exists!",
	                    HttpStatus.BAD_REQUEST);
	        }
                                

	        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
	                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
	       
	        Set<String> stringRoles = signUpRequest.getRoles();
	        Set<Role> roles = new HashSet<>();
                Role role=null;
	        
               if (stringRoles!=null) {
	       if (stringRoles.contains("ROLE_GUEST")) {role=roleRepository.findByRoleIgnoreCase("ROLE_GUEST");
               roles.add(role);}
	       if (stringRoles.contains("ROLE_ADMIN")) {role=roleRepository.findByRoleIgnoreCase("ROLE_ADMIN");
               roles.add(role);} }
                
               //for(Role r:user.getRoles()) {Role role=roleRepository.findByRoleIgnoreCase               
               //(r.getRole());      //roles.add(new Role("ROLE_GUEST"));
               //roles.add(role);};
               //user.setRoles(roles); }

	        user.setRoles(roles);    
	        userRepository.save(user);
	        //return ResponseEntity.ok().body("User registered successfully!");
	        return new ResponseEntity<>(new Message("User registered successfully!"), HttpStatus.OK);
	    }
	    
	    
	

}
