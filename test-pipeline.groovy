pipeline {
	agent any
	stages {
		stage('Run tests') {
			steps {
				script {
				
					def testRunner = build job: 'test-runner', parameters: [
						string(name: 'repository', value: 'https://github.com/mefmor/maven-positive-test1.git')]
					
					echo testRunner.getBuildVariables()['TEST_WORKSPACE']
				
				}
			}
		}
    }
}