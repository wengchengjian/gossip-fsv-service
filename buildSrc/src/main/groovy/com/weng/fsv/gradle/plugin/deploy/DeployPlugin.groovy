package com.weng.fsv.gradle.plugin.deploy


import com.weng.fsv.gradle.plugin.deploy.task.RestartTask
import com.weng.fsv.gradle.plugin.deploy.task.DeployTask
import com.weng.fsv.gradle.plugin.deploy.task.StartTask
import com.weng.fsv.gradle.plugin.deploy.task.StopTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.hidetake.gradle.ssh.plugin.SshPlugin

class DeployPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {

        def extensions = project.extensions
        extensions.create("DeployArgs", DeployPluginExtension)
        def tasks = project.tasks
        def plugins = project.plugins

        plugins.apply(SshPlugin)

        tasks.create("start", StartTask) {
            description = 'Starting Definition Service'
            group = 'deploy'
        }

        tasks.create("stop", StopTask) {
            description = 'Stopping Definition Service'
            group = 'deploy'
        }

        tasks.create("restart", RestartTask) {
            description = 'Restarting Definition Service'
            group = 'deploy'
            dependsOn('deploy')

        }

        tasks.create("deploy", DeployTask) {
            description = 'Deploy Definition Service'
            group = 'deploy'
        }
    }
}
