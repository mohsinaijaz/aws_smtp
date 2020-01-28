    pipelineJob("smtp-pipeline") {
      parameters {
        string {
            name 'OLD_SMTP'
            defaultValue ""
            description 'SMPT ID to terminate'
            trim true
        }
          string {
              name 'SMTP_ENI'
              defaultValue ""
              description 'SMPT ENI Attachment ID'
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
                scriptPath "jenkinsfiles/smtp-pipeline"
        }
    }
}
