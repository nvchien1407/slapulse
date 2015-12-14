if [ $# -ne 4 ]; then
    echo "Usage: $0 user pass server file"
    echo "Example:"
    echo "$0 user password http://localhost:8080/slapulse workflow.xml"
    exit -1
fi

user=$1
pass=$2
# remove trailing /
server=${3%/}
file=$4

here=`dirname $0`
${here}/upload.sh $user $pass $server/importExport.html fileWorkflow=@$file 
#| grep 'Workflow import'
