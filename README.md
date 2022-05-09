# SpringBoot Response & Exception Advice

## Maven Dependency
### XML

```xml

<dependency>
    <groupId>com.github.tangtongda</groupId>
    <artifactId>advice-starter</artifactId>
    <version>${advice-starter-version}</version>
</dependency>

```
### Version
   1. `1.0.0` First released version.

## How to integrate it?

1. Add @EnableGlobalDispose to SpringBootApplication class(required).
2. Add SDK config in application.yml/yaml(additional)
3. Configuration Description

```yaml
advice:
  # exclude packages
  exclude-packages: com.tangtongda
  # exclude classes for example: restFul controller class
  exclude-classes: DemoController2

```

## Use

### Packaging Controller Response

1. You are no need to write any extra code to use the response but just return.
2. Throw exception
    1. Exception contains 2 types: BaseException,BizException.
    2. Throw BaseException, for example:

```java
    throw new BaseException(CommonErrorCode.API_GATEWAY_ERROR);
```

3. And you will get the response:

```json
{
  "data": {
    "name": "tino",
    "age": 30
  },
  "code": 200,
  "message": null
}
```

### Throw BizException.

1. Create an Enum 'BizExceptionEnum' or any name you like, for example:

```java
public enum BizExceptionEnum {
    // Custom your business exception enum
    USER_AUTH_ERROR(4001, "User authorization error."),
    ;

    private final int code;
    private final String message;

    BizExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}      
```

2. Create a Class 'BizException' or any name you like, for example:

```java
public class BizException extends RuntimeException {

    public BizException(Integer code, String msg) {
        throw new BaseException(code, msg);
    }

    public BizException(BizExceptionEnum bizExceptionEnum) {
        throw new BaseException(bizExceptionEnum.getCode(), bizExceptionEnum.getMessage());
    }
}
```

3. Throw Business Exception.

```java
    throw new BizException(BizExceptionEnum.USER_AUTH_ERROR);
```

4. And you will get this response:

```json
{
  "data": null,
  "code": 4001,
  "message": "User authorization error."
}
```

## Project structure

```yaml
├───starter
│   ├───src
│   │   ├───main
│   │   │   ├───java
│   │   │   │   └───com
│   │   │   │       └───github
│   │   │   │           └───tangtongda
│   │   │   │               └───advice
│   │   │   │                   └───starter
│   │   │   │                       ├───advice
│   │   │   │                       ├───annotation
│   │   │   │                       │   └───exception
│   │   │   │                       ├───properties
│   │   │   │                       └───response
│   │   │   └───resources
│   │   │       └───META-INF
│   │   └───test
│   │       └───java
└───test
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───github
│   │   │           └───tangtongda
│   │   │               └───advice
│   │   │                   └───test
│   │   │                       ├───controller
│   │   │                       ├───entity
│   │   │                       └───exception
│   │   └───resources
│   └───test
│       └───java
│           └───com
│               └───github
│                   └───tangtongda
│                       └───advice
│                           └───test
```

**Attention**

1. You can get some function performances in `Test` module
2. You can run the `com.github.tangtongda.advice.test.DemoControllerTest` to test the advice features.

## Response Format Description

1. Success

```json
{
  "code": 200,
  "message": null,
  "data": null
}
```

2. Exception

```json
{
  "code": 500,
  "message": "",
  "data": null
}
```

## Key Annotations Function Brief

1. @EnableGlobalDispose
    1. It means Response Advice is enabled when you add the annotation.
    2. It must be added on the header of SpringBoot Application Runner Class or load earlier Configuration class.

2. @IgnoreResponseAdvice
    1. It should be added on the header of any Controller Classes or Methods which were no need to packaging response.

```java
/**
 * Here is a Restful api which no need to packaging response.
 */
@RestController
@RequestMapping("/wechat")
public class WechatController {
    /**
     * Validate developer by WeChat
     * It's a WeChat third party http callback api which need a standard response format.
     *
     * @param request http request
     * @return standard text/plain echo response without any other content.
     */
    @GetMapping("/callback")
    @IgnoreResponseAdvice
    public Long checkToken(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (WXPublicSignUtils.checkSignature(signature, timestamp, nonce, token)) {
            return Long.parseLong(echostr);
        }
        return 0L;
    }
}
```

## Possible Errors

1. "BaseResponse cannot cast to String"
    1. Checking the MessageConvertAutoConfiguration was registered correctly.
    2. Or you can customize a MessageConvert by yourself, for example:

```java

@Configuration
public class MessageConvertAutoConfiguration implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Handle any type which you want to add a customized a message convert  
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }
}
```
