pipelineJob('stages/termin_old_smtp') {
    parameters {
        string {
          name 'OLD_SMTP'
          defaultValue ""
          description 'First SMPT ID to terminate'
          trim true
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
                scriptPath "jenkinsfiles/termin_old_smtp"
            }
        }
    }
}
