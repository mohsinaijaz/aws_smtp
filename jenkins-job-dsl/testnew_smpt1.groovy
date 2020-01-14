pipelineJob('stages/testnew_smtp1') {
    parameters {
        string {
            name 'New_SMPT_1'
            defaultValue null
            description 'New SMTP 1 ID'
            trim true
        }
        definition {
            cpsScm {
                lightweight(false)
                scm {
                    git {
                        branch '*/${SOURCE_BRANCH}'
                        remote {
                            name('origin')
                            credentials 'ssp-github'
                            url 'https://github.cms.gov/CCSVDC-Infrastructure/image-services.git'
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
}
