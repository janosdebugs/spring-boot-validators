package zone.refactor.spring.validation.annotation;

import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.MinimumValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.Min;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * This validator takes the `@Min` annotation and turns it into either a numeric minimum validator, or if the target
 * is a string, the minimum length.
 */
@Service
public class MinValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        Min min = parameter.getAnnotation(Min.class);

        List<Validator> validators = new ArrayList<>();
        if (min != null) {
            if (
                BigDecimal.class.isAssignableFrom(parameter.getType()) ||
                BigInteger.class.isAssignableFrom(parameter.getType()) ||
                Byte.class.isAssignableFrom(parameter.getType()) ||
                Short.class.isAssignableFrom(parameter.getType()) ||
                Integer.class.isAssignableFrom(parameter.getType()) ||
                Long.class.isAssignableFrom(parameter.getType()) ||
                byte.class.isAssignableFrom(parameter.getType()) ||
                short.class.isAssignableFrom(parameter.getType()) ||
                int.class.isAssignableFrom(parameter.getType()) ||
                long.class.isAssignableFrom(parameter.getType())
            ) {
                validators.add(new MinimumValidator(min.value()));
            }
        }
        return validators;
    }
}
