# Use this script if the saigon web services have changed - see https://wiki.smsmt.com/confluence/display/slapulse/Support+and+Troubleshooting
wget -O xx http://kata-anz-release.dev.renewtek.com/kata/services/caseManagerRemote?wsdl
sed 's/:8080//' xx > ./core/src/main/resources/caseManager.wsdl
