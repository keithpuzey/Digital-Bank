pipeline {
   agent any

   stages {
      stage('Development') {
         steps {
            echo 'Deploy Build to Develpoment Environment'
            echo 'Prepare Environment - Start Mock Services'
            script {
               def response = httpRequest 'https://mock.blazemeter.com/api/v1/workspaces/350345/service-mocks'
               def json = new JsonSlurper().parseText(response.content)

               echo "Status: ${response.status}"

               echo "Mock Service DetailsDogs: ${json.message.keySet()}"
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
