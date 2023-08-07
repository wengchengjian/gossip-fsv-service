package com.wcj.blog.gradle.plugin.docker

import com.wcj.blog.gradle.plugin.docker.task.CreateDockerFileTask
import com.wcj.blog.gradle.plugin.docker.task.DockerBuildTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class DockerPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def extensions = project.extensions
        extensions.create("docker", DockerPluginExtension)
        def tasks = project.tasks

        tasks.create("dockerfile", CreateDockerFileTask) {
            group = "docker"
            description = "Create a Docker file"
        }
        tasks.create("dockerbuild", DockerBuildTask) {
            group = "docker"
            description = "docker build"
            dependsOn("dockerfile")
        }


    }
}
