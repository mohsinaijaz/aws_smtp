#!/usr/bin/env bash


#exit script if errors
set -e

smtp_delete_1=$1
smtp_delete_2=$2


echo "Now Terminating SMTP 1"
aws ec2 terminate-instances --instance-ids $smtp_delete_1
