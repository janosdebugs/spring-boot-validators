package zone.refactor.spring.validation.annotation;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestParamRequiredValidatorProvider extends AnnotationValidatorProvider<RequestParam> {
    @Override
    public List<Validator> provide(@Nullable final RequestParam annotation, final Class<?> type, final String source) {
        List<Validator> validators = new ArrayList<>();
        if (annotation != null && annotation.required()) {
            validators.add(new RequiredValidator());
        }
        return validators;
    }
}
