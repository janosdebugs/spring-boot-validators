package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * This validator takes Swaggers `@ApiModelProperty` and interprets its `required` field as not-null validation.
 */
@Service
public class ApiModelPropertyRequiredValidatorProvider extends AnnotationValidatorProvider<ApiModelProperty> {
    @Override
    public List<Validator> provide(@Nullable final ApiModelProperty annotation, final Class<?> type, final String source) {
        List<Validator> validators = new ArrayList<>();
        if (annotation != null && annotation.required()) {
            validators.add(new RequiredValidator());
        }
        return validators;
    }
}
