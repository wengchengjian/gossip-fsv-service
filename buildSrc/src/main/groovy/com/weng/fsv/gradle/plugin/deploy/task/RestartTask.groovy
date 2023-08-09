package com.weng.fsv.gradle.plugin.deploy.task


import com.weng.fsv.gradle.plugin.BaseSshTask
import org.gradle.api.Action
import org.gradle.api.Task

class RestartTask extends BaseSshTask {


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
