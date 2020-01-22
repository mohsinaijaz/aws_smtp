pipelineJob('stages/testnew_smtp1') {
    parameters {
        string {
            name 'New_SMPT_1'
            defaultValue null
            description 'New SMTP 1 ID'
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
                scriptPath "jenkinsfiles/testnew_smtp1"
            }
        }
    }
