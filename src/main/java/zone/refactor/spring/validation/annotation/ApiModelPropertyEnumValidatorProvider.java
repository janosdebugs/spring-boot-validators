package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.InListValidator;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This validator takes Swaggers `@ApiModelProperty` and interprets its `allowableValues` field if it has the format
 * of comma separated values and turns it into an enum validation.
 */
@Service
public class ApiModelPropertyEnumValidatorProvider extends AnnotationValidatorProvider<ApiModelProperty> {
    @Override
    public List<Validator> provide(@Nullable final ApiModelProperty apiModelProperty, final Class<?> type, String source) {
        List<Validator> validators = new ArrayList<>();
        if (
            apiModelProperty != null &&
            !apiModelProperty.allowableValues().isEmpty() &&
            !apiModelProperty.allowableValues().startsWith("range(") &&
            !apiModelProperty.allowableValues().startsWith("range[")
        ) {
            List<String> allowableValues = Arrays.asList(apiModelProperty.allowableValues().split(",", -1));

            List<Object> finalAllowableValues;
            if (type.isAssignableFrom(Boolean.class)) {
                finalAllowableValues = allowableValues.stream().map(
                    value -> {
                        if (value.equalsIgnoreCase("true")) {
                            return true;
                        } else if (value.equalsIgnoreCase("false")) {
                            return false;
                        } else {
                            throw new InvalidParameterException("Invalid annotation value for boolean: " + value);
                        }
                    }
                ).collect(Collectors.toList());
            } else if (type.isAssignableFrom(Integer.class)) {
                finalAllowableValues = allowableValues.stream().map(
                    value -> {
                        try {
                            return Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for integers: " + value + " on " + source);
                        }
                    }
                ).collect(Collectors.toList());
            } else if (type.isAssignableFrom(Short.class)) {
                finalAllowableValues = allowableValues.stream().map(
                    value -> {
                        try {
                            return Short.parseShort(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for short: " + value + " on " + source);
                        }
                    }
                ).collect(Collectors.toList());
            } else if (type.isAssignableFrom(Byte.class)) {
                finalAllowableValues = allowableValues.stream().map(
                    value -> {
                        try {
                            return Byte.parseByte(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for byte: " + value + " on " + source);
                        }
                    }
                ).collect(Collectors.toList());
            } else if (type.isAssignableFrom(Long.class)) {
                finalAllowableValues = allowableValues.stream().map(
                    value -> {
                        try {
                            return Long.parseLong(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for long: " + value + " on " + source);
                        }
                    }
                ).collect(Collectors.toList());
            } else {
                throw new InvalidParameterException("Unsupported data type for enum validation: " + type.getSimpleName() + " on " + source);
            }

            validators.add(new InListValidator(finalAllowableValues));
        }
        return validators;
    }
}
