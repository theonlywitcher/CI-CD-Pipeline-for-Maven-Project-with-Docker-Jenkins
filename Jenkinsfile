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
        stage("increment version") {
            steps {
                script {
                    echo "incrementing version"
                    gv.incremantVersion()
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
                    gv.buildImage()
                }
            }
        }
        stage("deploy") {
            input{
                message "select the environment to deploy"
                ok "Yes, I'm sure y"
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