package com.darren.validator;

import com.darren.annotation.IdentityCardNumber;
import com.darren.utils.IdCardValidatorUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Project: light
 * Time   : 2021-02-08 20:55
 * Author : Eric
 * Version: v1.0
 * Desc   : 身份证校验器
 */
public class IdentityCardNumberValidator implements ConstraintValidator<IdentityCardNumber, Object> {

    @Override
    public void initialize(IdentityCardNumber identityCardNumber) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return IdCardValidatorUtils.isValidate18Idcard(o.toString());
    }
}