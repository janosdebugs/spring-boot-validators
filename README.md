# Entity validation for Spring/Spring Boot

[![CircleCI](https://img.shields.io/circleci/build/gh/refactorzone/spring-boot-validators)](https://circleci.com/gh/refactorzone/spring-boot-validators)
[![Maven Central](https://img.shields.io/maven-central/v/zone.refactor.spring/validation)](https://search.maven.org/search?q=g:zone.refactor.spring%20AND%20a:validation)
[![Code Quality](https://img.shields.io/lgtm/grade/java/g/refactorzone/spring-boot-validators.svg)](https://lgtm.com/projects/g/refactorzone/spring-boot-validators/)
[![GitHub last commit](https://img.shields.io/github/last-commit/refactorzone/spring-boot-validators)](https://github.com/refactorzone/spring-boot-validators)
[![GitHub top language](https://img.shields.io/github/languages/top/refactorzone/spring-boot-validators.svg)](https://github.com/refactorzone/spring-boot-validators)
[![GitHub repo size](https://img.shields.io/github/repo-size/refactorzone/spring-boot-validators.svg)](https://github.com/refactorzone/spring-boot-validators)
[![GitHub issues](https://img.shields.io/github/issues/refactorzone/spring-boot-validators.svg)](https://github.com/refactorzone/spring-boot-validators/issues)
[![GitHub pull requests](https://img.shields.io/github/issues-pr/refactorzone/spring-boot-validators.svg)](https://github.com/refactorzone/spring-boot-validators/pulls)
[![GitHub](https://img.shields.io/github/license/refactorzone/spring-boot-validators)](https://github.com/refactorzone/spring-boot-validators/blob/master/LICENSE.md)

This package provides validation for Spring and Spring Boot by collecting all fields from the input and then
validating them all at once. The returned object can be used to display all errors on a form.

## Installation

This package can be installed from Maven Central:

```xml
<dependency>
    <groupId>zone.refactor.spring</groupId>
    <artifactId>validation</artifactId>
</dependency>
```

## Basic Usage

There are many ways to use this package. The easiest way to use it is to build a validator chain, for example:

```java
ValidatorChain<String> validatorChain = new ValidatorChain<>(
    exceptionFactory
);

validatorChain.addValidator(new RequiredValidator<String>(
    "my-required-field",
    typeService,
    localizationService
));

Map<String, String> data = new HashMap<>();
data.put("my-required-field", "my-value");

//Throws exception if validation fails.
validatorChain.validate(data);
```

Now, there are three dependencies, `exceptionFactory`, `typeService` and `localizationService`.

`exceptionFactory` is a variable of the type `ExceptionFactory`, which is responsible
for creating a validation factory is the validation fails. This is done such that the actual exception type is flexible.

`typeService` is a service that creates an error type. A default implementation called `StringTypeService` is provided
which simply returns the error key string. However, you may want to create an `enum` for all possible errors, which 
you can do using the `EnumTypeService` like this:

```java
TypeService<YourEnum> typeService = new EnumTypeService<>(YourEnum.class);
```

The built-in types are documented in the `BuiltInError` enum.

Finally, the `localizationService` is responsible for creating a user-readable error message. Again, the `BuiltInError`
can be used for possible values of the built in messages. An English localization engine for the error messages is
provided in `BuiltInLocalizationService`.

## Writing validators

If you wish to implement a custom validator, you may do so by implementing the `Validator` interface:

```java
public interface Validator<ERROR_TYPE> {
    ERROR_TYPE getErrorKey();
    String getDescription();
    boolean isValid(@Nullable Object value);
}
```

The `getErrorKey` function is supposed to return an error key object. If you are writing application-specific
validators, feel free to use, for example, your applications-specific `enum` for this purpose. If you are writing a
library, please consider using the `TypeService` for maximum flexibility.

The `getDescription` should return a user-readable description of the validation error. It is strongly recommended
that a `LocalizationService` implementation be used here for multilanguage support.

Finally, the `isValid` method should return true if the passed value is valid. Special attention should be paid that the
value may be a type you don't expect.
