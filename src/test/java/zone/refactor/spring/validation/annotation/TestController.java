package zone.refactor.spring.validation.annotation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigInteger;

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

    @RequestMapping("/api-param-enum")
    public void apiParamEnum(
        @ApiParam(allowableValues = "asdf,xcvb")
            String value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/api-param-enum-boolean")
    public void apiParamEnum(
        @ApiParam(allowableValues = "true")
            Boolean value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/api-param-enum-false")
    public void apiParamEnumFalse(
        @ApiParam(allowableValues = "false")
            Boolean value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/api-param-enum-integer")
    public void apiParamInteger(
        @ApiParam(allowableValues = "3,4,5")
            Integer value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/api-param-enum-bigint")
    public void apiParamInteger(
        @ApiParam(allowableValues = "3,4,5")
            BigInteger value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/api-param-enum-long")
    public void apiParamLong(
        @ApiParam(allowableValues = "3,4,5")
            Long value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/api-param-enum-short")
    public void apiParamShort(
        @ApiParam(allowableValues = "3,4,5")
            Short value
    ) {
        System.out.print(value);
    }


    @RequestMapping("/api-param-enum-byte")
    public void apiParamByte(
        @ApiParam(allowableValues = "3,4,5")
            Byte value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/api-param-minmax")
    public void minMax(
        @ApiParam(allowableValues = "range(2,4)")
            Integer value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/api-param-minmax-square")
    public void minMaxSquare(
        @ApiParam(allowableValues = "range[2,4]")
            Integer value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/api-param-minmax-string")
    public void minMaxString(
        @ApiParam(allowableValues = "range[2,4]")
            String value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/assert-false")
    public void assertFalse(
        @RequestParam
        @AssertFalse
            Boolean value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/assert-true")
    public void assertTrue(
        @RequestParam
        @AssertTrue
            Boolean value
    ) {
        System.out.print(value);
    }

    @RequestMapping("/subobject")
    public void subobject(
        @RequestBody
        @Valid
        TestRequest request
    ) {
        System.out.println(request);
    }

    public static class TestRequest {
        @ApiModelProperty(required = true)
        public final String test;

        public TestRequest(
            @JsonProperty(value = "test")
            String test
        ) {
            this.test = test;
        }
    }
}
