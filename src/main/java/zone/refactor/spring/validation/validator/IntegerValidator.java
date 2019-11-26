package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;

public class IntegerValidator implements Validator {
    public IntegerValidator() {
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.INTEGER.toString();
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return true;
        } else if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return true;
            }
            try {
                Long.parseLong((String)value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else //noinspection RedundantIfStatement
            if (
            value instanceof Integer ||
            value instanceof Long ||
            value instanceof Byte ||
            value instanceof Short
        ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof IntegerValidator;
    }
}
