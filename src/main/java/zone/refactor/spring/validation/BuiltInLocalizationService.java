package zone.refactor.spring.validation;

public class BuiltInLocalizationService implements LocalizationService {
    private BuiltInError getBuiltInError(String key) {
        for (BuiltInError error : BuiltInError.values()) {
            if (error.getLocalizationKey().equalsIgnoreCase(key)) {
                return error;
            }
        }
        return null;
    }

    @Override
    public String localize(String key, Object... parameters) {
        BuiltInError builtInError = getBuiltInError(key);
        if (builtInError == null) {
            // Can't translate
            return key;
        }

        switch (builtInError) {
            case UUID:
            case EMAIL:
            case IN_LIST:
            case INTEGER:
            case MAXIMUM:
            case MINIMUM:
            case PATTERN:
            case HTTP_URL:
            case REQUIRED:
            case EXACT_VALUE:
            case SINGLE_LINE:
            case MAXIMUM_LENGTH:
            case MINIMUM_LENGTH:
            case DOMAIN_NAME_FORMAL:
            default:
                return key;
        }
    }
}
