pipeline {
	agent any
	stages {
		stage('Run tests') {
			steps {
				build job: 'test-runner', parameters: [
					string(name: 'repository', value: 'https://github.com/mefmor/maven-positive-test1.git')]
			}
		}
    }
}