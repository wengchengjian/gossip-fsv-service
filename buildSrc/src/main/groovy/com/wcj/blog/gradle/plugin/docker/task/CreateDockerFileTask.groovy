package com.wcj.blog.gradle.plugin.docker.task

import com.wcj.blog.gradle.plugin.docker.DockerPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.nio.charset.StandardCharsets

class CreateDockerFileTask extends DefaultTask {


    @TaskAction
    def run() {
        def dockerFileTemplate = """
# 指定基础镜像
FROM openjdk:20-ea-17-jdk
            
LABEL maintainer = "#maintainer"
            
LABEL description = "${project.description}"
            
LABEL version = "${project.version}"
            
# 设置容器中的工作目录
WORKDIR /app
            
# 复制构建好的JAR文件到容器中
COPY #jar app.jar
            
# 设置容器中的环境变量
ENV PROPERTIES = "#properties"
            
# 设置容器的暴露端口
EXPOSE #port
            
# 设置JVM参数
ENV JVM_OPTS="#jvmOpts"

# 定义容器启动命令
CMD ["sh", "-c", "java \$JVM_OPTS -jar app.jar  \$PROPERTIES"]
"""

        def dockerExtension = project.extensions.getByType(DockerPluginExtension)

        String imageName = dockerExtension.imageName
        assert imageName != null && imageName.length() > 0, 'ImageName must not be null or empty'

        String filePath = project.projectDir.getAbsolutePath() + File.separator + "Dockerfile"

        if (dockerExtension.dockerfile != null && dockerExtension.dockerfile.length() > 0) {
            filePath = dockerExtension.dockerfile
        }
        def file = new File(filePath)
        def out = new FileOutputStream(file)
        try {
            dockerFileTemplate = dockerFileTemplate.replace("#maintainer", dockerExtension.maintainer)
            dockerFileTemplate = dockerFileTemplate.replace("#jar", dockerExtension.jar)
            dockerFileTemplate = dockerFileTemplate.replace('#properties', dockerExtension.properties)
            dockerFileTemplate = dockerFileTemplate.replace("#port", String.valueOf(dockerExtension.port))
            dockerFileTemplate = dockerFileTemplate.replace("#jvmOpts", dockerExtension.jvmOpts)
            if (dockerFileTemplate != null && dockerFileTemplate.length() > 0) {
                out.write(dockerFileTemplate.getBytes(StandardCharsets.UTF_8))
            }
            if (dockerExtension.dockerfile != file.getAbsolutePath()) {
                dockerExtension.dockerfile = file.getAbsolutePath()
            }
        } finally {
            out.close()
        }

    }
}
