package zone.refactor.spring.validation.annotation;

import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.MaximumValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.Max;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * This validator takes the `@Max` annotation and turns it into either a numeric maximum validator, or if the target
 * is a string, the maximum length.
 */
@Service
public class MaxValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        Max max = parameter.getAnnotation(Max.class);
        List<Validator> validators = new ArrayList<>();
        if (max != null) {
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
                validators.add(new MaximumValidator(max.value()));
            }
        }
        return validators;
    }
}
