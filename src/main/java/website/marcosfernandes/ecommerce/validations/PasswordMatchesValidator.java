package website.marcosfernandes.ecommerce.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import website.marcosfernandes.ecommerce.annotations.PasswordMatches;
import website.marcosfernandes.ecommerce.api.v1.dto.auth.RegisterRequestDTO;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterRequestDTO> {

    @Override
    public boolean isValid(RegisterRequestDTO dto, ConstraintValidatorContext context) {
        return dto.getPassword() != null &&
                dto.getPassword().equals(dto.getConfirmPassword());
    }
}

