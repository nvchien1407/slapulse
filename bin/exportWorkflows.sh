if [ $# -lt 3 ]; then
    echo "Usage: $0 user pass server"
    echo "Example:"
    echo "$0 user password http://localhost:8080/slapulse > workflow.xml"
    exit -1
fi

user=$1
pass=$2
# remove trailing /
server=${3%/}

here=`dirname $0`
${here}/post.sh $user $pass $server/importExport.html?exportWorkflow=yes 'exportWorkflow=yes'
