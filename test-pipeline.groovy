pipeline {
	agent any
	stages {
		stage('Run tests') {
			steps {
				script {
				
					def testRunner = build job: 'test-runner', parameters: [
						string(name: 'repository', value: 'https://github.com/mefmor/maven-positive-test1.git')]
					
					def testWorkspace = testRunner.getBuildVariables()['TEST_WORKSPACE']
					
					sh "ls ${testWorkspace}"
					sh "cp -r ${testWorkspace}/target/surefire-reports/*.xml ."
					
					junit '*.xml'
					
				}
			}
		}
    }
}