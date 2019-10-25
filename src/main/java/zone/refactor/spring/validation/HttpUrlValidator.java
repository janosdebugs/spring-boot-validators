package zone.refactor.spring.validation;

import java.util.regex.Pattern;


public class HttpUrlValidator<ERROR_TYPE> extends PatternValidator<ERROR_TYPE> {
    private final static Pattern pattern = Pattern.compile("\\Ahttps?://[a-zA-Z0-9\\-.]+/.*\\Z");
    private final LocalizationService localizationService;
    private final TypeService<ERROR_TYPE> typeService;

    public HttpUrlValidator(LocalizationService localizationService, TypeService<ERROR_TYPE> typeService) {
        super(pattern, localizationService, typeService);
        this.localizationService = localizationService;
        this.typeService = typeService;
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey("http-url");
    }

    @Override
    public String getDescription() {
        return localizationService.localize("please-enter-valid-http-url");
    }
}