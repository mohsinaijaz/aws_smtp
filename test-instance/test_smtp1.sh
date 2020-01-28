#!/usr/bin/env bash


#exit script if errors
set -e

smtp_loadbalancer='SMTP-PROD-Enterprise-V3-ELB'
instance_id_1=''


#ssh to new instance and send test email
ssh  -oStrictHostKeyChecking=no ec2-user@$instance_id_1 "./testmailq.sh"

./checkmailq.sh


#attach to smtp LB
#/usr/local/bin/aws elb register-instances-with-load-balancer --load-balancer-name $smtp_loadbalancer --instances $instance_id_1
