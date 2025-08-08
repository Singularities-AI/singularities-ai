package com.singularities.api.service;

import com.singularities.api.data.constant.ERole;
import com.singularities.api.data.entity.RoleModel;
import com.singularities.api.data.entity.UserModel;
import com.singularities.api.data.entity.UserRoleModel;
import com.singularities.api.data.entity.UserRoleModelCompositeId;
import com.singularities.api.data.repository.RoleRepository;
import com.singularities.api.data.repository.UserRepository;
import com.singularities.api.data.repository.UserRoleRepository;
import com.singularities.api.dto.request.UserUpdateRequestDto;
import com.singularities.api.exception.SingularitiesAINotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.singularities.api.exception.ExceptionMessage.ROLE_NOT_FOUND;
import static com.singularities.api.exception.ExceptionMessage.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    private final MessageService messageService;
    private final ChatService chatService;


    public UserModel findByUUID(String uuid) {
       return userRepository.findById(UUID.fromString(uuid)).orElseThrow(
               () -> new SingularitiesAINotFoundException(String.format(USER_NOT_FOUND, uuid))
       );
    }


    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new SingularitiesAINotFoundException(String.format(USER_NOT_FOUND, email))
        );
    }


    public List<UserRoleModel> findUserRolesByUserId(UUID userId) {
        return userRoleRepository.findAllByUserId(userId);
    }


    @Transactional
    public UserModel createByEmail(String email) {
        UserModel toSave = new UserModel();
        toSave.setEmail(email);
        UserModel user = userRepository.save(toSave);

        Set<RoleModel> roleModels = new HashSet<>();
        RoleModel userRoleModel = roleRepository.findByName(ERole.ROLE_USER.toString())
                .orElseThrow(() -> new SingularitiesAINotFoundException(String.format(ROLE_NOT_FOUND,
                        ERole.ROLE_USER)));
        roleModels.add(userRoleModel);
        for (RoleModel role : roleModels) {
            userRoleRepository.save(new UserRoleModel(new UserRoleModelCompositeId(toSave, role)));
        }

        //TODO create first onboarding chat
        //MessageModel firstMessage = chatService.addMessageToChat(user, new MessageRequestDto(null, ""));
        //messageService.create(firstMessage.getChat(), EMessageRole.AGENT, "test");

        return user;
    }


    @Transactional
    public UserModel update(UserModel user, UserUpdateRequestDto form) {
        user.setContext(form.getContext());
        return userRepository.save(user);
    }


    @Async
    public void updateLastLoginByEmail(String email) {
        UserModel user = userRepository.findByEmail(email).orElseThrow(
                () -> new SingularitiesAINotFoundException(String.format(USER_NOT_FOUND, email))
        );

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }
}
