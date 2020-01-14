#!/usr/bin/env bash



echo “Testing 123” | mailx -v -s “Test Email” -r “test@aws.cms.gov”  “aijaz_mohsin@bah.com”


if [ "$(mailq | grep 'empty' &>/dev/null; echo $?)" == 0 ]; then
  echo "mail queue empty"
  exit 0
else
  echo "mail queue NOT empty"
  exit 1
fi

exit

#for i in {1..100}; do
#    mailq=`mailq | grep "MAILER"`
#    if [[ $mailq ]]
#    then
#        echo "queue not empty trying again"
#        echo "Retrying..."; sleep 15;
#    else
#        echo "queue empty"
#        break;
#    fi
#done
