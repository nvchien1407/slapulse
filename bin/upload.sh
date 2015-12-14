if [ $# -lt 4 -o $# -gt 5 ]; then
    echo "Usage: $0 user pass url payload [payload2]"
    echo "Example:"
    echo "$0 user password url fileWorkflow=@workflow.xml"
    exit -1
fi

user=$1
pass=$2
url=$3
payload="-F $4"
if [ $# -gt 4 ]; then
    payload2="-F $5"
fi

cookies_file=/tmp/cookies.$$
here=`dirname $0`

# tomcat insists you first access the protected resource
curl --cookie-jar $cookies_file $payload $payload2 $url > /dev/null 2>&1

$here/auth.sh $user $pass $url $cookies_file

curl -b $cookies_file $payload $payload2 $url 
rm $cookies_file
