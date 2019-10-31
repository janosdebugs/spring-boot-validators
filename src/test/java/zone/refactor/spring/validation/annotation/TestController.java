package zone.refactor.spring.validation.annotation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@RestController
public class TestController {
    @RequestMapping("/min")
    public void minTest(
        @RequestParam
        @Min(3)
        int value
    ) {

    }
}
