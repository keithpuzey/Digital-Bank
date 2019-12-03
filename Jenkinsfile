pipeline {
   agent any

   stages {
      stage('Development') {
         steps {
            echo 'Deploy Build to Develpoment Environment'
            echo 'Prepare Environment - Start Mock Services'
            sh 'curl "https://mock.blazemeter.com/api/v1/workspaces/350345/services" -X POST -H 'accept: */*' -H 'Content-Type: application/json' --user "95c533ba1f5890ea027af301:913a87b2d34a4bc3fa0242c532539eec28dfc5484b393494e94499b6bedaa29db560a00d" -d '{"description":"Mock Service created from Jenkins"}''
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
