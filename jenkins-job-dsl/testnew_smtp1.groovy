pipelineJob('stages/testnew_smtp1') {
    parameters {
        string {
            name 'New_SMPT'
            defaultValue ''
            description 'New SMTP 1 ID'
            trim true
        }
        string {
            name 'SMTP_ELB'
            defaultValue 'SMTP-PROD-Enterprise-V3-ELB'
            description 'SMTP ELB'
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
                scriptPath "jenkinsfiles/testnew_smtp1"
            }
        }
    }
