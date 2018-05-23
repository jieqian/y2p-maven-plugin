# y2p-maven-plugin

##说明
该maven插件适用于spring boot微服务框架的目录结构，即
```
YourProject
    src
       main
           java
           resources
              config
                 application.yml
                 application-dev.yml
                 application-online.yml
                 bootstrap.yml
```

##maven依赖
```
<project>
    ...
    <profiles>
        <profile>
            <id>generate-properties-profile</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>ppc.cloud.plugin</groupId>
                        <artifactId>y2p-maven-plugin</artifactId>
                        <version>1.0-SNAPSHOT</version>
                        <executions>
                            <execution>
                                <goals>
                                    <goal>run</goal>
                                </goals>
                            </execution>
                        </executions>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>
    ...
</project>    
```

##如何使用
有时候你需要把application-${env}.yml的配置转换成properties中的格式，然后粘贴到配置中心的文本中去，那么只要在微服务的项目根目录中执行以下命令:
```
mvn y2p:run -Denv=${env} -P generate-properties-profile
```
* ${env}参数的选项: dev、test、stage、sandbox、online，如果不指定-Denv参数的话，则表示要转换application.yml;
* 执行完之后会在${project.basedir}/tmp目录下生成相应的.properties文件；


