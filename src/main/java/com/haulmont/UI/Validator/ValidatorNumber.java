package com.haulmont.UI.Validator;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

import java.util.regex.Pattern;

public class ValidatorNumber<T> implements Validator<T> {

    Pattern number = Pattern.compile("[0-9]{10}");

    @Override
    public ValidationResult apply(T t, ValueContext valueContext) {
        if (t instanceof String && ((String) t).matches(String.valueOf(number))) {
            return ValidationResult.ok();
        }else
            return ValidationResult.error("Введите норме без восьмерки");
    }
}

