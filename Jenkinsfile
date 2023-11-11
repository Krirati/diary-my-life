pipeline {
    agent any
    stages {
        stage('Setup') {
            steps {
                sh 'chmod +x ./gradlew'
            }
        }
        stage('Clean Build') {
            steps {
                sh 'echo "Clean Build"'
                sh './gradlew clean --no-daemon'
            }
        }
        stage('Test') {
            steps {
                sh 'echo "Test"'
                sh './gradlew testDevDebugUnitTest'
            }
        }
        stage('SonarQube') {
            steps {
                sh 'echo SonarQube'
            }
        }
        stage('Check Ktlint') {
            steps {
                sh 'echo "Check Ktlint"'
                sh './gradlew ktlintCheck'
            }
        }
        stage('Build') {
            steps {
                sh 'echo "Build"'
                sh './gradlew assembleDebug'
            }
        }
        stage('Compile') {
            steps {
                archiveArtifacts artifacts: '**/*.apk', fingerprint: true, onlyIfSuccessful: true
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