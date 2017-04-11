node {
   def mvnHome
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
	  git 'https://github.com/lahela96/Denver/'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'M3'
      env.JAVA_HOME = tool 'JDK1'
   }
   stage('Build') {
      // Run the maven build
      if (isUnix()) {
         //sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
         sh 'echo "${mvnHome}/bin/mvn clean package spring-boot:run -Dstart-class=com.colorado.denver.DenverApplication" | at now + 1 minutes'
      } else {
         //bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package -Dstart-class=com.colorado.denver.DenverApplication/)
        // bat(/"${mvnHome}\bin\mvn" spring-boot {com.colorado.denver.DenverApplication}:start /)
        bat(/"${mvnHome}\bin\mvn" clean package spring-boot:run -Dstart-class=com.colorado.denver.DenverApplication/)
      }
   }
   stage('Results') {
      //junit '**/target/surefire-reports/TEST-*.xml'
      archive 'target/*.war'
   }
}
