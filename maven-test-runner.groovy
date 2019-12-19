pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
	parameters {
        string(name: 'repository', 
			defaultValue: 'https://github.com/mefmor/maven-positive-test1.git', 
			description: 'Repository that will be used as test')
    }
    stages {
		stage('Checkout') {
			steps {
				git "${params.repository}"
			}
		}
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'

				script {
					env.TEST_WORKSPACE = env.WORKSPACE
				}
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}