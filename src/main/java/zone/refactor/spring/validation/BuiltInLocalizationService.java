package zone.refactor.spring.validation;

import jdk.internal.joptsimple.internal.Strings;

import java.util.List;

public class BuiltInLocalizationService implements LocalizationService {
    private BuiltInError getBuiltInError(String key) {
        for (BuiltInError error : BuiltInError.values()) {
            if (error.getLocalizationKey().equalsIgnoreCase(key)) {
                return error;
            }
        }
        return null;
    }

    private String replace(String text, Object... values) {
        return String.format(text, values);
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
                return "Please enter a valid UUID.";
            case EMAIL:
                return "Please enter a valid e-mail address.";
            case IN_LIST:
                //noinspection unchecked
                return "Please enter one of the following: " + Strings.join((List<String>)parameters[0], ", ");
            case INTEGER:
                return "Please enter whole number.";
            case MAXIMUM:
                return replace("Please enter a number smaller or equal than %d.", parameters);
            case MINIMUM:
                return replace("Please enter a number larger or equal than %d.", parameters);
            case PATTERN:
                return replace("Please enter a text matching the pattern %s.", parameters);
            case HTTP_URL:
                return "Please enter a valid web URL.";
            case REQUIRED:
                return "Please fill in this field.";
            case EXACT_VALUE:
                return "Please enter " + parameters[0];
            case SINGLE_LINE:
                return "Please enter a single line.";
            case MAXIMUM_LENGTH:
                return replace("Please enter at most %d characters.", parameters);
            case MINIMUM_LENGTH:
                return replace("Please enter at least %d characters.", parameters);
            case DOMAIN_NAME_FORMAL:
                return "Please enter a valid domain name.";
            default:
                return key;
        }
    }
}
