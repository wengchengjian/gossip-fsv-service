package com.wcj.blog.gradle.plugin.deploy.task

import com.wcj.blog.gradle.plugin.BaseSshTask
import org.gradle.api.Action
import org.gradle.api.Task

class StopTask extends BaseSshTask {

    @Override
    Task doLast(Action<? super Task> action) {
        ssh.run {
            session(remotes) {
                execute """
                              #!/bin/sh
                              cd ${deployExtension.remoteDir}
                              ${script}
                        """
            }
        }
        return super.doLast(action)
    }
}
