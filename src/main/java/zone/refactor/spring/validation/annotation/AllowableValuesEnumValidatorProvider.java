package zone.refactor.spring.validation.annotation;

import org.springframework.lang.Nullable;
import zone.refactor.spring.validation.validator.InListValidator;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

abstract class AllowableValuesEnumValidatorProvider implements ValidatorProvider {
    final List<Validator> getValidators(
        Parameter parameter,
        @Nullable
            String allowableValues
    ) {
        List<Validator> validators = new ArrayList<>();
        if (
            allowableValues != null &&
            !allowableValues.isEmpty() &&
            !allowableValues.startsWith("range(") &&
            !allowableValues.startsWith("range[")
        ) {
            List<String> allowableValuesList = Arrays.asList(allowableValues.split(",", -1));

            List<Object> finalAllowableValues;
            if (Boolean.class.isAssignableFrom(parameter.getType())) {
                finalAllowableValues = allowableValuesList.stream().map(
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
            } else if (String.class.isAssignableFrom(parameter.getType())) {
                finalAllowableValues = Arrays.asList(allowableValues.split(",", -1));
            } else if (BigInteger.class.isAssignableFrom(parameter.getType())) {
                finalAllowableValues = allowableValuesList.stream().map(
                    value -> {
                        try {
                            return new BigInteger(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for BigInteger: " + value + " on field " + parameter.getName());
                        }
                    }
                ).collect(Collectors.toList());
            } else if (Long.class.isAssignableFrom(parameter.getType())) {
                finalAllowableValues = allowableValuesList.stream().map(
                    value -> {
                        try {
                            return Long.parseLong(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for long: " + value + " on field " + parameter.getName());
                        }
                    }
                ).collect(Collectors.toList());
            } else if (Integer.class.isAssignableFrom(parameter.getType())) {
                finalAllowableValues = allowableValuesList.stream().map(
                    value -> {
                        try {
                            return Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for integers: " + value + " on field " + parameter.getName());
                        }
                    }
                ).collect(Collectors.toList());
            } else if (Short.class.isAssignableFrom(parameter.getType())) {
                finalAllowableValues = allowableValuesList.stream().map(
                    value -> {
                        try {
                            return Short.parseShort(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for short: " + value + " on field " + parameter.getName());
                        }
                    }
                ).collect(Collectors.toList());
            } else if (Byte.class.isAssignableFrom(parameter.getType())) {
                finalAllowableValues = allowableValuesList.stream().map(
                    value -> {
                        try {
                            return Byte.parseByte(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for byte: " + value + " on field " + parameter.getName());
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
