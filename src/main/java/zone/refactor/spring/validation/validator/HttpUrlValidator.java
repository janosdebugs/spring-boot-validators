package zone.refactor.spring.validation.validator;

import zone.refactor.spring.validation.localization.LocalizationService;

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
}
