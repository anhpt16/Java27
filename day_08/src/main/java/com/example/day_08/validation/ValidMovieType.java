package com.example.day_08.validation;

import com.example.day_08.validation.validator.MovieTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MovieTypeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMovieType {
    String message() default "Loại phim không phù hợp";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
