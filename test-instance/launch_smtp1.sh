#!/usr/bin/env bash

#exit script if errors
set -e

ec2_size='t2.micro'
cidr_ip='172.31.0.0/16'
subnet_id='subnet-4802dd03'
region='us-east-1'
source_ami='ami-000db10762d0c4c05'
#source_ami=`aws ssm get-parameter --name "/ccs/ami/rhel7/lvm/latest"`
smtp_loadbalancer='SMTP-PROD-Enterprise-V3-ELB'
securitygroup_id='sg-c832efba'
SMTP_ENI_NET_ID=$1



#create SMTP 1 instance
echo "Creating New SMTP instance"
instance_id=$(/usr/local/bin/aws ec2 run-instances --image-id $source_ami \
--count 1 --instance-type $ec2_size --key K8testkey \
--security-group-ids $securitygroup_id --subnet-id $subnet_id \
--tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value=SMTP_v3_'$RANDOM'}]' \
--user-data 'https://my-postfix.s3.amazonaws.com/configpfix.sh' \
--region $region --query 'Instances[0].InstanceId' --output text)


if [[ -z $instance_id ]]
then
    echo "ERROR. Unable to create SMTP"
    exit 1
fi

echo "Instance ID is $instance_id"


/usr/local/bin/aws ec2 wait instance-status-ok --instance-ids $instance_id

/usr/local/bin/aws ec2 attach-network-interface --network-interface-id $SMTP_ENI_NET_ID --instance-id $instance_id --device-index 1

instance_ip=$(/usr/local/bin/aws ec2 describe-instances --instance-ids $instance_id \
--query 'Reservations[0].Instances[0].NetworkInterfaces[0].PrivateIpAddresses[0].PrivateIpAddress' --output text)
echo "SMTP IP is $instance_ip"
