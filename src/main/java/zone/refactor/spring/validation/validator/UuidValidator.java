package zone.refactor.spring.validation.validator;

import java.util.regex.Pattern;


public class UuidValidator extends PatternValidator {
    private final static Pattern pattern =
            Pattern.compile(
                    "\\A[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\\Z"
            );

    public UuidValidator() {
        super(pattern);
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.UUID.toString();
    }


    @Override
    public boolean equals(Object other) {
        return other instanceof UuidValidator;
    }
}
