pipelineJob('stages/buildnew_smtp1') {
        definition {
            cpsScm {
                lightweight(false)
                scm {
                    git {
                        branch '*/${SOURCE_BRANCH}'
                        remote {
                            name('origin')
                            credentials 'ssp-github'
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
