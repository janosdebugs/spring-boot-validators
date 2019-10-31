package zone.refactor.spring.validation.annotation;

import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.MinimumLengthValidator;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.NotEmpty;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class NotEmptyValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        NotEmpty annotation = parameter.getAnnotation(NotEmpty.class);
        List<Validator> validators = new ArrayList<>();
        if (annotation != null) {
            validators.add(new RequiredValidator());
            if (
                parameter.getType().isAssignableFrom(CharSequence.class) ||
                parameter.getType().isAssignableFrom(Collection.class) ||
                parameter.getType().isAssignableFrom(Map.class) ||
                parameter.getType().isArray()
            ) {
                validators.add(new MinimumLengthValidator(1));
            }
        }
        return validators;
    }
}
