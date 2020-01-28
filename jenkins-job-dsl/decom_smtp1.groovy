pipelineJob('stages/decom_old_smtp1') {
    parameters {
        string {
            name 'OLD_SMTP_1'
            defaultValue null
            description 'First SMPT ID to terminate'
            trim true
        }
        string {
            name 'OLD_SMTP_ENI_1'
            defaultValue null
            description 'ENI of FIRST SMPT to terminate'
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
