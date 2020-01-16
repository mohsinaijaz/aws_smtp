    pipelineJob("smtp-pipeline") {
      parameters {
        string {
            name 'OLD_SMPT_1'
            defaultValue null
            description 'First SMPT ID to terminate'
            trim true
        }
        string {
            name 'OLD_SMPT_2'
            defaultValue null
            description 'Second SMPT ID to terminate'
            trim true
          }
        credentials {
          credentialType 'com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey'
          name 'superuser'
          defaultValue 'goldkey'
          description 'creds to login to old smtp'
          required true
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
