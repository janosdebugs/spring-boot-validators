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
 * of range() or range[]. The minimum and maximum values, if not -infinity and infinity respectively, are interpreted
 * either as a minimum and maximum number, or as a string length.
 */
@Service
public class ApiParamMinMaxValidatorProvider extends AllowableValuesMinMaxValidatorProvider<ApiParam> {
    @Override
    List<Validator> provide(@Nullable final ApiParam annotation, final Class<?> type, final String source) {
        if (annotation != null) {
            return this.getValidators(type, annotation.allowableValues());
        } else {
            return Collections.emptyList();
        }
    }
}
