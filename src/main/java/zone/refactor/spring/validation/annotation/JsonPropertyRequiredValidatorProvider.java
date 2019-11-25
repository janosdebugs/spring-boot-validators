package zone.refactor.spring.validation.annotation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * This validator takes the `@JsonProperty` annotation and interprets its `required`
 * field as a not-null requirement.
 */
@Service
public class JsonPropertyRequiredValidatorProvider extends AnnotationValidatorProvider<JsonProperty> {
    @Override
    public List<Validator> provide(@Nullable final JsonProperty annotation, final Class<?> type, final String source) {
        List<Validator> validators = new ArrayList<>();
        if (annotation != null && annotation.required()) {
            validators.add(new RequiredValidator());
        }
        return validators;
    }
}
