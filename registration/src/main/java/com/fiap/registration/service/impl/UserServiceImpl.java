package com.fiap.registration.service.impl;

import com.fiap.registration.model.dto.AuthDTO;
import com.fiap.registration.model.dto.JwtDTO;
import com.fiap.registration.model.dto.UserCreateDTO;
import com.fiap.registration.model.dto.UserDTO;
import com.fiap.registration.model.entities.User;
import com.fiap.registration.repository.UserRepository;
import com.fiap.registration.security.JwtTokenUtil;
import com.fiap.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private HttpServletResponse httpServletResponse;

    @Autowired
    public UserServiceImpl(JwtTokenUtil jwtTokenUtil,
                           AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           HttpServletResponse httpServletResponse){
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public UserDTO create(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        User savedUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setUsername(savedUser.getUsername());
        return userDTO;
    }

    @Override
    public JwtDTO login(AuthDTO authDTO) {
        Authentication authenticate;
        try{
            authenticationManager.authenticate(
                    authenticate = new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        String token = jwtTokenUtil.generateToken(authDTO.getUsername());
        JwtDTO jwtDTO = new JwtDTO();
        jwtDTO.setToken(token);
        httpServletResponse.setHeader("Authorization", token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return jwtDTO;
    }
}
