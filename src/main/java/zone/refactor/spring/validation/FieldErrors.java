package zone.refactor.spring.validation;

import java.util.ArrayList;
import java.util.List;

public class FieldErrors<ERROR_TYPE> {
    public final List<ERROR_TYPE> errors;

    public FieldErrors() {
        this.errors = new ArrayList<>();
    }

    public FieldErrors(List<ERROR_TYPE> errors) {
        this.errors = errors;
    }

    public FieldErrors<ERROR_TYPE> withError(ERROR_TYPE error) {
        ArrayList<ERROR_TYPE> list = new ArrayList<>(errors);
        list.add(error);
        return new FieldErrors<>(list);
    }
}
