# res
小萌神项目
1.功能:
    基本功能 : 登录/退出,浏览菜品，查看详情,加入购物车,修改(增加,减少,删除)下订数量,填写外卖送货信息等功能.
    后期功能 : 分页浏览, 用户历史访问记录, 点赞, 用户权限, 记录用户设备, 统计活跃程度.

2. 技术:
    github仓库地址: xxxx
    csdn:
    项目技术架构描述:
        系统整合:  springBoot+spring
        Web层框架:  springMVC
        Dao层: mybatisPlus
        数据源:  druid
        项目构建:  maven
        数据库: mysql8
        单元测试框架: spring boot test + junit,  采用 mock
        API发布管理: Swagger
        前后端联调工具:  postman
        项目运行容器:  docker   => K8S
        版本控制: git

        ＝＝＝＝＝＝发送注册邮件，发送日报表的功能
        定时任务:   quartz
        模板引擎: freemarker
        邮件:  javax.mail

        前端框架: vuejs
        缓存:   spring data +  redis   ->　　　查询缓存　　（查多于修改）　
        跨域问题:  浏览器的安全机制,  同源策略   -> 前后端项目分离，通过浏览器访问后端.

    ==============================================
        权限控制:  spring security
        报表:  水晶报表,....
        图表:  jfreechart,

        移动开发: uni-app.  基于vue

        no-sql:

===========================================================================================================================
技术小结：

================================token+jwt+spring security==============================================
token和session区别?
token的格式?
jwt的作用?
spring security?  =>


===============================================================================
1. mybatisplus分页: 　　　要告诉mp,数据库类型  =>　用拦截器来注册数据库类型　
 @Configuration
 public class MybatisPlugConfig {
     //分页拦截器:   MYSQL:    limit x,y      oracle: 子查询+rownum     sqlserver； top
     //             -> hibernate ORM框架  ->  指定方言  dialect=
     @Bean
     public MybatisPlusInterceptor mybatisPlusInterceptor() {
         MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
         //注册数据库类型
         interceptor.addInnerInterceptor(new PaginationInnerInterceptor(　　　DbType.MYSQL　　　)　　);
         return interceptor;
     }
 }

  @Override  // Wrapper接口 -> select的Wrapper接口     update的Wrapper接口    LambdaWrapper接口
     public Page<Resfood>  findByPage(int pageno, int pagesize, String sortby, String sort) {
         QueryWrapper<Resfood> wrapper = new QueryWrapper<>();
         if(   sort.equalsIgnoreCase("asc")){
             wrapper.orderByAsc(   sortby );
         }else{
             wrapper.orderByDesc(sortby );
         }
         // 设置分页信息,
         Page<Resfood> page = new Page<>(pageno, pagesize);
         // 执行分页查询
         Page<Resfood> userPage = resfoodDao.selectPage(page, wrapper);
         log.info("总记录数 = " + userPage.getTotal());
         log.info("总页数 = " + userPage.getPages());
         log.info("当前页码 = " + userPage.getCurrent());
         return page;
     }
==========================spring boot的端点监控================================
2. 服务注册中心监控微服务的运行状态. ->   且暴露运行状态信息   => Rest API   <= 整合其它的图表软件.
springboot四大组件:
  自动配置, starter, acuator监控器, cli.
  acuator监控器 -> endpoint   可访问的http接口

    步骤一:
       <!-- acuator端点监控启动器:  -->
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-actuator</artifactId>
          </dependency>
   步骤二:
management:
  endpoints:
    web:
      exposure:
        include: "*"          # info, health默认暴露
        exclude: ""

访问: http://ip:port/actuator/端点名

===========================服务注册发现==============================================
3. 用 alibaba的 服务注册的功能将服务自身注册到nacos服务器上.
       步骤一：  <dependency>
              <groupId>com.alibaba.cloud</groupId>
              <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
          </dependency>
        步骤二：
         spring:
            cloud:
              nacos:
                discovery:
                  server-addr: localhost:8848
                  username: nacos
                  password: nacos
        步骤三：
         @EnableDiscoveryClient

         //启用服务注册发现的客户端:  httpclient/postman

============================================================================



spring提供了很多模板操作:  JDBCTemplate   redisTemplate    restTemplate  =>　 模板模式(　　设计模式      ).


 1) restTemplate用法:  http客户端
       @Bean
      public RestTemplate restTemplate(    ){
          return new RestTemplate();
      }
      RestTemplate的方法:
                       postXXX, getXXX, putXXX....
                       exchange()
    简单的get请求:
    Map map= this.restTemplate.getForObject(  url   , Map.class );
    复杂的http请求:
    HttpHeaders header=new HttpHeaders() {{
                set( "Authorization", bearerToken );
                set("User-Agent","yc IE");
            }};
    ResponseEntity<Map> re=restTemplate.exchange
                    (url, HttpMethod.GET,    new HttpEntity<Map>( header),     Map.class);
     Map m=re.getBody();  // m就是那个  claims

2)redisTemplate用法: 重点是解决对象序列化的问题 ( no-sql库[redis, mongodb, es...等] )
      要求1： 存到redis中的对象必须实现java.io.Serializable接口
      要求2:  默认情况下， RedisTemplate<Object,Object> 对象被创建,
             如果要创建  <String,Object>，则需手动指定 RedisTemplate的序列化器.
             java程序: java对象包装数据　－> 　　　　转化(序列化)　　　　　　　　　　-> no-sql库
          @Bean
          public RedisTemplate redisTemplate(RedisConnectionFactory factory){
              RedisTemplate<String,Object> redisTemplate=new RedisTemplate();

              //设置  键 序列化器
              redisTemplate.setKeySerializer(   new StringRedisSerializer());
              //设置 hash值对部分的  键为  StringRedisSerializer
              redisTemplate.setHashKeySerializer(  new StringRedisSerializer() );
              //将redis中保存的hash的value ，因为它是一个对象，
              //这里调用  GenericJackson2JsonRedisSerializer序列化器将对象转为了json字符串
              redisTemplate.setValueSerializer(    new GenericJackson2JsonRedisSerializer());
              redisTemplate.setHashValueSerializer(   new GenericJackson2JsonRedisSerializer()  );

              redisTemplate.setConnectionFactory(factory);

              return redisTemplate;
          }

    redisTemplate.opsForHash()   操作hash值的方法

    redis内存数据库: 键-值
         值: String ,list, set, sorted set, hash,  geo

=================================================================================
引入LoadBalancer,通过服务名以负载均衡的方式查找服务节点
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-loadbalancer</artifactId>
        </dependency>

     方案一: 配置  RestTemplate客户端为  RoundRobin负载均衡策略.
        @Configuration
        @EnableDiscoveryClient    //启用服务发现的客户端
        public class ApplicationConfig {
            @Bean
            @LoadBalanced     //负载平衡器: 一个服务名下有多个服务节点
            public RestTemplate restTemplate(    ){   //如此RestTemplate对象就有这个功能了..
                return new RestTemplate();
            }
        }
      方案二:对每个服务进行单独的配置
        //对每个服务分别指定负载均衡策略
        @LoadBalancerClients(
                value = {
                        @LoadBalancerClient(value = "res-food", configuration = MyOnlyOnceConfig.class),
                        @LoadBalancerClient(value = "res-security", configuration = RoundRobinConfig.class),
                }, defaultConfiguration = LoadBalancerClientConfiguration.class
        )
        public class LoadBalancerConfig {
            @LoadBalanced   //开启RestTemplate对象的负载均衡功能 .
            @Bean
            public RestTemplate restTemplate() {
                return new RestTemplate();
            }
        }

     自定义实现负载均衡策略: class MyOnlyOnceLoadBalancer implements ReactorServiceInstanceLoadBalancer {
                               实现choose()
           }

================================================================================
openFeign  api客户端接口
1. 基础服务的创建
2. 创建api项目
    <!--导入openfeign-->
          <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-openfeign</artifactId>
          </dependency>
          <!--实体类 -->
          <dependency>
              <groupId>org.example</groupId>
              <artifactId>res-entity</artifactId>
              <version>1.0-SNAPSHOT</version>
          </dependency>

    支持springMVC注解:
       @FeignClient(value="res-foods",path="resfood")
       public interface ResfoodApi {
           @RequestMapping("findByPage")
           public Map<String,Object> findByPage(@RequestParam int pageno, @RequestParam int pagesize, @RequestParam String sortby, @RequestParam String sort   );
       }
3. 调用端的使用:
     <!--导入openfeign-->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-openfeign</artifactId>
           </dependency>
           <dependency>
               <groupId>org.example</groupId>
               <artifactId>res-api</artifactId>
               <version>1.0-SNAPSHOT</version>
           </dependency>

     @EnableFeignClients(basePackages ={"com.yc.api"} )

     注入api接口对象,调用
         @Autowired
            private ResfoodApi resfoodApi;

          //方案三: 利用 openFeign来发出请求.
                 Map<String,Object> resultMap=this.resfoodApi.findById(   fid );


======================================
feign日志配置:
   服务日志的级别
1. logging:
     level:
       com.yc.api.ResfoodApi: DEBUG
2. 每个服务要加入的网络日志级别
    @Configuration
    public class FeignLogConfig {
        @Bean        //NONE,BASIC HEADERS,FULL,
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }

    }
3. 对api接口对应的服务配置日志
   @FeignClient(value="res-foods",path="resfood",configuration= FeignLogConfig.class)
=========================================
feign数据压缩:
  feign:
   compression:
    request:
      enabled: true
      mime-types: # 可以被压缩的类型
       - text/xml
       - application/xml
       - application/json
      min-request-size: 2048 # 超过2048的字节进行压缩
==========================================
feign访问超时时间:
  feign:
   client:
      config:
        default:   #所有api服务
          connectTimeout: 10000
          readTimeout: 10000
==============================================
feign框架的原理:  几大组件:
  contractor,  encoder/decoder,  client类型,   retryer, .....

====================================================
流量控制:    sentinel  采集服务器运行指标      dashboard 可视化展现+console
 分布式链路追踪:  sleuth 采集资源调用信息     zipkin: 可视化呈现
 资源调用信息 :  数据结构=>
     trace: traceid在一个调用链条中不变, 每个 资源的调用( controller中的方法，redis操作,resttemplate... )都创建一个span
      span:   一个span要记录四个点:
                  CS:  client sent
                  sr: server received
                  ss: server sent
                  cr:   client received
 记录信息的代码 <= sleuth完成.   => AOP完成.
 1) Servlet  -> servlet  =>
 　　　　filter过滤器
 2) springMVC  中的controller
     Interceptor
 3) redisTemplate
      opsForString() 方法   =>   AOP
 4) jdbc:
        pstmt.executeUpdate()
                  .executeQuery()     ->  AOP
 ....
