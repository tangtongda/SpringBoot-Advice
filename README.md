## 代码仓库

https://github.com/tangtongda/springboot-advice.git

> 参考依据：https://github.com/purgeteam/unified-dispose-springboot
> 由于使用他人提供的方法发生了一些问题，比如：
> 
>  1. 返回string时报Class cast exception 
>  2. 部分包下面的异常无法正常捕获 
>  3. 异常处理不能满足业务需要等等

## 如何使用
### 1.maven
本人提交的Sonatype的审核还没有通过，暂不支持该种方式，如果通过会传到Sonatype maven仓库中。
但是maven依赖的方式无法自定义，比如我将错误状态码的业务异常定义为4000，如果你想改，那么maven依赖的方式不可能并不适合你，请看第二种。
### 2.自定义（推荐）
 1. 将代码拉到本地 git clone https://github.com/tangtongda/springboot-advice.git
 2. 根据需要进行修改，推到自己公司的maven私服仓库（如果没有私服仓库，也可以maven install后直接依赖jar包，不过注意版本控制。）
##### maven依赖的方式
注意修改私服地址

```javascript
<!-- maven 私服配置 -->
<distributionManagement>
    <repository>
        <id>nexus.releases</id>
        <url>你的私服域名/repository/releases/</url>
        <uniqueVersion>false</uniqueVersion>
    </repository>
    <snapshotRepository>
        <id>nexus.snapshots</id>
        <url>你的私服域名/repository/snapshots/</url>
        <uniqueVersion>false</uniqueVersion>
    </snapshotRepository>
</distributionManagement>
```
如果项目已经微服务化，将依赖添加到公共依赖中保证所有服务都可以用，普通springboot项目直接即可
```javascript
<dependency>
    <groupId>com.tino</groupId>
    <artifactId>advice</artifactId>
    <version>1.0.0-RELEASE</version>
</dependency>
```
##### jar包方式
mvn install 打包，在需要依赖的depencies中添加该jar包

##### 开始使用
启动上添加@EnableGlobalDispose开启统一处理
```java
/**
 * 核心服务启动类
 *
 * @author tino
 * @date 2019/7/31
 */
@EnableGlobalDispose
@SpringBootApplication
public class CoreApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CoreApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}

```
添加或在已有的配置类上继承WebConfiguration 
```java
/**
 * 启动配置，目的是利用WebConfiguration 中的configureMessageConverters来处理String类型的异常
 *
 * @author tino
 * @date 2019/8/2
 */
@Configuration
public class CoreConfiguration extends WebConfiguration implements WebMvcConfigurer  {
}
```
**异常处理：添加BusinessExceptionAdvice 用户处理全局异常**
*注意：之所以需要建该类的原因是包路径的问题，由于BaseExceptionAdvice 所在的位置是在我定义的com.tino.advice.starter.advice下，如果你想捕获你的包下面的异常，那么必须在你的包下面继承BaseExceptionAdvice，否则实测无法正确捕获异常。或者也可以把我的代码拉到本地，修改包路径和你一样，那么也可以正确捕获*
```java
/**
 * 基础全局异常处理
 *
 * @author tino
 * @date 2020/1/2
 */
@RestControllerAdvice
public class BusinessExceptionAdvice extends BaseExceptionAdvice {
}

```
**（非必须）添加BusinessErrorCode异常处理code、message枚举类**
*如果你需要扩展业务异常的code和message，那么你必须做这一步*

```java
/**
 * 异常
 *
 * @author tino
 * @date 2019/7/31
 */
public enum BusinessErrorCode {
    
    SG_USER_LOGIN_ERR(4000, "登录异常"),
    ;

    private Integer code;
    private String message;

    BusinessErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
```

**如何抛出异常**

```java
// 抛出通用异常
BaseException.exception(BaseErrorCode.PARAM_ERROR);
// 抛出自定义message的业务异常
BaseException.businessException(BusinessErrorCode.SG_USER_LOGIN_ERR.getMessage());
// 抛出自定义code和message的异常
BaseException.exception(BusinessErrorCode.SG_NO_LOGIN_ERR.getCode(), BusinessErrorCode.SG_NO_LOGIN_ERR.getMessage());

```

## 功能说明
项目结构
```yaml
│  .gitignore
│  pom.xml
│  README.md
│  
├─src
│  └─main
│      ├─java
│      │  └─com
│      │      └─tino
│      │          └─advice
│      │              │  AdviceApplication.java
│      │              │  
│      │              └─starter
│      │                  ├─advice
│      │                  │      BaseExceptionAdvice.java
│      │                  │      BaseResponseAdvice.java
│      │                  │      
│      │                  ├─annotation
│      │                  │      EnableGlobalDispose.java
│      │                  │      IgnoreResponseAdvice.java
│      │                  │      
│      │                  ├─config
│      │                  │      DefaultConfiguration.java
│      │                  │      DefaultProperties.java
│      │                  │      WebConfiguration.java
│      │                  │      
│      │                  ├─exception
│      │                  │      BaseErrorCode.java
│      │                  │      BaseException.java
│      │                  │      
│      │                  └─response
│      │                          BaseResponse.java
│      │                          
│      └─resources
│              application.yml
│              dispose.properties
│              
└─target
```
## 简要说明
#### 1.数据结构类说明
 ***BaseResponse.java*** 为统一返况：正常返回和失败返回
 
 **成功**
```javascript
{
    "code":200,
    "message":null,
    "success":true,
    "data":null
}
```
**失败**
```javascript
{
    "code":500,// http异常和业务异常
    "message":null,// 自定义的message
    "success":false,
    "data":null // 失败时数据不返回
}
```
这里由于GET请求通常不会处理请求结果中的message，所以成功的message直接返回null，如果有需要应该由前端来定义用户看到的message

***BaseErrorCodeEnum.java*** 为自定义的异常code、message基类，可以根据需要扩展
***BaseException.java*** 为RuntimeException的子类，用来提供自定义抛出异常的方法


#### 1.统一返回结果包装
原理是通过@RestControllerAdvice注解和ResponseBodyAdvice中的beforeBodyWrite方法来拦截controller层的返回结果并包装成自己想要的样子。

```java
/**
 * 统一返回包装器
 *
 * @author tino
 * @date 2020/1/2
 */
@RestControllerAdvice
public class BaseResponseAdvice implements ResponseBodyAdvice<Object> {

    private DefaultProperties defaultProperties;

    public BaseResponseAdvice(DefaultProperties defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return filter(methodParameter);
    }

    @Nullable
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        // 如果手动进行了返回封装，判断类型防止二次封装
        if (!(o instanceof BaseResponse)) {
            return BaseResponse.succeed(o);
        }
        return o;
    }

    /**
     * 用来过滤不需要包装返回参数的接口
     *
     * @param methodParameter
     * @return
     */
    private Boolean filter(MethodParameter methodParameter) {
        Class<?> declaringClass = methodParameter.getDeclaringClass();
        // 检查过滤包路径
        long count = defaultProperties.getAdviceFilterPackage().stream()
                .filter(l -> declaringClass.getName().contains(l)).count();
        if (count > 0) {
            return false;
        }
        // 检查<类>过滤列表
        if (defaultProperties.getAdviceFilterClass().contains(declaringClass.getName())) {
            return false;
        }
        // 检查注解是否存在
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        return !methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class);
    }
}
```

#### 2.异常统一处理
同样的原理，通过@RestControllerAdvice注解拦截所有controller，然后通过@ExceptionHandler来处理异常并返回指定结果，异常类型可以根据自身需要添加，因为考虑到返回体的包装结果大部分都是前端开发去看，所以我只处理了HTTP的常见异常，至于RPC类型的异常，我把它交给500异常统一捕获（Exception.class）。

```java
/**
 * 基础全局异常处理
 *
 * @author tino
 * @date 2020/1/2
 */
@RestControllerAdvice
@ResponseBody
public class BaseExceptionAdvice {

    private static Logger logger = LoggerFactory.getLogger(BaseExceptionAdvice.class);

    /**
     * 处理其他所以未知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    public BaseResponse globalExceptionHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.fail(BaseErrorCode.EXCEPTION);
    }

    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({BaseException.class})
    public BaseResponse businessExceptionHandler(BaseException e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.fail(e.getCode(), e.getMessage());
    }

    /**
     * 404 异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse handlerNoHandlerFoundException(NoHandlerFoundException e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.fail(BaseErrorCode.NOT_FOUND);
    }

    /**
     * 405 异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse handlerHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.fail(BaseErrorCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 415 异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public BaseResponse handlerHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.fail(BaseErrorCode.UNSUPPORTED_MEDIA_TYPE);
    }
}
```
#### 3.处理String类型转换错误异常
当返回String时，会发生*BaseResponse cannot cast to String*
```java
/**
 * 重写configureMessageConverters，处理字符串返回cannot be cast to java.lang.String错误
 *
 * @author tino
 * @date 2020/1/2
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }
}

```

#### 4.自定义注解的作用
***EnableGlobalDispose*** 的作用是用来开启全局统一返回处理，需要加在springboot启动类上
***IgnoreResponseAdvice*** 的作用是忽略不需要封装返回结果的接口，例如【微信公众号开发】，【微信支付回调】，【支付宝支付回调】等等需要获取我方接口返回结果的三方接口。
**切记！！否则可能发生调试不通或请求阻塞的情况**

```java
    /**
     * GET请求校验开发者
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/callback")
    @IgnoreResponseAdvice
    public Long checkToken(HttpServletRequest request) {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (WXPublicSignUtils.checkSignature(signature, timestamp, nonce, token)) {
            return Long.parseLong(echostr);
        }
        return 0L;
    }
```
