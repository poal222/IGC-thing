1、maven 仓库推送 命令如下
推送命令（建议针对不同的jar包，分别推送）
<code>
mvn clean install org.apache.maven.plugins:maven-deploy-plugin:2.8:deploy -DskipTests
</code>

当前版本(以推送至ali库中)

        <dependency>
            <groupId>org.isdp</groupId>
            <artifactId>isdp-boot</artifactId>
            <version>1.1-SNAPSHOT</version>
        </dependency>

2、工作原理

参照springboot及vertx-boot设计，实现通过注解启动vertx应用的方法

3、注解清单及解释

3.1 
@Application 应用启动总入口，样例如下：

<code>
@Application
public class TestBoot{
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        IsdpApplication isdpApplication =  new IsdpApplication();
        isdpApplication.Run(args,TestBoot.class, vertx);
    }
}
</code>

3.1
@DeployVerticle 部署verticle使用，样例如下

<code>
@DeployVerticle(name = "TestBoot1")
public class TestBoot1 extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);
        System.out.println("TestBoot1");
    }
}
</code>

其中 name请确保唯一，为了实现每一个verticle具备一套独立的部署参数，该name会在部署时作为配置参数的唯一寻址key存在


4、启动

正常达成jar包，执行 java -jar **.jar即可，默认为单机环境

样例配置文件请参考源码中 resources/application.json

可添加命令，命令清单如下
-cluster 集群模式
-conf= 外部配置文件
-env= 环境变量

例如：启动生产环境，集群模式，外部配置文件，可执行


java -jar **.jar -cluster -conf=bin/application.json -env=dev

5、下一步要实现内容

- [ ]  Configuration配置组件的动态加载机制
- [ ]  利用服务发现和注解，实现多数据源提供机制