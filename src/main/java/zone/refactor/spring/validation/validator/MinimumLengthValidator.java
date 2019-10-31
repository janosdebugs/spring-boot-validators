package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Map;

public class MinimumLengthValidator implements Validator {
    private final long minimumLength;

    public MinimumLengthValidator(long minimumLength) {
        this.minimumLength = minimumLength;
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return ((String) value).length() >= minimumLength;
        } else if (value instanceof CharSequence) {
            return (((CharSequence) value).length() >= minimumLength);
        } else if (value instanceof Collection) {
            return ((Collection) value).size() >= minimumLength;
        } else if (value instanceof Map) {
            return ((Map) value).size() >= minimumLength;
        } else if (value.getClass().isArray()) {
            return ((Object[]) value).length >= minimumLength;
        } else {
            return value.toString().isEmpty() || value.toString().length() >= minimumLength;
        }
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.MINIMUM_LENGTH.toString();
    }
}
