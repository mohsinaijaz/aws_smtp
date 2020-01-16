    pipelineJob("smtp-pipeline") {
        parameters {
          credentials credentialType: 'com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey', defaultValue: 'goldkey', description: '', name: 'superuser', required: true
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
