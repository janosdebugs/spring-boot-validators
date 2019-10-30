package zone.refactor.spring.validation.annotation;

import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.MinimumLengthValidator;
import zone.refactor.spring.validation.validator.MinimumValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.Min;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MinValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        Min min = parameter.getAnnotation(Min.class);

        List<Validator> validators = new ArrayList<>();
        if (min != null) {
            if (parameter.getType().isAssignableFrom(String.class)) {
                validators.add(new MinimumLengthValidator(min.value()));
            } else {
                validators.add(new MinimumValidator(min.value()));
            }
        }
        return validators;
    }
}
