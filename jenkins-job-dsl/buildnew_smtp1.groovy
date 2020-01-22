pipelineJob('stages/buildnew_smtp1') {
        definition {
            cpsScm {
                lightweight(false)
                scm {
                    git {
                        branch '*/master'
                        remote {
                            name('origin')
                            url 'https://github.com/mohsinaijaz/aws_smtp.git'
                        }
                        extensions {
                           wipeWorkspace()
                        }
                    }
                }
                scriptPath "jenkinsfiles/buildnew_smtp1"
        }
    }
}
