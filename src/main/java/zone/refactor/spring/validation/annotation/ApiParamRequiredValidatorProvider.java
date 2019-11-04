package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * This validator takes Swaggers `@ApiModelProperty` and interprets its `required` field as not-null validation.
 */
@Service
public class ApiParamRequiredValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        ApiParam apiModelProperty = parameter.getAnnotation(ApiParam.class);
        List<Validator> validators = new ArrayList<>();
        if (apiModelProperty != null && apiModelProperty.required()) {
            validators.add(new RequiredValidator());
        }
        return validators;
    }
}
