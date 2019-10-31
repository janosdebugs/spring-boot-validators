package zone.refactor.spring.validation.annotation;

import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.MinimumLengthValidator;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotBlankValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        NotBlank annotation = parameter.getAnnotation(NotBlank.class);
        List<Validator> validators = new ArrayList<>();
        if (annotation != null && parameter.getType().isAssignableFrom(CharSequence.class)) {
            validators.add(new RequiredValidator());
            validators.add(new MinimumLengthValidator(1));
        }
        return validators;
    }
}
