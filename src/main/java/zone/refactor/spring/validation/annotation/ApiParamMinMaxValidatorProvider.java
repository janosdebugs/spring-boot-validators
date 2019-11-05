package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;

/**
 * This validator takes Swaggers `@ApiParam` and interprets its `allowableValues` field if it has the format
 * of range() or range[]. The minimum and maximum values, if not -infinity and infinity respectively, are interpreted
 * either as a minimum and maximum number, or as a string length.
 */
@Service
public class ApiParamMinMaxValidatorProvider extends AllowableValuesMinMaxValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        ApiParam apiParam = parameter.getAnnotation(ApiParam.class);
        if (apiParam != null) {
            return this.getValidators(parameter, apiParam.allowableValues());
        } else {
            return Collections.emptyList();
        }
    }
}
