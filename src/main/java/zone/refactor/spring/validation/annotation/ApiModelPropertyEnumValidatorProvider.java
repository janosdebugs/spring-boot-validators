package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiModelProperty;
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
public class ApiModelPropertyEnumValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        ApiModelProperty apiModelProperty = parameter.getAnnotation(ApiModelProperty.class);
        List<Validator> validators = new ArrayList<>();
        if (
            apiModelProperty != null &&
            !apiModelProperty.allowableValues().isEmpty() &&
            !apiModelProperty.allowableValues().startsWith("range(") &&
            !apiModelProperty.allowableValues().startsWith("range[")
        ) {
            List<String> allowableValues = Arrays.asList(apiModelProperty.allowableValues().split(",", -1));

            List<Object> finalAllowableValues;
            if (parameter.getType().isAssignableFrom(Boolean.class)) {
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
            } else if (parameter.getType().isAssignableFrom(Integer.class)) {
                finalAllowableValues = allowableValues.stream().map(
                    value -> {
                        try {
                            return Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for integers: " + value + " on field " + parameter.getName());
                        }
                    }
                ).collect(Collectors.toList());
            } else if (parameter.getType().isAssignableFrom(Short.class)) {
                finalAllowableValues = allowableValues.stream().map(
                    value -> {
                        try {
                            return Short.parseShort(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for short: " + value + " on field " + parameter.getName());
                        }
                    }
                ).collect(Collectors.toList());
            } else if (parameter.getType().isAssignableFrom(Byte.class)) {
                finalAllowableValues = allowableValues.stream().map(
                    value -> {
                        try {
                            return Byte.parseByte(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for byte: " + value + " on field " + parameter.getName());
                        }
                    }
                ).collect(Collectors.toList());
            } else if (parameter.getType().isAssignableFrom(Long.class)) {
                finalAllowableValues = allowableValues.stream().map(
                    value -> {
                        try {
                            return Long.parseLong(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for long: " + value + " on field " + parameter.getName());
                        }
                    }
                ).collect(Collectors.toList());
            } else {
                throw new InvalidParameterException("Unsupported data type for enum validation: " + parameter.getType().getSimpleName() + " on field " + parameter.getName());
            }

            validators.add(new InListValidator(finalAllowableValues));
        }
        return validators;
    }
}
