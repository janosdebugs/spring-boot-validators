package zone.refactor.spring.validation.annotation;

import java.util.Map;
import java.util.Set;

public class ValidationException extends Exception {
    public final Map<String, Set<String>> errors;

    public ValidationException(Map<String, Set<String>> errors) {
        super("Validation failed.");
        this.errors = errors;
    }
}
