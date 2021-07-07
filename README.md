# ProjectPractice

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

#### 自定义标签实现: shopizer/sm-shop/src/main/java/com/salesmanager/shop/tags/CommonResponseHeadersTag.java

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

#### 自定义标签 tld 文件: shopizer/sm-shop/src/main/webapp/WEB-INF/shopizer-tags.tld

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

#### template: shopizer/sm-shop/src/main/webapp/pages/shop/templates/december/catalogLayout.jsp

```jsp
<sm:common-response-headers cacheControl="no-cache" pragma="no-cache" expires="-1"/>
```





