package zone.refactor.spring.validation.annotation;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsonPropertyRequiredValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        JsonProperty jsonProperty = parameter.getAnnotation(JsonProperty.class);
        List<Validator> validators = new ArrayList<>();
        if (jsonProperty != null && jsonProperty.required()) {
            validators.add(new RequiredValidator());
        }
        return validators;
    }
}
