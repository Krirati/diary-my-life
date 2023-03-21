pipeline {
    agent any
    stages {
        stage('Clean Build') {
            steps {
                sh 'echo "Clean Build"'
                sh './gradlew clean --no-daemon'
            }
        }
        stage('Test') {
            steps {
                sh 'echo "Test"'
                sh './gradlew testDebugUnitTest --no-daemon'
            }
        }
        stage('Check Ktlint') {
            steps {
                sh 'echo "Check Ktlint"'
                sh './gradlew ktlint'
            }
        }
        stage('Build') {
            steps {
                sh 'echo "Build"'
                sh './gradlew assembleRelease'
            }
        }
        stage('Deploy With fastlane') {
            steps {
                sh 'echo "Deploy"'
            }
        }
        stage('Notify') {
            steps {
                sh 'echo "Notify"'
            }
        }
    }
}