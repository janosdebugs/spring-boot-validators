package zone.refactor.spring.validation.annotation;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestParamRequiredValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        List<Validator> validators = new ArrayList<>();
        if (requestParam != null && requestParam.required()) {
            validators.add(new RequiredValidator());
        }
        return validators;
    }
}
