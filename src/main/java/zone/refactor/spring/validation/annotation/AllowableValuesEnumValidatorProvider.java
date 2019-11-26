package zone.refactor.spring.validation.annotation;

import org.springframework.lang.Nullable;
import zone.refactor.spring.validation.validator.InListValidator;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.annotation.Annotation;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

abstract class AllowableValuesEnumValidatorProvider<T extends Annotation> extends AnnotationValidatorProvider<T> {
    final List<Validator> getValidators(
        Class<?> type,
        @Nullable
            String allowableValues,
        final String source) {
        List<Validator> validators = new ArrayList<>();
        if (
            allowableValues != null &&
            !allowableValues.isEmpty() &&
            !allowableValues.startsWith("range(") &&
            !allowableValues.startsWith("range[")
        ) {
            List<String> allowableValuesList = Arrays.asList(allowableValues.split(",", -1));

            List<Object> finalAllowableValues;
            if (Boolean.class.isAssignableFrom(type)) {
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
            } else if (String.class.isAssignableFrom(type)) {
                finalAllowableValues = Arrays.asList((Object[])allowableValues.split(",", -1));
            } else if (BigInteger.class.isAssignableFrom(type)) {
                finalAllowableValues = allowableValuesList.stream().map(
                    value -> {
                        try {
                            return new BigInteger(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for BigInteger: " + value + " on " + source);
                        }
                    }
                ).collect(Collectors.toList());
            } else if (Long.class.isAssignableFrom(type)) {
                finalAllowableValues = allowableValuesList.stream().map(
                    value -> {
                        try {
                            return Long.parseLong(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for long: " + value + " on " + source);
                        }
                    }
                ).collect(Collectors.toList());
            } else if (Integer.class.isAssignableFrom(type)) {
                finalAllowableValues = allowableValuesList.stream().map(
                    value -> {
                        try {
                            return Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for integers: " + value + " on " + source);
                        }
                    }
                ).collect(Collectors.toList());
            } else if (Short.class.isAssignableFrom(type)) {
                finalAllowableValues = allowableValuesList.stream().map(
                    value -> {
                        try {
                            return Short.parseShort(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for short: " + value + " on " + source);
                        }
                    }
                ).collect(Collectors.toList());
            } else if (Byte.class.isAssignableFrom(type)) {
                finalAllowableValues = allowableValuesList.stream().map(
                    value -> {
                        try {
                            return Byte.parseByte(value);
                        } catch (NumberFormatException e) {
                            throw new InvalidParameterException("Invalid value for byte: " + value + " on " + source);
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
