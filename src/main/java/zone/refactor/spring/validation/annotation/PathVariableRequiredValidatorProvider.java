package zone.refactor.spring.validation.annotation;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PathVariableRequiredValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
        List<Validator> validators = new ArrayList<>();
        if (pathVariable != null && pathVariable.required()) {
            validators.add(new RequiredValidator());
        }
        return validators;
    }
}
