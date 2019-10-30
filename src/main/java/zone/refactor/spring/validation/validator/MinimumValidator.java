package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;

public class MinimumValidator implements Validator {
    private final long minimum;

    public MinimumValidator(long minimum) {
        this.minimum = minimum;
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return true;
            }
            try {
                return Integer.parseInt((String)value) >= minimum;
            } catch (NumberFormatException e) {
                // Not a number, can't validate. Use IntegerValidator.
                return true;
            }
        } else if (value instanceof Integer) {
            return (Integer)value >= minimum;
        } else {
            return false;
        }
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.MINIMUM.toString();
    }
}
