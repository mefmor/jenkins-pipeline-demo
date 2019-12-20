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
		string(name: 'store_folder',
			defaultValue: ".",
			description: 'Path to the directory where the results will be uploaded')
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
            }
            post {
                always {
					sh "mkdir -p ${params.store_folder}"
					sh "cp -r ./target/surefire-reports/*.xml ${params.store_folder}"
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}