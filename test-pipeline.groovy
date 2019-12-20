pipeline {
	agent any
	stages {
		stage('clean') {
			steps {
				deleteDir()
			}
		}
		
		stage('Running tests') {
			parallel {
				stage('maven-positive-test1') {
					steps {
						script {
							def testRunner = build job: 'test-runner', parameters: [
								string(name: 'repository', value: 'https://github.com/mefmor/maven-positive-test1.git')]
							
							sh "cp -r ${testRunner.getBuildVariables()['TEST_WORKSPACE']}/target/surefire-reports/*.xml ."
							
							junit '*.xml'
							
						}
					}
				}
				
				stage('simple-java-maven-app') {
					steps {
						script {
							def testRunner = build job: 'test-runner', parameters: [
								string(name: 'repository', value: 'https://github.com/mefmor/simple-java-maven-app.git')]
							
							sh "cp -r ${testRunner.getBuildVariables()['TEST_WORKSPACE']}/target/surefire-reports/*.xml ."
							
							junit '*.xml'
							
						}
					}
				}
			}
		}
		
    }
}