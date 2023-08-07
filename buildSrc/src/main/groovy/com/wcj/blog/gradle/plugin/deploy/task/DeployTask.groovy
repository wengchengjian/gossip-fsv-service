package com.wcj.blog.gradle.plugin.deploy.task

import com.wcj.blog.gradle.plugin.BaseSshTask
import org.gradle.api.Action
import org.gradle.api.Task

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.util.regex.Pattern

class DeployTask extends BaseSshTask {


    @Override
    Task doLast(Action<? super Task> action) {
        ssh.run {
            session(remotes) {
                // 备份
                execute """
                                cd ${deployExtension.remoteDir}
                                [ -d "${deployExtension.remoteDir}/jar/bak" ] || mkdir -p ${deployExtension.remoteDir}/jar/bak
                                oldJar=`ls | grep *.jar`
                                [ -n "\${oldJar}" ] && mv *.jar ./bak
                            """
                put from: "${deployExtension.localDir}/${findLatestJar()}", to: "${deployExtension.remoteDir}"
            }
        }
        return super.doLast(action)
    }


    def findLatestJar() {
        def localDir = new File(deployExtension.localDir)

        if (localDir.isDirectory()) {
            def files = localDir.listFiles()
            Pattern p = Pattern.compile(deployExtension.nameRegex)
            def file = files.max {
                it ->
                    {
                        Files.readAttributes(Paths.get(it.getAbsolutePath()), BasicFileAttributes.class).creationTime()
                    }
            }
            return file.getName()
        }
    }
}


