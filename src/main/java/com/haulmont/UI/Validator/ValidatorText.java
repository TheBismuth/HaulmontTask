package com.haulmont.UI.Validator;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

import java.util.regex.Pattern;

public class ValidatorText<T> implements Validator<T> {

    Pattern text = Pattern.compile("[а-яА-Я]{2,15}");

    @Override
    public ValidationResult apply(T t, ValueContext valueContext) {
        if (t instanceof String && ((String) t).matches(String.valueOf(text))) {
            return ValidationResult.ok();
        } else
            return ValidationResult.error("Введите корректное значение");
    }
}



