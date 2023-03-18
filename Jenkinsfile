pipeline {
    agent any
    stages {
        stage('Clean Build') {
            steps {
                sh 'echo "Clean Build"'
                sh './gradlew clean'
            }
        }
        stage('Check Ktlint') {
            steps {
                sh 'echo "Check Ktlint"'
                sh './gradlew ktlint'
            }
        }
        stage('Test') {
            steps {
                sh 'echo "Test"'
                sh './gradlew testDebugUnitTest'
            }
        }
        stage('Build') {
            steps {
                sh 'echo "Build"'
            }
        }
        stage('Deploy') {
            steps {
                sh 'echo "Deploy"'
            }
        }
    }
}