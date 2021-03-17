import groovy.json.JsonSlurper

def workspaceID = 348613
def ServiceID = "1180"
def MockThinkTime = "0"
def BMTestID = 8951028
def BMfunctest = 8969143
def account = 291446
def BMfuncsuitetest = 10112353

	
pipeline {
   agent any
   stages {
      stage('Development') {
         steps {
            echo 'Extract Build to Develpoment Environment'
            echo 'Prepare Environment - Create Mock Services'
            script {
	// KeithP Workspace and VISA Payment Service
               def payload = """{ "description": "Jenkins Build $BUILD_NUMBER", "endpointPreference": "HTTPS", "harborId": "5c544422c7dc9735767b23ce","type": "TRANSACTIONAL", "liveSystemHost": "null", "liveSystemPort": "null", "name": "Jenkins Build $BUILD_NUMBER", "serviceId": ${ServiceID}, "shipId":"5d3ccab3526ad28f53205574" ,"thinkTime": ${MockThinkTime}, "mockServiceTransactions": [{"txnId":9500,"priority":10},{"txnId":9501,"priority":10},{"txnId":9502,"priority":10}]}"""

	// Create Mock Service using payload patchOrg
	       def response = httpRequest authentication: 'BMCredentials', contentType: 'APPLICATION_JSON', httpMode: 'POST', requestBody: payload , url: "https://mock.blazemeter.com/api/v1/workspaces/" + workspaceID + "/service-mocks"
               def json = new JsonSlurper().parseText(response.content)
               mockid = json.result.id
               echo "Mock Service IDs: ${json.result.id}"
            }
         echo "Prepare Environment - Start Mock Services - Jenkins Build " + BUILD_NUMBER
            script {
            // Start Mock Service
		    
	    def response = httpRequest authentication: 'BMCredentials', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://mock.blazemeter.com/api/v1/workspaces/" +workspaceID + "/service-mocks/"+ mockid + "/deploy"
            def json = new JsonSlurper().parseText(response.content)
            }
	    script {
            while (true) {
	    sleep 120

	    // Retrieve Status of Mock Service    
	    
	    def response = httpRequest authentication: 'BMCredentials', acceptType: 'APPLICATION_JSON_UTF8', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://mock.blazemeter.com/api/v1/workspaces/" +workspaceID + "/service-mocks/"+ mockid
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
           script {
	// Start Blazemeter  Performance Test
	    echo "Start Blazemeter Performance Test "
		   
	       def response = httpRequest authentication: 'BMCredentials', contentType: 'APPLICATION_JSON', httpMode: 'POST', url: "https://a.blazemeter.com/api/v4/tests/"+BMTestID+"/Start"
               def json = new JsonSlurper().parseText(response.content)
               testsessionid = json.result.sessionsId[0]
	       echo "Test Session ID =  " + testsessionid
	   }
	    script {
            while (true) {
	    sleep 120
	    // Check Status of Test    
	    def response = httpRequest authentication: 'BMCredentials', acceptType: 'APPLICATION_JSON_UTF8', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://a.blazemeter.com:443/api/latest/sessions/"+testsessionid
	    def json = new JsonSlurper().parseText(response.content)
            testthreshold = json.result.failedThresholds
            teststat = json.result.status
            if ( teststat == 'ENDED') break
            }
            if (testthreshold == 0 ) {
                echo 'Test Passed'
		testresult = "Blazemeter Performance Test Passed"
            } else {
                echo 'Test Failed '
		testresult = "Blazemeter Performance Test Failed"
            }  
           } 
		 
script {
// Start Blazemeter Functional Test
echo "Define Test Data"
def datamodel = """{"dependencies":{"data":{"kind":"tdm","type":"object",
"properties":{"FirstName":{"type":"string"},"LastName":{"type":"string"},"EmailAddress":{"type":"string"},"SSN":{"type":"string"},"home_phone":{"type":"string"},"mobile_phone":{"type":"string"},"work_phone":{"type":"string"},"index":{"type":"string"},"dob":{"type":"string"},"address":{"type":"string"},"city":{"type":"string"},"state":{"type":"string"},"zip_code":{"type":"string"},"jenkins_build":{"type":"string"},"url":{"type":"string"},"title":{"type":"string"}},
"requirements":{"FirstName":"ifCondition(\${title} == \\"Mr.\\",randFromSeedlist(\\"firstnamemaleamerican\\"),randFromSeedlist(\\"firstnamefemaleamerican\\")).replace(\\/ \\/g,\\".\\")","LastName":"randlov(0, seedlist(\\"lastnames\\"))","EmailAddress":"(\${FirstName}+\\".\\"+\${LastName}+\\"@gmail.com\\").replace(\\/ \\/g,\\".\\")","SSN":"randDigits(3,3)+\\"-\\"+randDigits(2,2)+\\"-\\"+randDigits(4,4)","home_phone":"\\"(\\"+randDigits(3,3)+\\")\\"+randDigits(3,3)+\\"-\\"+randDigits(4,4)","mobile_phone":"\\"(\\"+randDigits(3,3)+\\")\\"+randDigits(3,3)+\\"-\\"+randDigits(4,4)","work_phone":"\\"(\\"+randDigits(3,3)+\\")\\"+randDigits(3,3)+\\"-\\"+randDigits(4,4)","index":"randInt(1,100)","jenkins_build":"${BUILD_NUMBER}","dob":"datetime(dateOfBirth(18, 100,now()),\\"MM/DD/YYYY\\")","address":"valueFromSeedlist(\\"usaddress-multicol\\", \${index}, 1)","city":"valueFromSeedlist(\\"usaddress-multicol\\", \${index}, 2) ","state":"valueFromSeedlist(\\"usaddress-multicol\\", \${index}, 3) ","zip_code":"valueFromSeedlist(\\"usaddress-multicol\\", \${index}, 4)","url":"\\"http://dbankdemo.com/bank\\"","title":"randFromList([\\"Mr.\\",\\"Mrs.\\",\\"Ms.\\"])"},
"repeat":"5" }}}"""
	    
	    
 def dmresponse = httpRequest authentication: 'BMCredentials', contentType: 'APPLICATION_JSON', httpMode: 'PUT', requestBody: datamodel , url: "https://a.blazemeter.com/api/v4/tests/"+BMfunctest
 // def dmjson = new JsonSlurper().parseText(dmresponse.content)
	    
echo "Start Blazemeter Functional Test"
		   
	     def response = httpRequest authentication: 'BMCredentials', contentType: 'APPLICATION_JSON', httpMode: 'POST', url: "https://a.blazemeter.com/api/v4/tests/"+BMfunctest+"/start"
             def json = new JsonSlurper().parseText(response.content)
             testmasterid = json.result.id
	   }
	    script {
            while (true) {
	    sleep 120
	    // Check Status of Test    
	    def response = httpRequest authentication: 'BMCredentials', acceptType: 'APPLICATION_JSON_UTF8', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://a.blazemeter.com:443/api/v4/masters/"+testmasterid+"/status?events=false"
	    def json = new JsonSlurper().parseText(response.content)
            teststat = json.result.status
	    echo "Test Status = " + teststat
            if ( teststat == "ENDED") break
            }
	    def response = httpRequest authentication: 'BMCredentials', acceptType: 'APPLICATION_JSON_UTF8', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://a.blazemeter.com:443/api/v4/masters/"+testmasterid+"/full?external=false"
	    def json = new JsonSlurper().parseText(response.content)
            projectID = json.result.projectId
	    testresult = json.result.passed
            if (testresult == true ) {
                echo 'Test Passed'
		echo "Test details : https://a.blazemeter.com/app/#/accounts/"+account+"/workspaces/"+workspaceID+"/projects/"+projectID+"/masters/"+testmasterid+"/cross-browser-summary"
		testresult = "Blazemeter Test Passed"
            } else {
                echo 'Test Failed '
		"Test details : https://a.blazemeter.com/app/#/accounts/"+account+"/workspaces/"+workspaceID+"/projects/"+projectID+"/masters/"+testmasterid+"/cross-browser-summary"
		testresult = "Blazemeter Test Failed"
            }  
           }  
           script {
	// Start Blazemeter Functional Test Suite
	    echo "Define Test Data"
def datamodel = """{"dependencies":{"data":{"kind":"tdm","type":"object",
"properties":{"FirstName":{"type":"string"},"LastName":{"type":"string"},"EmailAddress":{"type":"string"},"SSN":{"type":"string"},"home_phone":{"type":"string"},"mobile_phone":{"type":"string"},"work_phone":{"type":"string"},"index":{"type":"string"},"dob":{"type":"string"},"address":{"type":"string"},"city":{"type":"string"},"state":{"type":"string"},"zip_code":{"type":"string"},"jenkins_build":{"type":"string"},"url":{"type":"string"},"title":{"type":"string"}},
"requirements":{"FirstName":"ifCondition(\${title} == \\"Mr.\\",randFromSeedlist(\\"firstnamemaleamerican\\"),randFromSeedlist(\\"firstnamefemaleamerican\\")).replace(\\/ \\/g,\\".\\")","LastName":"randlov(0, seedlist(\\"lastnames\\"))","EmailAddress":"(\${FirstName}+\\".\\"+\${LastName}+\\"@gmail.com\\").replace(\\/ \\/g,\\".\\")","SSN":"randDigits(3,3)+\\"-\\"+randDigits(2,2)+\\"-\\"+randDigits(4,4)","home_phone":"\\"(\\"+randDigits(3,3)+\\")\\"+randDigits(3,3)+\\"-\\"+randDigits(4,4)","mobile_phone":"\\"(\\"+randDigits(3,3)+\\")\\"+randDigits(3,3)+\\"-\\"+randDigits(4,4)","work_phone":"\\"(\\"+randDigits(3,3)+\\")\\"+randDigits(3,3)+\\"-\\"+randDigits(4,4)","index":"randInt(1,100)","jenkins_build":"${BUILD_NUMBER}","dob":"datetime(dateOfBirth(18, 100,now()),\\"MM/DD/YYYY\\")","address":"valueFromSeedlist(\\"usaddress-multicol\\", \${index}, 1)","city":"valueFromSeedlist(\\"usaddress-multicol\\", \${index}, 2) ","state":"valueFromSeedlist(\\"usaddress-multicol\\", \${index}, 3) ","zip_code":"valueFromSeedlist(\\"usaddress-multicol\\", \${index}, 4)","url":"\\"http://dbankdemo.com/bank\\"","title":"randFromList([\\"Mr.\\",\\"Mrs.\\",\\"Ms.\\"])"},
"repeat":"5" }}}"""
		   
	    def dmresponse = httpRequest authentication: 'BMCredentials', contentType: 'APPLICATION_JSON', httpMode: 'PUT', requestBody: datamodel , url: "https://a.blazemeter.com/api/v4/tests/"+BMfunctest
            // def dmjson = new JsonSlurper().parseText(dmresponse.content)
	    
	    echo "Start Blazemeter Functional Test Suite"
		   
	     def response = httpRequest authentication: 'BMCredentials', contentType: 'APPLICATION_JSON', httpMode: 'POST', url: "https://a.blazemeter.com/api/v4/multi-tests/"+BMfuncsuitetest+"/start"
             def json = new JsonSlurper().parseText(response.content)
             testmasterid = json.result.id
	   }
	    script {
            while (true) {
	    sleep 120
	    // Check Status of Test    
	    def response = httpRequest authentication: 'BMCredentials', acceptType: 'APPLICATION_JSON_UTF8', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://a.blazemeter.com:443/api/v4/masters/"+testmasterid+"/test-suite-summary"
	    def json = new JsonSlurper().parseText(response.content)
            endtime = json.result.suiteSummary.ended
	    echo "End Time = " + endtime
            if ( endtime > 0 ) break
            }
	    def suiteresponse = httpRequest authentication: 'BMCredentials', acceptType: 'APPLICATION_JSON_UTF8', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://a.blazemeter.com:443/api/v4/masters/"+testmasterid+"/full?external=false"
	    def suitejson = new JsonSlurper().parseText(suiteresponse.content)
            projectID = suitejson.result.projectId
            testresult = suitejson.result.passed
            if (testresult == true ) {
                echo 'Test Passed'
		echo "Test details : https://a.blazemeter.com/app/#/accounts/"+account+"/workspaces/"+workspaceID+"/projects/"+projectID"+/masters/"+testmasterid+"/suite-report"
		testresult = "Blazemeter Test Suite Passed"
            } else {
                echo 'Test Failed '
		echo "Test details : https://a.blazemeter.com/app/#/accounts/"+account+"/workspaces/"+workspaceID+"/projects/"+projectID+"/masters/"+testmasterid+"/suite-report"
		testresult = "Blazemeter Test Suite Failed"
            }  
           }  

           script {
            // Define Variable
             def USER_INPUT = input(
                    message: 'Deployment Paused                ' + testresult,
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
		   
	    def response = httpRequest authentication: 'BMCredentials', contentType: 'APPLICATION_JSON', httpMode: 'DELETE', url: "https://mock.blazemeter.com/api/v1/workspaces/" +workspaceID + "/service-mocks/"+ mockid
            echo "Deleting Mock Service Jenkins Build " + BUILD_NUMBER
            }
            break
            }
        }
	   script {
            def response = httpRequest authentication: 'BMCredentials', contentType: 'APPLICATION_JSON', httpMode: 'DELETE', url: "https://mock.blazemeter.com/api/v1/workspaces/" +workspaceID + "/service-mocks/"+ mockid
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
