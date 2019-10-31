package zone.refactor.spring.validation.annotation;

import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.ExactValueValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.AssertFalse;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * This validator takes the `@Max` annotation and turns it into either a numeric maximum validator, or if the target
 * is a string, the maximum length.
 */
@Service
public class AssertFalseValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        AssertFalse annotation = parameter.getAnnotation(AssertFalse.class);
        List<Validator> validators = new ArrayList<>();
        if (annotation != null) {
            validators.add(new ExactValueValidator(false));
        }
        return validators;
    }
}
