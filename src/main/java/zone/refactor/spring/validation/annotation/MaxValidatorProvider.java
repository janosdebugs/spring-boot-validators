package zone.refactor.spring.validation.annotation;

import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.MaximumLengthValidator;
import zone.refactor.spring.validation.validator.MaximumValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.Max;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
                parameter.getType().isAssignableFrom(CharSequence.class) ||
                parameter.getType().isAssignableFrom(Collection.class) ||
                parameter.getType().isAssignableFrom(Map.class)
            ) {
                validators.add(new MaximumLengthValidator(max.value()));
            } else {
                validators.add(new MaximumValidator(max.value()));
            }
        }
        return validators;
    }
}
