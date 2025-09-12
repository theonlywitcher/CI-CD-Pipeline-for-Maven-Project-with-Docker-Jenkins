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
                    echo "building jar"
                    gv.buildJar()
                }
            }
        }
        stage("test app") {
            steps {
                script {
                    echo "testing app"
                    gv.testapp()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    echo "building image"
                    echo "building image again"
                    gv.buildImage()
                }
            }
        }
        stage("deploy") {
            input{
                message "select the environment to deploy"
                ok "Yes, I'm sure"
                parameters {
                    choice(name : 'env', choices: ['dev', 'staging', 'prod'], description: 'Select the environment')
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
