#!/usr/bin/env groovy
// Name of the Jenkins Node
node("master") {
    try {
        // def AWS_ACCESS_KEY = credentials("JenkinsUser")
        def GOLD_AWS = "goldkey"
        def AWS_DEFAULT_REGION = "us-east-1"
        // def AWS_ACCESS_KEY_ID = "${AWS_ACCESS_KEY_USR}"
        // def AWS_SECRET_ACCESS_KEY = "${AWS_ACCESS_KEY_PSW}"
        def SMTP_ENI_1 = "eni-0331c5e11c70144db"
        // parameters
        properties([
            parameters([
                string(
                    name: "OLD_SMTP_1",
                    defaultValue: "",
                    description: "First SMTP ID to terminate"),
                string(
                    name: "OLD_SMTP_2",
                    defaultValue: "",
                    description: "Second SMTP ID to terminate"),
            ])
        ])
        ansiColor("xterm") {
            //First Stage
            def SMTP_IP
            stage ("Retrieve SMTP's IP Address") {
                dir("test-instance") {
                    script_string = """
                    sh ./decom_smtp1.sh '${OLD_SMTP_1}' '${OLD_SMTP_2}'
                    """
                    SMTP_IP = sh script:script_string, returnStdout: true
                    println SMTP_IP
                } // dir
            } // stage
            boolean checkMail = true
            stage ("Decommission Old SMTP Server") {
                try {
                    dir("test-instance") {
                        sshagent(credentials: ["goldkey"]) {
                            sh "ssh -o StrictHostKeyChecking=no ec2-user@${SMTP_IP} sh ./checkmailq.sh"
                        }
                    } // dir
                } catch (Exception e) {
                    checkMail = false
                }
            } // stage
            stage ("Detaching SMTP from ENI") {
                if (checkMail) {
                    dir("test-instance") {
                        println("\u001b[33mDetaching ${SMTP_IP} from ENI...\u001b[0m")
                        sh """
                        /usr/local/bin/aws ec2 detach-network-interface --attachment-id ${SMTP_ENI_1}
                        """
                    }
                }
            } // stage
        } // ansicolor
    } catch (Exception e) {
        current.Build.result = "Failed"
        throw e
    }
} // node
