FROM bellsoft/liberica-openjdk-alpine:21
#FROM openjdk:21-slim
#FROM eclipse-temurin:21-jre-noble
LABEL authors="Adonis.liu"

WORKDIR /app
# 将本地的 JAR 文件复制到容器中
COPY target/tidb-sync-0.0.1-SNAPSHOT.jar tidb-sync.jar
# 将本地的 DataX 目录复制到容器中
COPY datax datax

# 定义启动命令
ENTRYPOINT ["java", "-jar", "tidb-sync.jar"]