pipeline {
    agent any
    stages {
        stage('Setup') {
            steps {
                sh 'chmod +x ./gradlew'
            }
        }
//        stage('Clean Build') {
//            steps {
//                sh 'echo "Clean Build"'
//                sh './gradlew clean'
//            }
//        }
        stage('Test') {
            steps {
                sh 'echo "Test"'
                sh './gradlew testDevDebugUnitTest'
            }
        }
//        stage('Check Ktlint') {
//            steps {
//                sh 'echo "Check Ktlint"'
//                sh './gradlew ktlint'
//            }
//        }
//        stage('Build') {
//            steps {
//                sh 'echo "Build"'
//                sh './gradlew assembleRelease'
//            }
//        }
//        stage('Deploy With fastlane') {
//            steps {
//                sh 'echo "Deploy"'
//            }
//        }
        stage('Notify') {
            steps {
                sh 'echo "Notify"'
            }
        }
    }
}