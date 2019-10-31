package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Map;

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
        } else if (value instanceof CharSequence) {
            return (((CharSequence) value).length() <= maximumLength);
        } else if (value instanceof Collection) {
            return ((Collection) value).size() <= maximumLength;
        } else if (value instanceof Map) {
            return ((Map) value).size() <= maximumLength;
        } else if (value.getClass().isArray()) {
            return ((Object[]) value).length <= maximumLength;
        } else {
            return value.toString().length() <= maximumLength;
        }
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.MAXIMUM_LENGTH.toString();
    }
}
