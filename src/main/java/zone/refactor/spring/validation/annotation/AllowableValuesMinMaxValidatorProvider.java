package zone.refactor.spring.validation.annotation;

import org.springframework.lang.Nullable;
import zone.refactor.spring.validation.validator.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class AllowableValuesMinMaxValidatorProvider<T extends Annotation> extends AnnotationValidatorProvider<T> {
    private final static Pattern rangePattern = Pattern.compile(
        "\\Arange(\\((?<minRound>-infinity|-?[0-9]+),(?<maxRound>-infinity|-?[0-9]+)\\)|\\[(?<minSquare>-infinity|-?[0-9]+),(?<maxSquare>-infinity|-?[0-9]+)])\\Z"
    );
    final List<Validator> getValidators(Class<?> type, @Nullable String allowableValues) {
        Long minimum = null;
        Long maximum = null;
        List<Validator> validators = new ArrayList<>();
        if (allowableValues != null && !allowableValues.isEmpty()) {
            Matcher matcher = rangePattern.matcher(allowableValues);
            if (matcher.matches()) {
                if (matcher.group("minRound") != null && matcher.group("maxRound") != null) {
                    if (!matcher.group("minRound").equalsIgnoreCase("-infinity")) {
                        minimum = Long.parseLong(matcher.group("minRound"));
                    }
                    if (!matcher.group("maxRound").equalsIgnoreCase("infinity")) {
                        maximum = Long.parseLong(matcher.group("maxRound"));
                    }
                } else if (matcher.group("minSquare") != null && matcher.group("maxSquare") != null) {
                    if (!matcher.group("minSquare").equalsIgnoreCase("-infinity")) {
                        minimum = Long.parseLong(matcher.group("minSquare"));
                    }
                    if (!matcher.group("maxSquare").equalsIgnoreCase("infinity")) {
                        maximum = Long.parseLong(matcher.group("maxSquare"));
                    }
                }
            }
        }

        if (CharSequence.class.isAssignableFrom(type)) {
            if (minimum != null) {
                validators.add(new MinimumLengthValidator(minimum));
            }
            if (maximum != null) {
                validators.add(new MaximumLengthValidator(maximum));
            }
        } else {
            if (minimum != null) {
                validators.add(new MinimumValidator(minimum));
            }
            if (maximum != null) {
                validators.add(new MaximumValidator(maximum));
            }
        }
        return validators;
    }
}
