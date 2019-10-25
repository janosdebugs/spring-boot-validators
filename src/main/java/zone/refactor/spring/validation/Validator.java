package zone.refactor.spring.validation;

import org.springframework.lang.Nullable;

public interface Validator<ERROR_TYPE> {
    /**
     * @return a unique error key object. This can be either directly returned, or internally provided by a TypeService
     *         implementation.
     */
    ERROR_TYPE getErrorKey();

    /**
     * @return a user-readable error description to be rendered in a form. Please consider using a LocalizationService
     *         implementation to generate it.
     */
    String getDescription();

    /**
     * @param value the value to be checked
     * @return true if the value is valid, false otherwise. Note that `null` should be considered valid, unless the
     *         validator specifically tests if a field is filled out.
     */
    boolean isValid(@Nullable Object value);
}
