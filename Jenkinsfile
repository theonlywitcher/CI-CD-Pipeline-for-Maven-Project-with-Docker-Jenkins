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
                    echo "building jar file"
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
            input {
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
        stage ("commit Version update") {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'Github account', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        sh 'git config user.email "jenkins@local"'
                        sh 'git config user.name "jenkins"'
                        
                        sh 'git status'
                        sh 'git branch'
                        sh 'git config --list'

                        sh 'git config set-url origin https://${USER}:${PASS}@https://github.com/theonlywitcher/jave-maven-app"'
                        sh 'git add .'
                        sh 'git commit -m "Jenkins: Bumping version to ${IMAGE_NAME}"'
                        sh 'git push origin HEAD:main'
                    }
                }
            }
        }
    }
}