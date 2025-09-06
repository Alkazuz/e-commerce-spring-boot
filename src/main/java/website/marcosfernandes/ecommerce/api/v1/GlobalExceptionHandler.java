package website.marcosfernandes.ecommerce.api.v1;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import website.marcosfernandes.ecommerce.exceptions.EmailAlreadyExistsException;
import website.marcosfernandes.ecommerce.exceptions.UserNotFoundException;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex, HttpServletRequest req) {
        return handleDefault(ex, req, HttpStatus.NOT_FOUND, "user.not.found", "User not found");
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyExists(EmailAlreadyExistsException ex, HttpServletRequest req) {
        return handleDefault(ex, req, HttpStatus.CONFLICT, "user.email.already.exists", "Email already exists");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                 HttpServletRequest req) {
        BindingResult br = ex.getBindingResult();
        List<ApiError.FieldErrorItem> fields = br.getFieldErrors().stream()
                .map(fe -> new ApiError.FieldErrorItem(
                        fe.getField(),
                        messageSource.getMessage(fe, LocaleContextHolder.getLocale()),
                        fe.getCode()
                ))
                .toList();

        String msg = messageSource.getMessage("validation.failed", null, "Validation failed",
                LocaleContextHolder.getLocale());

        ApiError body = build("validation.failed", msg, HttpStatus.BAD_REQUEST, req.getRequestURI(), fields);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
        List<ApiError.FieldErrorItem> fields = ex.getConstraintViolations().stream()
                .map(v -> new ApiError.FieldErrorItem(
                        v.getPropertyPath().toString(),
                        v.getMessage(),
                        v.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName()
                ))
                .toList();

        String msg = messageSource.getMessage("validation.failed", null, "Validation failed",
                LocaleContextHolder.getLocale());

        ApiError body = build("validation.failed", msg, HttpStatus.BAD_REQUEST, req.getRequestURI(), fields);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex, HttpServletRequest req) {
        return handleDefault(ex, req, HttpStatus.UNAUTHORIZED, "auth.bad.credentials", "Invalid email or password");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        return handleDefault(ex, req, HttpStatus.CONFLICT, "data.integrity.violation", "Data integrity violation");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception ex, HttpServletRequest req) {
        return handleDefault(ex, req, HttpStatus.INTERNAL_SERVER_ERROR, "internal.server.error", "Internal server error");
    }

    private ResponseEntity<ApiError> handleDefault(Exception ex, HttpServletRequest req,
                                                       HttpStatus status, String code, String defaultMsg) {
        String error = ex.getClass().getSimpleName();
        String exceptionKey = "exception." + error.toLowerCase() + ".code";
        String msg = messageSource.getMessage(exceptionKey, null, defaultMsg, LocaleContextHolder.getLocale());
        ApiError body = build(code, msg, status, req.getRequestURI(), null);
        return ResponseEntity.status(status).body(body);
    }

    private ApiError build(String code, String message, HttpStatus status,
                           String path, List<ApiError.FieldErrorItem> fields) {
        return new ApiError(
                code,
                message,
                status.value(),
                path,
                OffsetDateTime.now(),
                fields
        );
    }
}
