package com.example.day_08.validation.validator;

import com.example.day_08.model.enums.MovieType;
import com.example.day_08.validation.ValidMovieType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.EnumSet;

public class MovieTypeValidator implements ConstraintValidator<ValidMovieType, MovieType> {

    @Override
    public boolean isValid(MovieType value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return false;
        return EnumSet.of(MovieType.PHIM_LE, MovieType.PHIM_BO, MovieType.PHIM_CHIEU_RAP).contains(value);
    }
}
