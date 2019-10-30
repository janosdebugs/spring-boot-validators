package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;

public interface Validator {
    /**
     * @return a unique error key object. This can be either directly returned, or internally provided by a TypeService
     *         implementation.
     */
    String getErrorKey();

    /**
     * @param value the value to be checked
     * @return true if the value is valid, false otherwise. Note that `null` should be considered valid, unless the
     *         validator specifically tests if a field is filled out.
     */
    boolean isValid(@Nullable Object value);
}
