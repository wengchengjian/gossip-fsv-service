# 指定基础镜像
FROM rainbowzephyr/openjdk17

LABEL maintainer = ${maintainer}

LABEL description = ${description}

LABEL version = ${version}

# 设置容器中的工作目录
WORKDIR /app

# 复制构建好的JAR文件到容器中
COPY ${jar} app.jar

# 设置容器中的环境变量
ENV PROPERTIES = ${properties}

# 设置容器的暴露端口
EXPOSE ${port}

# 设置JVM参数
ENV JVM_OPTS=${jvmOpts}



# 定义容器启动命令
CMD ["sh", "-c", "java $JVM_OPTS -jar app.jar  $PROPERTIES"]


