pipelineJob('stages/termin_old_smtp1') {
    parameters {
        string {
          name 'OLD_SMPT_1'
          defaultValue null
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
                scriptPath "jenkinsfiles/termin_old_smtp1"
            }
        }
    }
}
