package com.singularities.api.mapper;

import com.singularities.api.data.entity.RoleModel;
import com.singularities.api.data.entity.UserModel;
import com.singularities.api.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final AbstractMapper<UserModel, UserResponseDto> abstractMapper;

    public UserResponseDto toDto(UserModel model) {
        UserResponseDto dto = new UserResponseDto();
        dto.setEmail(model.getEmail());
        dto.setContext(model.getContext());
        dto.setLastLogin(model.getLastLogin());
        dto.setContext(model.getContext());
        dto.setRoles(model.getRoles().stream().map(RoleModel::getName).toList());
        return abstractMapper.completeAbstractDto(model, dto);
    }
}
