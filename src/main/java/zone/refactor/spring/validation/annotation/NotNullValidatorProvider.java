package zone.refactor.spring.validation.annotation;

import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotNullValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        NotNull notNull = parameter.getAnnotation(NotNull.class);
        List<Validator> validators = new ArrayList<>();
        if (notNull != null) {
            validators.add(new RequiredValidator());
        }
        return validators;
    }
}
