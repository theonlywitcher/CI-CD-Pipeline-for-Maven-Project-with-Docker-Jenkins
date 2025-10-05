def incremantVersion() {
    echo "incrementing the app Version..."
    sh 'mvn build-helper:parse-version versions:set -DnewVersion=\\${parsedVersion.majorVersion}.\\${parsedVersion.minorVersion}.\\${parsedVersion.nextIncrementalVersion} versions:commit'
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version = matcher[0][1]
    env.IMAGE_NAME = "${version}-${BUILD_NUMBER}"
}

def buildJar() {
    echo "building the application..."
    sh 'mvn clean package'
} 


def testapp() {
    echo "testing the applactions..."
        sh 'mvn test'
    }

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'dockerhub-accout', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t ahmedredadev/jave-maven-app:${IMAGE_NAME} ."
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push ahmedredadev/jave-maven-app:${IMAGE_NAME}"
    }
} 

def deployApp() {
    dockerCmd = "docker run -d -p 8080:8080 --name jave-maven-app-${IMAGE_NAME} ahmedredadev/jave-maven-app:${IMAGE_NAME}"
    sshagent(['Ec2-Server']) {
    sh 'ssh -o StrictHostKeyChecking=no ec2user@98.81.215.132'
    }
} 

return this
