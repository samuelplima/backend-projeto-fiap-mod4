package com.fiap.registration.service;

import com.fiap.registration.model.dto.AuthDTO;
import com.fiap.registration.model.dto.JwtDTO;
import com.fiap.registration.model.dto.UserCreateDTO;
import com.fiap.registration.model.dto.UserDTO;

public interface UserService {

    UserDTO create(UserCreateDTO userCreateDTO);
    JwtDTO login(AuthDTO authDTO);

}
