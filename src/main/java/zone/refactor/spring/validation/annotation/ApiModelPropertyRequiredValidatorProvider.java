package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiModelPropertyRequiredValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        ApiModelProperty apiModelProperty = parameter.getAnnotation(ApiModelProperty.class);
        List<Validator> validators = new ArrayList<>();
        if (apiModelProperty != null && apiModelProperty.required()) {
            validators.add(new RequiredValidator());
        }
        return validators;
    }
}
