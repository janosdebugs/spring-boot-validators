package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;

/**
 * This validator takes Swaggers `@ApiParam` and interprets its `allowableValues` field if it has the format
 * of comma separated values and turns it into an enum validation.
 */
@Service
public class ApiParamEnumValidatorProvider extends AllowableValuesEnumValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        ApiParam apiParam = parameter.getAnnotation(ApiParam.class);
        if (
            apiParam != null
        ) {
            return getValidators(parameter, apiParam.allowableValues());
        } else {
            return Collections.emptyList();
        }
    }
}
