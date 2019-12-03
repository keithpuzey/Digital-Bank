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
        "name": "Jenkins Build ", 
        "noMatchingRequestPreference": "return404", 
        "serviceId": 1448, 
        "thinkTime": 0, 
        "transactionIds": 12072}"""
               def response = httpRequest authentication: 'credentialsID', contentType: 'APPLICATION_JSON', httpMode: 'POST', requestBody: patchOrg, url: "https://mock.blazemeter.com/api/v1/workspaces/350345/service-mocks"
               def json = new JsonSlurper().parseText(response.content)
               echo "Status: ${response.status}"
               echo "Mock Service IDs: ${json.result.id}"
               env.MOCKID = "${json.result.id}"
               echo "this is the mock id variable $env.MOCKID"
            }
         echo 'Prepare Environment - Start Mock Services'
            script {
               def response = httpRequest authentication: 'credentialsID', contentType: 'APPLICATION_JSON', httpMode: 'GET', url: "https://mock.blazemeter.com/api/v1/workspaces/350345/service-mocks/"+$env.MOCKID+"/deploy"
               def json = new JsonSlurper().parseText(response.content)
               echo "Status: ${response.status}"
               echo "Mock Service Tracking IDs: ${json.result.trackingUrl}"
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
