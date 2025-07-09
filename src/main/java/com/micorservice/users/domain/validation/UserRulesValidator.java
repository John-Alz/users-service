package com.micorservice.users.domain.validation;

import com.micorservice.users.domain.exception.InvalidFieldException;
import com.micorservice.users.domain.exception.RequiredFieldException;
import com.micorservice.users.domain.model.UserModel;
import com.micorservice.users.domain.utils.DomainConstants;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class UserRulesValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(DomainConstants.REGEX_EMAIL);
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(DomainConstants.REGEX_PHONE_NUMBER);
    private static final Pattern IDENTITY_NUMBER_PATTERN = Pattern.compile(DomainConstants.REGEX_IDENTITY_NUMBER);


    public void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new RequiredFieldException(DomainConstants.REQUIRED_EMAIL);
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidFieldException(DomainConstants.INVALID_EMAIL);
        }
    }

    public void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new RequiredFieldException(DomainConstants.REQUIRED_PHONE_NUMBER);
        }
        if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new InvalidFieldException(DomainConstants.INVALID_PHONE_NUMBER);
        }
    }

    public void validateIdentityNumber(String identityNumber) {
        if (identityNumber == null || identityNumber.trim().isEmpty()) {
            throw new RequiredFieldException(DomainConstants.REQUIRED_IDENTITY_NUMBER);
        }
        if (!IDENTITY_NUMBER_PATTERN.matcher(identityNumber).matches()) {
            throw new InvalidFieldException(DomainConstants.INVALID_IDENTITY_NUMBER);
        }
    }

    public void validateAge(LocalDate birthDay) {
        if (birthDay == null) {
            throw new RequiredFieldException(DomainConstants.REQUIRED_IDENTITY_NUMBER);
        }
        if (Period.between(birthDay, LocalDate.now()).getYears() < DomainConstants.MIN_AGE) {
            throw new InvalidFieldException(DomainConstants.INVALID_BIRTHDAY);
        }
    }

    public void validateUserData(UserModel user) {
        validateEmail(user.getEmail());
        validatePhoneNumber(user.getPhoneNumber());
        validateIdentityNumber(user.getDocumentNumber());
        validateAge(user.getBirthDate());
    }

}
