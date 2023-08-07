package com.wcj.blog.gradle.plugin.docker

class DockerPluginExtension {
    String jar = "build/libs"

    String maintainer = "chengjian.weng"

    Integer port = 8080

    String properties = ""

    String jvmOpts = """
                -Xms256m
              -Xmx512m 
              -XX:MetaspaceSize=128m 
              -XX:MaxMetaspaceSize=256m 
              -XX:+UseG1GC 
              -XX:ParallelGCThreads=4 
              -XX:ConcGCThreads=2 
              -XX:+UnlockExperimentalVMOptions 
              -XX:+UseCGroupMemoryLimitForHeap 
              -XX:+ExitOnOutOfMemoryError --add-opens java.base/java.lang=ALL-UNNAMED
             --add-opens java.base/java.math=ALL-UNNAMED
             --add-opens java.base/java.util=ALL-UNNAMED 
             --add-opens java.base/java.util.concurrent=ALL-UNNAMED 
             --add-opens java.base/java.lang.reflect=ALL-UNNAMED 
             --add-opens java.base/java.net=ALL-UNNAMED 
             --add-opens java.base/java.text=ALL-UNNAMED
             --add-opens java.base/java.lang.invoke=ALL-UNNAMED
"""

    String imageName

    String version = "1.0.0-SNAPSHOT"

    String description = "通用docker镜像打包脚本"

    String dockerfile = ""
}