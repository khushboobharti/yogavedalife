### AWS Configure
aws configure

## List Default
aws configure list --profile default

## List Profiles
aws configure list-profiles

## Switch between AWS accounts
./switchaws

## check credentials
vi credentials

## Change the link to credentials file to the required credentials file
ln -s credentials.personal credentials

## AWS Config
vi ~/.aws/config 

## AWS Login
ssh -i "<key-pair-file>" <ec2-user>@<host-name>

## Copy file to server (when running from tf folder)
scp -i ../tf/yv-key-pair-prod.pem <file-name-with-path> ec2-user@ec2-98-82-135-65.compute-1.amazonaws.com:/tmp/<file-name>
