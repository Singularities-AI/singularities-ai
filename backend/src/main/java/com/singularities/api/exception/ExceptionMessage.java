package com.singularities.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExceptionMessage {

    public static final String AN_ERROR_OCCURED = "An error occurred.";
    public static final String FORBIDDEN = "Access denied.";


    //USERS --
    public static final String USER_NOT_FOUND = "User not found: %s.";

    //ROLES --
    public static final String ROLE_NOT_FOUND = "Role not found: %s.";

    //CHATS --
    public static final String CHAT_NOT_FOUND = "Chat not found: %s.";
    public static final String CHAT_ACCESS_FORBIDDEN = "Chat access forbidden : %s.";
    public static final String CHAT_UPDATE_FORBIDDEN = "Chat update forbidden : %s.";
    public static final String CHAT_DELETE_FORBIDDEN = "Chat delete forbidden : %s.";

    //MODELS --
    public static final String MODEL_NOT_FOUND = "Model not found: %s.";
    public static final String MODEL_IS_ALREADY_DOWNLOAD = "Model is already download: %s.";

    //AUTH --
    public static final String USER_AUTHENTICATION = "Cannot set user authentication: {}";
    public static final String AUTH_FAILURE = "Authentication failed.";
    public static final String AUTH_CODE_INVALID = "Authentication code is invalid: %s.";
    public static final String AUTH_CODE_EXPIRED = "Authentication code is expired: %s.";
}
