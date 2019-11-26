package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;

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
        } else if (value instanceof Short) {
            return (Short)value >= minimum;
        } else if (value instanceof Byte) {
            return (Byte)value >= minimum;
        } else if (value instanceof Long) {
            return (Long)value >= minimum;
        } else if (value instanceof BigInteger) {
            return ((BigInteger) value).compareTo(BigInteger.valueOf(minimum)) > -1;
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).compareTo(BigDecimal.valueOf(minimum)) > -1;
        } else {
            return false;
        }
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.MINIMUM.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof MinimumValidator && ((MinimumValidator) other).minimum == minimum;
    }
}
