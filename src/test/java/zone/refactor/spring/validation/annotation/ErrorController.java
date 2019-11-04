package zone.refactor.spring.validation.annotation;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@ControllerAdvice
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping(
            value = "/error",
            produces = "application/json"
    )
    public ResponseEntity<Void> errorJson(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            return new ResponseEntity<>(HttpStatus.valueOf((int)status));
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { ValidationException.class })
    public ResponseEntity<ValidationException> onException(ValidationException apiException) {
        return new ResponseEntity<>(
            apiException,
            HttpStatus.BAD_REQUEST
        );
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
