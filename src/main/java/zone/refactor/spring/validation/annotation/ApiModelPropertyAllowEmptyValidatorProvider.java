package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.MinimumLengthValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * This validator takes the Swagger `@ApiModelProperty` annotation and interprets its `allowEmptyValue` field as a
 * MinimumLengthValidator with the value of 1.
 */
@Service
public class ApiModelPropertyAllowEmptyValidatorProvider extends AnnotationValidatorProvider<ApiModelProperty> {
    public List<Validator> provide(@Nullable final ApiModelProperty apiModelProperty, Class<?> type, String source) {
        List<Validator> validators = new ArrayList<>();
        if (apiModelProperty != null && !apiModelProperty.allowEmptyValue() && type.isAssignableFrom(String.class)) {
            validators.add(new MinimumLengthValidator(1));
        }
        return validators;
    }
}
