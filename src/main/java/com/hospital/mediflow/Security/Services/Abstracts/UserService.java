package com.hospital.mediflow.Security.Services.Abstracts;

import com.hospital.mediflow.Security.Dtos.UserLogin;
import com.hospital.mediflow.Security.Dtos.UserLoginResponse;
import com.hospital.mediflow.Security.Dtos.UserRegister;
import com.hospital.mediflow.Security.Dtos.UserRegisterResponse;

public interface UserService {

     UserRegisterResponse register(UserRegister register);
     UserLoginResponse verify(UserLogin login);
}
