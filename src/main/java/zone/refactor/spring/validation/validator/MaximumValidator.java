package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;

public class MaximumValidator implements Validator {
    private final long maximum;

    public MaximumValidator(long maximum) {
        this.maximum = maximum;
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
                return Integer.parseInt((String)value) <= maximum;
            } catch (NumberFormatException e) {
                // Not a number, can't validate. Use IntegerValidator.
                return true;
            }
        } else if (value instanceof Integer) {
            return (Integer)value <= maximum;
        } else {
            return false;
        }
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.MAXIMUM.toString();
    }
}
