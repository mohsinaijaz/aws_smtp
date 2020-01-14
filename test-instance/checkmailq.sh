#!/usr/bin/env bash


for i in {1..100}; do
    mailq=`mailq | grep "MAILER"`
    if [[ $mailq ]]
    then
        echo "queue not empty trying again"
        echo "Retrying..."; sleep 15;
    else
        echo "queue empty"
        break;
    fi
done

exit
