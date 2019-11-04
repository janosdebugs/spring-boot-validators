package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@RestController
public class TestController {
    @RequestMapping("/min")
    public void min(
        @RequestParam
        @Min(3)
        int value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/max")
    public void max(
        @RequestParam
        @Max(3)
        int value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/pattern")
    public void max(
        @RequestParam
        @Pattern(regexp = "\\A[a-z]+\\Z")
        String value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/api-param-allow-empty")
    public void allowEmpty(
        @ApiParam(allowEmptyValue = false, required = false)
        String value
    ) {
        System.out.print(value);
    }


    @RequestMapping("/api-param-required")
    public void apiParamRequired(
        @ApiParam(required = true, allowEmptyValue = true)
            String value
    ) {
        System.out.print(value);
    }
}
