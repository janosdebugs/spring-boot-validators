package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;

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
            return ((String) value).isEmpty() || ((String)value).length() >= minimumLength;
        } else {
            return value.toString().isEmpty() || value.toString().length() >= minimumLength;
        }
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.MINIMUM_LENGTH.toString();
    }
}
