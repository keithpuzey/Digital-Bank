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
               def json2 = new JsonSlurper().parseText(response.content.result.id)
         //      echo "Status: ${response.status}"
               echo "Mock Service IDs: ${json.result.id}"
               echo "this is the ID value ${json2}"
            }
         echo 'Prepare Environment - Start Mock Services'
            //script {
            // def url = "https://mock.blazemeter.com/api/v1/workspaces/350345/service-mocks/"+ ${json.result.id} + "/deploy"
            // echo url
            // def response = httpRequest authentication: 'credentialsID', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: ${url}
            // def json = new JsonSlurper().parseText(response.content)
            // echo "Status: ${response.status}"
            // echo "Mock Service Tracking IDs: ${json.result.trackingUrl}"
            //}
            sh 'sleep 600'
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
