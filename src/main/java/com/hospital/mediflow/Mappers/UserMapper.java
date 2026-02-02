package com.hospital.mediflow.Mappers;

import com.hospital.mediflow.Security.Dtos.UserRegister;
import com.hospital.mediflow.Security.Dtos.UserRegisterResponse;
import com.hospital.mediflow.Security.Dtos.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserRegisterResponse toRegisterResponse(User user);
    User toUser(UserRegister register);
}
