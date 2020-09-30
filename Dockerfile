#FROM openjdk:8-jdk-alpine
#VOLUME /测试 eureka
#ADD cloud-demo-eureka-user.jar eureka-user.jar
#EXPOSE 8002
#ENTRYPOINT ["java","-jar","/eureka-user.jar"]
FROM image-registry.openshift-image-registry.svc:5000/openshift/maven:3.6.1-jdk-8-alpine as BUILD
COPY src /usr/app/src
COPY pom.xml /usr/app

RUN mvn -f /usr/app/pom.xml clean package -Dmaven.test.skip=true

FROM image-registry.openshift-image-registry.svc:5000/openshift/openjdk:8-jdk-alpine
#FROM java:8
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#这里的 /tmp 目录就会在运行时自动挂载为匿名卷，任何向 /tmp 中写入的信息都不会记录进容器存储层
VOLUME /tmp
#ARG JAR_FILE
#复制上下文目录下的target/demo-1.0.0.jar 到容器里
COPY --from=BUILD /usr/app/target/cloud-demo-eureka-user-0.0.1-SNAPSHOT.jar eureka-user.jar

#指定容器启动程序及参数   <ENTRYPOINT> "<CMD>"
ENTRYPOINT ["java","-jar","-Xms512m", "-Xmx1024m","/eureka-user.jar"]

#声明运行时容器提供服务端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务
EXPOSE 8802