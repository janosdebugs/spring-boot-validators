package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;

/**
 * This validator takes Swaggers `@ApiModelProperty` and interprets its `allowableValues` field if it has the format
 * of comma separated values and turns it into an enum validation.
 */
@Service
public class ApiModelPropertyEnumValidatorProvider extends AllowableValuesEnumValidatorProvider<ApiModelProperty> {
    @Override
    public List<Validator> provide(@Nullable final ApiModelProperty annotation, final Class<?> type, final String source) {
        if (
            annotation != null
        ) {
            return getValidators(type, annotation.allowableValues(), source);
        } else {
            return Collections.emptyList();
        }
    }
}
