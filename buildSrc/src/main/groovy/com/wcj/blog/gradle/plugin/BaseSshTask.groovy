package com.wcj.blog.gradle.plugin

import com.wcj.blog.gradle.plugin.deploy.DeployPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.hidetake.groovy.ssh.core.Remote
import org.hidetake.groovy.ssh.core.Service

abstract class BaseSshTask extends DefaultTask {
    def deployExtension = project.extensions.getByType(DeployPluginExtension)
    def ssh = project.extensions.getByType(Service)
    def remotes = project.container(Remote)

    def script = ''

    @TaskAction
    def exec() {
        assert script != null && script.length() > 0, 'Script must not be null or empty'
    }

}
