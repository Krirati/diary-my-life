pipeline {
    agent any
    stages {
        stage('Clean Build') {
            steps {
                sh 'echo "Clean Build"'
                sh './gradlew clean'
            }
        }
        stage('Test') {
            steps {
                sh 'echo "Test"'
                sh './gradlew testDevDebugUnitTestCoverage'
            }
        }
        stage('Build') {
            steps {
                sh 'echo "Test"'
            }
        }
        stage('Deploy') {
            steps {
                sh 'echo "Deploy"'
            }
        }
    }
}