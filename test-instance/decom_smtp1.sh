#!/usr/bin/env bash

#exit script if errors
set -e

smtp_loadbalancer='SMTP-PROD-Enterprise-V3-ELB'
smtp_eni_1='eni-0331c5e11c70144db'
smtp_eni_2='eni-096a0c47835b10ece'
OLD_SMPT_1=$1
OLD_SMPT_2=$2
goldkey=$4

old_smtp_ip_1=$(/usr/local/bin/aws ec2 describe-instances --instance-ids $OLD_SMPT_1 \
--query 'Reservations[0].Instances[0].NetworkInterfaces[0].PrivateIpAddresses[0].PrivateIpAddress' --output text)


echo "Removing $OLD_SMPT_1 from SMTP LB"
/usr/local/bin/aws elb deregister-instances-from-load-balancer --load-balancer-name $smtp_loadbalancer --instances $OLD_SMPT_1


#ssh to old smtp and check mail qu before removing the ENI

ssh -tt -o StrictHostKeyChecking=no -i $gold_key ec2-user@$old_smtp_ip_1 "./checkmailq.sh"

echo "Deatching SMTP from ENI 1"
/usr/local/bin/aws ec2 detach-network-interface --attachment-id $smtp_eni_1



#if [ "$(mailq | grep 'empty' &>/dev/null; echo $?)" == 0 ]; then
#  echo "mail queue empty"
#  exit 0
#else
#  echo "mail queue NOT empty"
#fi
