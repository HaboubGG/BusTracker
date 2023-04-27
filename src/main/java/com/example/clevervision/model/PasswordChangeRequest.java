package com.example.clevervision.model;

public class PasswordChangeRequest {
    private String currentPassword ;
    private String newPassword;

    @Override
    public String toString() {
        return "PasswordChangeRequest{" +
                "currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
