package website.marcosfernandes.ecommerce.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import website.marcosfernandes.ecommerce.validations.PasswordMatchesValidator;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    String message() default "Passwords do not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
