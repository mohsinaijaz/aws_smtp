pipelineJob('stages/buildnew_smtp') {
    parameters {
          string {
            name 'SMTP_ENI_NET_ID'
            defaultValue ""
            description 'SMPT ENI Network interface ID'
            trim true
          }
        }
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
                scriptPath "jenkinsfiles/buildnew_smtp"
        }
    }
}
