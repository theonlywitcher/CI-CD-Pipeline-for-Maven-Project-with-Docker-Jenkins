@Library('jenkins-shared-library')
def gv

pipeline {
    agent any

    tools {
        maven 'maven-3.9' 
    }
    stages { 
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    buildJar()
                }
            }
        }
        stage("build image and push to dockerhub") {
            steps {
                script {
                    buildImage ('ahmedredadev/jave-maven-app:jma-2.0')
                    dockerLogin()
                    dockerPush('ahmedredadev/jave-maven-app:jma-2.0')
                }
            }
        }
        stage("deploy") {
            input{
                message "select the environment to deploy"
                ok "Yes, I'm sure"
                parameters {
                    choice(name :'env', choices: ['dev', 'staging', 'prod'], description: 'Select the environment')
                }
            }
            steps {
                script {
                    gv.deployApp()
                    echo "deploying to ${params.env}"
                }
            }
        }
    }  
}