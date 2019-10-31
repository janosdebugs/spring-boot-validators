package zone.refactor.spring.validation.annotation;

import java.util.Collection;
import java.util.Map;

public class ValidationException extends Exception {
    public final Map<String, Collection<String>> errors;

    public ValidationException(Map<String, Collection<String>> errors) {
        super("Validation failed.");
        this.errors = errors;
    }
}
