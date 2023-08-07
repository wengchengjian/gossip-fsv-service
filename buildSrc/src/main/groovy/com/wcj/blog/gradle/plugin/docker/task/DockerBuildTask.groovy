package com.wcj.blog.gradle.plugin.docker.task

import com.wcj.blog.gradle.plugin.docker.DockerPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

class DockerBuildTask extends DefaultTask {


    @TaskAction
    def run() {
        def dockerExtension = project.extensions.getByType(DockerPluginExtension)
        def file = new File(dockerExtension.dockerfile)

        def command = ["docker", "build", "-t", "${dockerExtension.imageName}:${project.version}", "."].collect { it.toString() }
        def processBuilder = new ProcessBuilder(command)
        processBuilder.directory(file.getParentFile())
        processBuilder.redirectErrorStream(false)

        def process = processBuilder.start()
        def output = new StringBuilder()
        def reader = new BufferedReader(new InputStreamReader(process.getInputStream()))

        String line = null
        while ((line = reader.readLine()) != null) {
            output.append(line).append(System.getProperty('line.separator'))
            println line
        }
        reader.close()

        def errorStream = process.getErrorStream()
        reader = new BufferedReader(new InputStreamReader(errorStream))

        while ((line = reader.readLine()) != null) {
            output.append(line).append(System.getProperty('line.separator'))
            println line
        }
        reader.close()

        process.waitFor()
        def exitCode = process.exitValue()
        if (exitCode != 0) {
            throw new GradleException("Docker build failed with exit code ${exitCode}.")
        }

        println "Command output: ${output.toString()}"
    }

}
