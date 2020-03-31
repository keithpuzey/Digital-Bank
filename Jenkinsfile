import groovy.json.JsonSlurper

def workspaceID = 350345
def ServiceID = 1448
def MockThinkTime = 0

pipeline {
   agent any
   stages {
      stage('Development') {
         steps {
            echo 'Extract Build to Develpoment Environment'
            echo 'Prepare Environment - Create Mock Services'
            script {
           def patchOrg = """
                {"description": "Jenkins Created Mock Service", 
	"harborId":"5c544422c7dc9735767b23ce",
	"shipId":"5d3ccab3526ad28f53205574",
        "endpointPreference": "HTTPS", 
        "name": "Jenkins Build $BUILD_NUMBER", 
        "noMatchingRequestPreference": "return404", 
        "serviceId": ${ServiceID}, 
        "thinkTime": ${MockThinkTime}, 
        "mockServiceTransactions":[{"txnId":12072,"priority":10},{"txnId":12073,"priority":10},{"txnId":12074,"priority":10}]}"""
	       
	// Create Mock Service using payload patchOrg
		    
	       def response = httpRequest authentication: 'credentialsID', contentType: 'APPLICATION_JSON', httpMode: 'POST', requestBody: patchOrg, url: "https://mock.blazemeter.com/api/v1/workspaces/" + workspaceID + "/service-mocks"
               def json = new JsonSlurper().parseText(response.content)
               mockid = json.result.id
             // echo "Status: ${response.status}"
             // echo "Mock Service IDs: ${json.result.id}"
            }
         echo "Prepare Environment - Start Mock Services - Jenkins Build " + BUILD_NUMBER
            script {
            // Start Mock Service
		    
	    def response = httpRequest authentication: 'credentialsID', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://mock.blazemeter.com/api/v1/workspaces/" +workspaceID + "/service-mocks/"+ mockid + "/deploy"
            def json = new JsonSlurper().parseText(response.content)
            // echo "Mock Service Tracking IDs: ${json.result.trackingUrl}"
            }
	    script {
            while (true) {
	    sleep 35

	    // Retrieve Status of Mock Service    
	    
	    def response = httpRequest authentication: 'credentialsID', acceptType: 'APPLICATION_JSON_UTF8', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://mock.blazemeter.com/api/v1/workspaces/" +workspaceID + "/service-mocks/"+ mockid
            def json = new JsonSlurper().parseText(response.content)
            mockendpoint = json.result.httpsEndpoint
            mockstat = json.result.status
            if ( mockstat == 'RUNNING') break
            }
           }  
           echo "Mock Service Jenkins Build " + BUILD_NUMBER + "  Started -  Endpoint details " + mockendpoint 
	   echo "Deploy Digital Bank Build" + BUILD_NUMBER + "  to Test Environment"
	   sleep 5
           echo "Configuring Digital Banking application with mock service details"
           echo "Build tests Running"
           script {
	// Start Blazemeter Test
	    echo "Start Blazemeter Test"
		   
	       def response = httpRequest authentication: 'credentialsID', contentType: 'APPLICATION_JSON', httpMode: 'POST', url: "https://a.blazemeter.com/api/v4/tests/7853380/Start"
               def json = new JsonSlurper().parseText(response.content)
               testsessionid = json.result.sessionsId[0]
             echo "Test Session ID :" + testsessionid
             echo "Test Response: ${json}"
            }

	    script {
            while (true) {
	    sleep 35

	    // Check Status of Test    
	    
	    def response = httpRequest authentication: 'credentialsID', acceptType: 'APPLICATION_JSON_UTF8', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://a.blazemeter.com:443/api/latest/sessions/"+testsessionid
	    def json = new JsonSlurper().parseText(response.content)
            testresult = json.result.failedThresholds
            teststat = json.result.status
	    echo "Test Response: ${json}"
            if ( teststat == 'ENDED') break
            }
	   echo "Test Results :" + testresult
           echo "Test Status:" + teststat	  
           }  
		 
		 
           sleep 5
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

	    // Delete Mock Service
		   
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
	stage('UAT') {
         steps {
            echo 'Deploy Build to QA Environment'
         }
      }
            }
}
