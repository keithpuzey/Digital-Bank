pipeline {
   agent any

   stages {
      stage('Development') {
         steps {
            echo 'Deploy Build to Develpoment Environment'
            echo 'Prepare Environment - Start Mock Services'
            sh 'curl -X POST https://mock.blazemeter.com/api/v1/workspaces/350345/service-mocks -H "Accept-Encoding: gzip, deflate" -H "Authorization: Basic OTVjNTMzYmExZjU4OTBlYTAyN2FmMzAxOjkxM2E4N2IyZDM0YTRiYzNmYTAyNDJjNTMyNTM5ZWVjMjhkZmM1NDg0YjM5MzQ5NGU5NDQ5OWI2YmVkYWEyOWRiNTYwYTAwZA==" -H "Cache-Control: no-cache"  -H "Connection: keep-alive" 
  -H "Content-Length: 271" -H "Content-Type: application/json" -H "Host: mock.blazemeter.com" -H "accept: */*" -d '{ "description": "Jenkins Created Mock Service", 
        "endpointPreference": "HTTPS", 
        "name": "Jenkins Build ", 
        "noMatchingRequestPreference": "return404", 
        "serviceId": 1448, 
        "thinkTime": 0, 
        "transactionIds": 12072
}''
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
