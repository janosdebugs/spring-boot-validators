package zone.refactor.spring.validation.validator;

import java.util.regex.Pattern;


public class HttpUrlValidator extends PatternValidator {
    private final static Pattern pattern = Pattern.compile("\\Ahttps?://[a-zA-Z0-9\\-.]+(|/.*)\\Z");

    public HttpUrlValidator() {
        super(pattern);
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.HTTP_URL.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof HttpUrlValidator;
    }
}
