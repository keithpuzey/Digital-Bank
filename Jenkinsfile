import groovy.json.JsonSlurper

def workspaceID = '350345'
def ServiceID = '1448'
def MockServiceName = 'Jenkins Build $BUILD_NUMBER'
def MockServiceTransactions = '[{"txnId":12072,"priority":10},{"txnId":12073,"priority":10},{"txnId":12074,"priority":10}]}"""'
def MockThinkTime = '0'

pipeline {
   agent any
   stages {
      stage('Development') {
         steps {
            echo 'Deploy Build to Develpoment Environment'
            echo 'Prepare Environment - Create Mock Services'
            script {
           def patchOrg = """
                {"description": "${MockServiceName}", 
        "endpointPreference": "HTTPS", 
        "name": "Jenkins Build $BUILD_NUMBER", 
        "noMatchingRequestPreference": "return404", 
        "serviceId": ${ServiceID}, 
        "thinkTime": ${MockThinkTime}, 
        "mockServiceTransactions":${MockServiceTransactions}
               def response = httpRequest authentication: 'credentialsID', contentType: 'APPLICATION_JSON', httpMode: 'POST', requestBody: patchOrg, url: "https://mock.blazemeter.com/api/v1/workspaces/" + workspaceID + "/service-mocks"
               def json = new JsonSlurper().parseText(response.content)
               mockid = json.result.id
             // echo "Status: ${response.status}"
             // echo "Mock Service IDs: ${json.result.id}"
            }
         echo "Prepare Environment - Start Mock Services - Jenkins Build " + BUILD_NUMBER
            script {
            def response = httpRequest authentication: 'credentialsID', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://mock.blazemeter.com/api/v1/workspaces/" +workspaceID + "/service-mocks/"+ mockid + "/deploy"
            def json = new JsonSlurper().parseText(response.content)
            // echo "Mock Service Tracking IDs: ${json.result.trackingUrl}"
            }
	    script {
            while (true) {
	    sleep 35
            def response = httpRequest authentication: 'credentialsID', acceptType: 'APPLICATION_JSON_UTF8', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://mock.blazemeter.com/api/v1/workspaces/" +workspaceID + "/service-mocks/"+ mockid
            def json = new JsonSlurper().parseText(response.content)
            mockendpoint = json.result.httpsEndpoint
            mockstat = json.result.status
            if ( mockstat == 'RUNNING') break
            }
           }  
           echo "Mock Service Jenkins Build " + BUILD_NUMBER + "  Started -  Endpoint details " + mockendpoint 
           echo "Configuring Digital Banking application with mock service details"
           script {
            // Define Variable
             def USER_INPUT = input(
                    message: 'Deployment Paused',
                    parameters: [
                            [$class: 'ChoiceParameterDefinition',
                             choices: ['Yes','No'].join('\n'),
                             name: 'input',
                             description: 'Do you want to proceed?']
                    ])

            echo "The answer is: ${USER_INPUT}"

            if( "${USER_INPUT}" == "Yes"){
            echo "Deployment Continuing"
            } else {
	    echo "Deployment Cancelled by user input"
       	   script {
            def response = httpRequest authentication: 'credentialsID', contentType: 'APPLICATION_JSON', httpMode: 'DELETE', url: "https://mock.blazemeter.com/api/v1/workspaces/" +workspaceID + "/service-mocks/"+ mockid
            echo "Deleting Mock Service Jenkins Build " + BUILD_NUMBER
            }
            break
            }
        }
	   script {
            def response = httpRequest authentication: 'credentialsID', contentType: 'APPLICATION_JSON', httpMode: 'DELETE', url: "https://mock.blazemeter.com/api/v1/workspaces/" +workspaceID + "/service-mocks/"+ mockid
            echo "Deleting Mock Service Jenkins Build " + BUILD_NUMBER
            }
           }
          }
         stage('QA') {
         steps {
            echo 'Deploy Build to QA Environment'
         }
      }
            }
}
