package website.marcosfernandes.ecommerce.api.v1;

import java.time.OffsetDateTime;
import java.util.List;

public record ApiError(
        String code,
        String message,
        int status,
        String path,
        OffsetDateTime timestamp,
        List<FieldErrorItem> errors
) {
    public record FieldErrorItem(String field, String message, String code) {}
}
