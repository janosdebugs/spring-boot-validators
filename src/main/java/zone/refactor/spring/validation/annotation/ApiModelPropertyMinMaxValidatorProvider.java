package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;

/**
 * This validator takes Swaggers `@ApiModelProperty` and interprets its `allowableValues` field if it has the format
 * of range() or range[]. The minimum and maximum values, if not -infinity and infinity respectively, are interpreted
 * either as a minimum and maximum number, or as a string length.
 */
@Service
public class ApiModelPropertyMinMaxValidatorProvider extends AllowableValuesMinMaxValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        ApiModelProperty apiModelProperty = parameter.getAnnotation(ApiModelProperty.class);
        if (apiModelProperty != null) {
            return this.getValidators(parameter, apiModelProperty.allowableValues());
        } else {
            return Collections.emptyList();
        }
    }
}
