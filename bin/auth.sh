if [ $# -lt 3 ]; then
    echo "Usage: $0 user pass url [cookiesFile]"
    exit -1
fi

user=$1
pass=$2
# remove slapulse and everything after
url=${3%slapulse*}
url=${url}"slapulse/j_security_check"
cookies=${4:-/tmp/cookies.txt}

if [ "$user" = "-" ]; then
    read -s -p Username: user
fi
if [ "$pass" = "-" ]; then
    read -s -p Password: pass
fi

curl --data "j_username=${user}&j_password=${pass}" ${url} --cookie-jar $cookies -b $cookies
