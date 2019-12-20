pipeline {
	agent any
	stages {
		stage('Clean workspace') {
			steps {
				deleteDir()
			}
		}
		
		stage('Running tests') {
			parallel {
				stage('maven-positive-test1') {
					steps {
						build job: 'test-runner', parameters: [
							string(name: 'repository', value: 'https://github.com/mefmor/maven-positive-test1.git'),
							string(name: 'store_folder', value: "${env.WORKSPACE}")]
					}
				}
				
				
				stage('maven-negative-test1') {
					steps {
						build job: 'test-runner', parameters: [
							string(name: 'repository', value: 'https://github.com/mefmor/maven-negative-test1.git'),
							string(name: 'store_folder', value: "${env.WORKSPACE}")]
					}
				}
				
				stage('simple-java-maven-app') {
					steps {
						build job: 'test-runner', parameters: [
							string(name: 'repository', value: 'https://github.com/mefmor/simple-java-maven-app.git'),
							string(name: 'store_folder', value: "${env.WORKSPACE}")]
					}
				}
				
			}
			post {
                always {
					sh 'ls'
					junit '*.xml' 
                }
            }
		}
		
    }
}