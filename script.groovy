def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 


def testapp() {
    echo "testing the applactions..."
        sh 'mvn test'
    }

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'dockerhub-accout', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t ahmedredadev/jave-maven-app:jma-2.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push ahmedredadev/jave-maven-app:jma-2.0'
    }
} 

def deployApp() {
    echo 'deploying the application...'
} 

return this