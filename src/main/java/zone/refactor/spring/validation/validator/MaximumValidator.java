package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MaximumValidator implements Validator {
    private final long maximum;
    private final boolean inclusive;

    public MaximumValidator(long maximum) {
        this.maximum = maximum;
        this.inclusive = true;
    }

    public MaximumValidator(long maximum, boolean inclusive) {
        this.maximum = maximum;
        this.inclusive = inclusive;
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
                return Long.parseLong((String)value) <= maximum;
            } catch (NumberFormatException e) {
                // Not a number, can't validate. Use IntegerValidator.
                return true;
            }
        } else if (value instanceof Integer) {
            return (inclusive && (Integer)value <= maximum) || (!inclusive && (Integer)value < maximum);
        } else if (value instanceof Short) {
            return (inclusive && (Short)value <= maximum) || (!inclusive && (Short)value < maximum);
        } else if (value instanceof Byte) {
            return (inclusive && (Byte)value <= maximum) || (!inclusive && (Byte)value < maximum);
        } else if (value instanceof Long) {
            return (inclusive && (Long)value <= maximum) || (!inclusive && (Long)value < maximum);
        } else if (value instanceof BigInteger) {
            return
                (inclusive && ((BigInteger) value).compareTo(BigInteger.valueOf(maximum)) <= 0) ||
                (!inclusive && ((BigInteger) value).compareTo(BigInteger.valueOf(maximum)) < 0);
        } else if (value instanceof BigDecimal) {
            return
                (inclusive && ((BigDecimal) value).compareTo(BigDecimal.valueOf(maximum)) <= 0) ||
                (!inclusive && ((BigDecimal) value).compareTo(BigDecimal.valueOf(maximum)) < 0);
        } else {
            return false;
        }
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.MAXIMUM.toString();
    }
}
