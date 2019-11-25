package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.annotation.Nullable;
import javax.validation.constraints.AssertTrue;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * This validator takes Swaggers `@ApiModelProperty` and interprets its `required` field as not-null validation.
 */
@Service
public class ApiParamRequiredValidatorProvider extends AnnotationValidatorProvider<ApiParam> {
    @Override
    List<Validator> provide(@Nullable final ApiParam annotation, final Class<?> type, final String source) {
        List<Validator> validators = new ArrayList<>();
        if (annotation != null && annotation.required()) {
            validators.add(new RequiredValidator());
        }
        return validators;
    }
}
