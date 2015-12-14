if [ $# -ne 4 ]; then
    echo "Usage: $0 user pass url payload"
    echo "payload can be JSON string or @filename"
    echo "Example:"
    echo "$0 user password http://localhost:8080/saigon/rest/adminroles/import @ar5.js"
    exit -1
fi

user=$1
pass=$2
url=$3
payload=$4

cookies_file=/tmp/cookies.$$
here=`dirname $0`

# tomcat insists you first access the protected resource
curl --cookie-jar $cookies_file -X POST -d "$payload" $url > /dev/null

$here/auth.sh $user $pass $url $cookies_file

curl -b $cookies_file -X POST -d "$payload" $url
rm $cookies_file
