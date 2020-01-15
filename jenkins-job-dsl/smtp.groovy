def amiTypes = [
    [name: 'rhel7', ssm_param: '/ccs/ami/rhel7/hvm/latest'],
    [name: 'rhel6hvm', ssm_param: '/ccs/ami/rhel6/hvm/latest'],
    [name: 'rhel6pv', ssm_param: '/ccs/ami/rhel6/pv/latest']
]
for (amiType in amiTypes) {
    pipelineJob("gold-image-${amiType['name']}-pipeline") {
        triggers {
              cron '0 20 * * *'
        }
        parameters {
            string {
                name 'SSM_PARAM'
                defaultValue amiType['ssm_param']
                description 'AWS Systems Manager Parameter name used to store the AMI id.  NOTE: This parameter must already exist in us-east-1.'
                trim true
            }
            string {
                name 'AMI_NAME_SUFFIX'
                defaultValue ""
                description 'Optional suffix for AMI name'
                trim true
            }
            string {
                name 'SHARE_AMI_EXTRA_ARGS'
                defaultValue ""
                description 'Optional extra arguments for share_ami.py, e.g., --account 123456789012'
                trim true
            }
            string {
                name 'GOLD_IMAGE_TYPE'
                defaultValue amiType['name'].toUpperCase()
                description 'Gold Image Type'
                trim true
            }
            string {
                name 'SNS_ARN'
                defaultValue 'arn:aws:sns:us-east-1:842420567215:gold-image'
                description 'SNS ARN to publish message to'
                trim true
            }
            string {
                name 'SNS_PARAM_PREFIX'
                defaultValue '/sns/ami/semantic_version'
                description 'SNS SSM Parameter prefix'
                trim true
            }
            string {
                name 'SOURCE_BRANCH'
                defaultValue 'master'
                description 'Git branch of image-services to use'
                trim true
            }
            string {
                name 'SHARED_LIB_BRANCH'
                defaultValue 'master'
                description 'Git branch of gold-image-jenkins-shared-lib'
                trim true
            }
            booleanParam {
                name 'Nightly'
                defaultValue true
                description 'This is a nightly build parameter, uncheck to publish.'
            }
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
                scriptPath "gold/jenkinsfiles/gold-image-${amiType['name']}-pipeline"
            }
        }
    }
}
