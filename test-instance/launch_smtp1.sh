#!/usr/bin/env bash

#exit script if errors
set -e

ec2_size='t2.micro'
cidr_ip='172.31.0.0/16'
subnet_id='subnet-4802dd03'
region='us-east-1'
source_ami='ami-000db10762d0c4c05'
#source_ami=`aws ssm get-parameter --name "/ccs/ami/rhel7/lvm/latest"`
smtp_loadbalancer=''
securitygroup_id='sg-c832efba'
smtp_eni_1='eni-0331c5e11c70144db'
smtp_eni_2='eni-096a0c47835b10ece'
#smtp_delete_1=$1
#smtp_delete_2=$2


#create SMTP 1 instance
echo "Creating New SMTP1 instance"
instance_id_1=$(/usr/local/bin/aws ec2 run-instances --image-id $source_ami \
--count 1 --instance-type $ec2_size --key K8testkey \
--security-group-ids $securitygroup_id --subnet-id $subnet_id \
--tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value=SMTP_1}]' \
--user-data 'https://my-postfix.s3.amazonaws.com/configpfix.sh' \
--region $region --query 'Instances[0].InstanceId' --output text)


if [[ -z $instance_id_1 ]]
then
    echo "ERROR. Unable to create SMTP 1"
    exit 1
fi

echo "Instance ID is $instance_id_1"


/usr/local/bin/aws ec2 wait instance-status-ok --instance-ids $instance_id_1

/usr/local/bin/aws ec2 attach-network-interface --network-interface-id $smtp_eni_1 --instance-id $instance_id_1 --device-index 1

instance_ip_1=$(/usr/local/bin/aws ec2 describe-instances --instance-ids $instance_id_1 \
--query 'Reservations[0].Instances[0].NetworkInterfaces[0].PrivateIpAddresses[0].PrivateIpAddress' --output text)
echo "SMTP 1 IP is $instance_ip_1"
