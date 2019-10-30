package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;

public class MaximumLengthValidator implements Validator {
    private final long maximumLength;

    public MaximumLengthValidator(long maximumLength) {
        this.maximumLength = maximumLength;
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return true;
        } else if (value instanceof String) {
            return ((String)value).length() <= maximumLength;
        } else {
            return value.toString().length() <= maximumLength;
        }
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.MAXIMUM_LENGTH.toString();
    }
}
