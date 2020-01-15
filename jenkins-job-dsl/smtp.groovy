    pipelineJob("smtp-pipeline") {
        triggers {
              cron '0 20 * * *'
        }
        parameters {
            string {
                name 'SNS_PARAM_PREFIX'
                defaultValue null
                description 'SNS SSM Parameter prefix'
                trim true
            }
            string {
                name 'SOURCE_BRANCH'
                defaultValue 'master'
                description 'Git branch of image-services to use'
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
