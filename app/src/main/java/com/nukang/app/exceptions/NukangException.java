package com.nukang.app.exceptions;

import java.util.HashMap;
import java.util.Map;

public class NukangException{
    private String ErrorCode;
    private Map<String,String> ErrorMessage;

    public NukangException(String errorCode, String id, String en) {
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("Indonesian", id);
        errorMessage.put("English", en);
        ErrorCode = errorCode;
        ErrorMessage = errorMessage;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public Map<String, String> getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(Map<String, String> errorMessage) {
        ErrorMessage = errorMessage;
    }
}
