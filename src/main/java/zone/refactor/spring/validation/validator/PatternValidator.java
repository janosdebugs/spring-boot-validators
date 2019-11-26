package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;

import java.util.regex.Pattern;


public class PatternValidator implements Validator {

    private final Pattern pattern;

    public PatternValidator(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.PATTERN.toString();
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return ((String) value).isEmpty() || pattern.matcher((String)value).matches();
        } else {
            return value.toString().isEmpty() || pattern.matcher(value.toString()).matches();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof PatternValidator && ((PatternValidator) other).pattern.equals(pattern);
    }
}
