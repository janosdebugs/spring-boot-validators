package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.Validator;

import javax.annotation.Nullable;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;

/**
 * This validator takes Swaggers `@ApiParam` and interprets its `allowableValues` field if it has the format
 * of comma separated values and turns it into an enum validation.
 */
@Service
public class ApiParamEnumValidatorProvider extends AllowableValuesEnumValidatorProvider<ApiParam> {
    @Override
    List<Validator> provide(@Nullable final ApiParam annotation, final Class<?> type, final String source) {
        if (
            annotation != null
        ) {
            return getValidators(type, annotation.allowableValues(), source);
        } else {
            return Collections.emptyList();
        }
    }
}
