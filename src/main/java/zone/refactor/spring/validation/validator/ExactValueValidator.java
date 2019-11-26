package zone.refactor.spring.validation.validator;


import org.springframework.lang.Nullable;

public class ExactValueValidator implements Validator {
    private final Object expected;

    public ExactValueValidator(Object expected) {
        this.expected = expected;
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.EXACT_VALUE.toString();
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        return value == null || (value instanceof String && ((String) value).isEmpty()) || value.equals(expected);
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (!(other instanceof ExactValueValidator)) {
            return false;
        }
        return expected.equals(((ExactValueValidator) other).expected);
    }
}
