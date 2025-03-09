
pipeline {

    agent any
    options {
        durabilityHint 'MAX_SURVIVABILITY'
    }
    stages {
        stage('Checkout') {
            steps {
                sh 'rm -rf shop_kotlin; git clone https://github.com/cherepakhin/shop_kotlin'
            }
        }

        stage('Unit tests') {
            steps {
                sh 'pwd;cd shop_kotlin;./gradlew clean test --tests *Test'
            }
        }

        stage('Integration tests') {
            steps {
                sh 'pwd;cd shop_kotlin;./gradlew clean test --tests *TestIntegration'
            }
        }

        stage('Build bootJar') {
            steps {
                sh 'pwd;cd shop_kotlin;./gradlew bootJar'
            }
        }

        stage('Publish to Nexus') {
            environment {
                NEXUS_CRED = credentials('nexus_admin')
            }
            steps {
                git url: 'https://github.com/cherepakhin/shop_kotlin.git', branch: 'dev'
                sh './gradlew publish'
            }
        }
    }
}