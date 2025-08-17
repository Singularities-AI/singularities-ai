package com.singularities.api.service;

import com.singularities.api.data.constant.ESettingKey;
import com.singularities.api.data.entity.AuthTokenModel;
import com.singularities.api.data.entity.SettingValueModel;
import com.singularities.api.data.entity.UserModel;
import com.singularities.api.data.repository.UserRepository;
import com.singularities.api.dto.request.GenerateTokenRequestDto;
import com.singularities.api.dto.request.GetTokenRequestDto;
import com.singularities.api.exception.SingularitiesAIForbiddenException;
import com.singularities.api.exception.SingularitiesAIInternalServerErrorException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.singularities.api.exception.ExceptionMessage.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserService userService;
    private final AuthTokenService authTokenService;
    private final SettingService settingService;

    @Value("classpath:email/SecurityLoginEmail.html")
    private Resource securityLoginEmail;

    @Value("${app.url}")
    private String appUrl;

    @Value("${app.name}")
    private String appName;


    public UserModel getUserByAuthentication(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            String email = (String) authentication.getPrincipal();
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException(String.format(USER_NOT_FOUND, email)));
        } else {
            throw new RuntimeException(AUTH_FAILURE);
        }
    }


    public String getToken(GetTokenRequestDto form) {
        //check if the code is correct
        Optional<AuthTokenModel> authToken = authTokenService.getTokenByEmailAndSecurityCode(form.getEmail(), form.getCode());

        if (authToken.isEmpty()) {
            throw new SingularitiesAIForbiddenException(String.format(AUTH_CODE_INVALID, form.getCode()));
        }

        //check if the code limitation date is valid
        if (authToken.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new SingularitiesAIForbiddenException(String.format(AUTH_CODE_EXPIRED, form.getCode()));
        }

        //update last login date in async
        userService.updateLastLoginByEmail(form.getEmail());

        return authToken.get().getJwt();
    }


    @Transactional
    public void generateToken(GenerateTokenRequestDto form) {
        validateAuthorizedEmailDomain(form);

        UserModel user = new UserModel();
        if (form.getEmail() != null && userRepository.findByEmail(form.getEmail()).isEmpty()) {
            //create new user
            log.info(form.getEmail() + " is not exist - creating a new account");
            user = userService.createByEmail(form.getEmail());
        } else {
            user = userRepository.findByEmail(form.getEmail()).get();
        }

        //gen security code
        String securityCode = authTokenService.create(user).getSecurityCode();

        //send email with code
        sendEmailWithSecurityCode(user.getEmail(), securityCode);
    }


    private void validateAuthorizedEmailDomain(GenerateTokenRequestDto form) {
        List<String> domains = settingService.getByKey(ESettingKey.AUTH_AUTHORIZED_DOMAIN).getValues().stream().map(SettingValueModel::getValue).toList();
        boolean authorized = domains.isEmpty() || domains.stream().anyMatch(domain -> form.getEmail().toLowerCase().endsWith("@" + domain.toLowerCase()));

        if (!authorized) {
            throw new SingularitiesAIForbiddenException(String.format(AUTH_EMAIL_FORBIDDEN, form.getEmail()));
        }
    }


    public void sendEmailWithSecurityCode(String email, String securityCode) {
        String url = appUrl + "/email-confirmation?email=" + email + "&code=" + securityCode;

        try {
            emailService.sendHtmlEmail(email, "Connect to " + appName,
                    securityLoginEmail.getContentAsString(StandardCharsets.UTF_8)
                            .replace("{{code}}", securityCode)
                            .replace("{{appName}}", appName)
                            .replace("{{appUrl}}", appUrl)
                            .replace("{{year}}", String.valueOf(LocalDate.now().getYear()))
                            .replace("{{url}}", url));

        } catch (Exception e) {
            log.warn(AN_ERROR_OCCURED, e);
            throw new SingularitiesAIInternalServerErrorException(AN_ERROR_OCCURED);
        }
    }
}
