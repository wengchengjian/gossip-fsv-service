package com.weng.fsv.gradle.plugin.docker.task


import com.weng.fsv.gradle.plugin.docker.DockerPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


abstract class BaseDockerTask extends DefaultTask {
    def dockerExtension = project.extensions.getByType(DockerPluginExtension)

    @TaskAction
    def run() {

    }
}
