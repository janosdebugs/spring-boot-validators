package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This validator takes Swaggers `@ApiModelProperty` and interprets its `allowableValues` field if it has the format
 * of range() or range[]. The minimum and maximum values, if not -infinity and infinity respectively, are interpreted
 * either as a minimum and maximum number, or as a string length.
 */
@Service
public class ApiModelPropertyMinMaxValidatorProvider extends AllowableValuesMinMaxValidatorProvider<ApiModelProperty> {
    @Override
    List<Validator> provide(@Nullable final ApiModelProperty annotation, final Class<?> type, final String source) {
        if (annotation != null) {
            return this.getValidators(type, annotation.allowableValues());
        } else {
            return Collections.emptyList();
        }
    }
}
