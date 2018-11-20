# XXSCloud: 适用于Java 的核心库
XXSCloud是一组核心库，基元的API /实用程序，反射，字符串处理等等！


## 将XXSCloud 添加到构建中

```
<dependency>
  <groupId>com.google.guava</groupId>
  <artifactId>guava</artifactId>
  <version>27.0-jre</version>
  <!-- or, for Android: -->
  <version>27.0-android</version>
</dependency>
```

## 开始使用使用
> `PS 后期会迁移到GitBook 这里只做简略概述`

##### 使用ApiCoreController

```
// 继承类 ApiCoreController 并调用 apiHandle 方法, 传入请求参数,响应参数, 版本号, 以及渠道号
@CrossOrigin(origins = ["*"]) 
@RestController
class ApiController : ApiCoreController() {
    @RequestMapping(value = ["/api/{version}/{channel}/*"])
    fun api(request: HttpServletRequest, response: HttpServletResponse,
            @PathVariable("version") version: String,
            @PathVariable("channel") channel: String): String {
        return apiHandle(request, response, version, channel)
    }
}
```

> 反射到Service 默认做非Null 校验, 如果需要参数为NULL 则在头部加入
`@APINullable`


##### 使用RedisUtils
> RedisUtils 使用Sping-Redis作为底层进行的通讯
> 所有API和官方命令一样

```
//Spring Boot 使用前必须添加到扫描
@ComponentScan(basePackages = {"com.xxscloud.core.RedisUtils"})

```

##### 超级强力的Http 控件
> Http 底层使用okHttp 进行管理请求

> 提供了 HttpUtils 处理多次请求, 本工具类会记录请求的Cookie  
> 提供了 HttpApiUtils, 处理API请求, 工具类不会记录任何会话  
> 工具类默认都会失败重试三次

##### 超级强力的StringUtils 文件工具
> 工具类对常见的字符串处理都进行了收录

```
   /**
     * 格式化日期.
     * 默认 yyyy-MM-dd
     */
    fun formatDate(format: String): String {
         
    }

    /**
     * 格式化自定义日期.
     */
    fun formatDate(data: Date, format: String): String {
       
    }

    /**
     * 从右截取字符.
     */
    fun stringRight(data: String?, length: Int): String {
       
    }


    /**
     * 从左截取字符串.
     */
    fun stringLeft(data: String?, length: Int): String {
         
    }

    /**
     * 格式化银行卡号.
     */
    fun formatBankCard(no: String?): String {
        
    }

    /**
     * 格式化身份证.
     */
    fun formatIdCard(no: String?): String {
        
    }

    /**
     * 格式化手机号.
     *  输出 156 **** 2911
     */
    fun formatPhone(phone: String?): String {
         
    }

    /**
     * 验证邮箱.
     */
    fun emailVerification(email: String?): Boolean {
         
    }

    /**
     * 验证手机号.
     */
    fun phoneVerification(phone: String?): Boolean {
       
    }

    /**
     * 验证银行卡.
     */
    fun bankCardVerification(bankCard: String?): Boolean {
      
    }

    /**
     * 验证身份证.
     */
    fun idCardVerification(idCard: String?): Boolean {
        
    }

    /**
     * 判断是否是Long 类型
     */
    fun isLong(value: String?): Boolean {
       
    }

```


##### 超级强力的FileUtils 文件工具
> 文档编写中...

##### 超级强力的ImageUtils 文件工具
> 文档编写中...


## 问题反馈
- **邮箱** 15629116378@163.com
- **QQ** 735802488

## License
Apache Dubbo is under the Apache 2.0 license. See the LICENSE file for details.