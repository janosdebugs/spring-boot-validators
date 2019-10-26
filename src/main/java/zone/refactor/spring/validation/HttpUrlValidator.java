package zone.refactor.spring.validation;

import java.util.regex.Pattern;


public class HttpUrlValidator<ERROR_TYPE> extends PatternValidator<ERROR_TYPE> {
    private final static Pattern pattern = Pattern.compile("\\Ahttps?://[a-zA-Z0-9\\-.]+(|/.*)\\Z");
    private final LocalizationService localizationService;
    private final TypeService<ERROR_TYPE> typeService;

    public HttpUrlValidator(TypeService<ERROR_TYPE> typeService, LocalizationService localizationService) {
        super(pattern, typeService, localizationService);
        this.localizationService = localizationService;
        this.typeService = typeService;
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey(BuiltInError.HTTP_URL.toString());
    }

    @Override
    public String getDescription() {
        return localizationService.localize(BuiltInError.HTTP_URL.getLocalizationKey());
    }
}
