pipelineJob('stages/decom_old_smtp1') {
    parameters {
        string {
            name 'OLD_SMTP'
            defaultValue null
            description 'SMPT ID to terminate'
            trim true
        }
        string {
            name 'SMTP_ENI'
            defaultValue null
            description 'SMPT ENI ID'
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
                scriptPath "jenkinsfiles/decom_old_smtp1"
            }
        }
    }
