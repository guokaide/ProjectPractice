# ProjectPractice

## Homework 03

### 问题

1. 【必做题】通过 MicroProfile REST Client 实现 POST 接⼝去请求项⽬中的 ShutdownEndpoint，URI：`http://127.0.0.1:8080/actuator/shutdown`

2. 【选做题】完善 my-rest-client 框架 POST ⽅法，实现 `org.geektimes.rest.client.DefaultInvocationBuilder#buildPost `⽅法

### 实现

#### 问题1

见 projects/stage-1/shopizer/sm-shop-model/src/test

引入依赖：

```xml
        <dependency>
            <groupId>org.geekbang.projects</groupId>
            <artifactId>my-configuration</artifactId>
            <version>${revision}</version>
            <scope>test</scope>
        </dependency>

        <!-- Jersey mp rest client -->
        <dependency>
            <groupId>org.glassfish.jersey.ext.microprofile</groupId>
            <artifactId>jersey-mp-rest-client</artifactId>
        </dependency>

        <dependency>
            <groupId>javax.ws.rs</groupId>
            <artifactId>javax.ws.rs-api</artifactId>
            <scope>test</scope>
        </dependency>
```

接口实现：com.salesmanager.rest.service.ActuatorService

```java
@Path("/actuator")
public interface ActuatorService {

    @POST
    @Path("/shutdown")
    Map<String, String> shutdown();

}
```

测试 demo: com.salesmanager.rest.service.ActuatorServiceTest

```java
public class ActuatorServiceTest {

    /**
     * Test {@link ActuatorService#shutdown()}}
     */
    @Test
    public void testPostShutdown() throws URISyntaxException {
        URI uri = new URI("http://127.0.0.1:8080");
        ActuatorService actuatorService = RestClientBuilder.newBuilder()
                .baseUri(uri)
                .build(ActuatorService.class);

        Map<String, String> result = actuatorService.shutdown();

        System.out.println(result); // {message=Shutting down, bye...}
    }

}
```



#### 问题2

实现中...



## Homework 02

### 问题

在 my-configuration 基础上，实现 ServletRequest 请求参数的 ConfigSource（MicroProfile Config），提供参考：Apache Commons Configuration 中的 org.apache.commons.configuration.web.ServletRequestConfiguration。

### 实现

见：org.geektimes.configuration.microprofile.config.source.servlet.ServletRequestConfigSource.java

```java
public class ServletRequestConfigSource extends MapBasedConfigSource {

    private final ServletRequest servletRequest;

    public ServletRequestConfigSource(ServletRequest servletRequest) {
        super("ServletRequest Init Parameters", 650);
        this.servletRequest = servletRequest;
    }

    @Override
    protected void prepareConfigData(Map configData) throws Throwable {
        Enumeration<String> parameterNames = servletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            configData.put(parameterName, servletRequest.getParameterValues(parameterName));
        }
    }
}
```



## Homework 01

### 问题

参考 com.salesmanager.shop.tags.CommonResponseHeadersTag 实现一个自定义的 Tag，将 Hard Code 的 Header 名值对，变为属性配置的方式，类似于:

```xml
    <tag>
        <name>common-response-headers</name>
        <tag-class>com.salesmanager.shop.tags.CommonResponseHeadersTag</tag-class>
        <body-content>empty</body-content>

        <attribute>
            <name>cacheControl</name>
            <required>false</required>
            <rtexprvalue>no</rtexprvalue>
            <type>java.lang.String</type>
        </attribute>
        <attribute>
            <name>pragma</name>
            <required>false</required>
            <rtexprvalue>no</rtexprvalue>
            <type>java.lang.String</type>
        </attribute>
        <attribute>
            <name>expires</name>
            <required>false</required>
            <rtexprvalue>no</rtexprvalue>
            <type>java.lang.Long</type>
        </attribute>
    </tag>
```

1. 实现⾃定义标签

2. 编写⾃定义标签 tld ⽂件

3. 部署到 Servlet 应⽤

### 实现

#### 自定义标签实现

shopizer/sm-shop/src/main/java/com/salesmanager/shop/tags/CommonResponseHeadersTag.java

```java
public class CommonResponseHeadersTag extends SimpleTagSupport {

    private String cacheControl;
    private String pragma;
    private Long expires;

    @Override
    public void doTag() throws JspException, IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.setHeader("Cache-Control", cacheControl);
        response.setHeader("Pragma", pragma);
        response.setDateHeader("Expires", expires);
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public void setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
    }

    public String getPragma() {
        return pragma;
    }

    public void setPragma(String pragma) {
        this.pragma = pragma;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }
}
```

#### 自定义标签 tld 文件
shopizer/sm-shop/src/main/webapp/WEB-INF/shopizer-tags.tld

```xml
    <tag>
        <name>common-response-headers</name>
        <tag-class>com.salesmanager.shop.tags.CommonResponseHeadersTag</tag-class>
        <body-content>empty</body-content>

        <attribute>
            <name>cacheControl</name>
            <required>false</required>
            <rtexprvalue>no</rtexprvalue>
            <type>java.lang.String</type>
        </attribute>
        <attribute>
            <name>pragma</name>
            <required>false</required>
            <rtexprvalue>no</rtexprvalue>
            <type>java.lang.String</type>
        </attribute>
        <attribute>
            <name>expires</name>
            <required>false</required>
            <rtexprvalue>no</rtexprvalue>
            <type>java.lang.Long</type>
        </attribute>
    </tag>
```

#### Template
shopizer/sm-shop/src/main/webapp/pages/shop/templates/december/catalogLayout.jsp

```jsp
<sm:common-response-headers cacheControl="no-cache" pragma="no-cache" expires="-1"/>
```





