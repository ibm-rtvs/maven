pipeline {
  agent any
  stages {
    stage('Run Tests') {
      steps {
        sh 'set'
        sh 'cp -f $JAVA_HOME/jre/lib/security/cacerts $WORKSPACE/my-cacerts'
        sh 'openssl s_client -connect $CONTROL_PANEL_TCP </dev/null | sed -ne "/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p" > my-rtcp.crt'
        sh '$JAVA_HOME/jre/bin/keytool -import -noprompt -alias rtcp -file my-rtcp.crt -keystore $WORKSPACE/my-cacerts -storepass changeit'
        sh 'sed -e s/localhost:5443/$CONTROL_PANEL_TCP/ -e "s#\\(<it-plugin-version>\\).*\\(</it-plugin-version>\\)#\\1$INTEGRATION_TESTER_PLUGIN_VERSION\\2#" pom-test.xml > my-pom-test.xml'
        sh 'MAVEN_OPTS=-Djavax.net.ssl.trustStore=$WORKSPACE/my-cacerts mvn -Dmaven.test.failure.ignore=true verify -f my-pom-test.xml -X'
      }
      post {
        always {
          junit 'target/failsafe-reports/TEST-*.xml' 
        }
      }
    }
  }
  environment {
    CONTROL_PANEL_TCP = 'localhost:5443'
    INTEGRATION_TESTER_PLUGIN_VERSION = '1.911.1'
    INTEGRATION_TESTER_HOME = '/var/jenkins_home/IBM/RationalIntegrationTester'
    LANG = 'en_US.UTF-8'
  }
  tools {
    maven 'Maven 3.5.3'
    jdk 'jdk8'
  }
}
