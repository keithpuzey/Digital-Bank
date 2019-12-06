import groovy.json.JsonSlurper

pipeline {
   agent any
   stages {
      stage('Development') {
         steps {
            echo 'Deploy Build to Develpoment Environment'
            echo 'Prepare Environment - Create Mock Services'
            script {
           def patchOrg = """
                {"description": "Jenkins Created Mock Service", 
        "endpointPreference": "HTTPS", 
        "name": "Jenkins Build $BUILD_NUMBER", 
        "noMatchingRequestPreference": "return404", 
        "serviceId": 1448, 
        "thinkTime": 0, 
        "transactionIds": 12072}"""
               def response = httpRequest authentication: 'credentialsID', contentType: 'APPLICATION_JSON', httpMode: 'POST', requestBody: patchOrg, url: "https://mock.blazemeter.com/api/v1/workspaces/350345/service-mocks"
               def json = new JsonSlurper().parseText(response.content)
               mockid = json.result.id
             // echo "Status: ${response.status}"
             // echo "Mock Service IDs: ${json.result.id}"
            }
         echo 'Prepare Environment - Start Mock Services'
            script {
            def response = httpRequest authentication: 'credentialsID', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://mock.blazemeter.com/api/v1/workspaces/350345/service-mocks/"+ mockid + "/deploy"
            def json = new JsonSlurper().parseText(response.content)
            // echo "Mock Service Tracking IDs: ${json.result.trackingUrl}"
            }
			script {
            while (true) {
            sleep 20
            def response = httpRequest authentication: 'credentialsID', acceptType: 'APPLICATION_JSON_UTF8', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://mock.blazemeter.com/api/v1/workspaces/350345/service-mocks/"+ mockid
            def json = new JsonSlurper().parseText(response.content)
            mockendpoint = json.result.httpsEndpoint
            mockstat = json.result.status
            if ( mockstat == 'RUNNING') break
            }
           }  
           echo "Mock Service Jenkins Build " + $BUILD_NUMBER + "Started Endpoint details " + mockendpoint 
           echo "Configuring Digital Banking application with mock service details"
           sleep 30
		    script {
            def response = httpRequest authentication: 'credentialsID', contentType: 'APPLICATION_JSON', httpMode: 'DELETE', url: "https://mock.blazemeter.com/api/v1/workspaces/350345/service-mocks/"+ mockid
            echo "Deleting Mock Service Jenkins Build " + $BUILD_NUMBER
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
            echo 'Deploy Build to UAT Environment'
         }
      }
      stage('Pre Production') {
         steps {
            echo 'Deploy Build to Pre - Production Environment'
         }
      }
     stage('Production') {
         steps {
            echo 'Deploy Build to Production Environment'
                }
              }   
            }
}
