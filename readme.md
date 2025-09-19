# CI/CD Pipeline for Maven Project with Docker & Jenkins

> a devops project — adapted and documented for clarity.

---

## Overview

This repository demonstrates a simple CI/CD pipeline for a Java Maven application using Jenkins and Docker. It includes:

- A `Jenkinsfile` that defines pipeline stages (checkout, build, test, package, docker image build, push).
- A `dockerfile` for containerizing the Maven-built artifact.
- A `pom.xml` for the Java/Maven project build.
- `script.groovy` (helper script or shared pipeline logic).

Files present in the repository: `.gitignore`, `Jenkinsfile`, `dockerfile`, `pom.xml`, `script.groovy`, and `src/` (application source). citeturn0view0

> **Note:** No `LICENSE` file or release artifacts were found in the repository root at the time of writing. If you want a license added, tell me which one.

---

## Prerequisites

Before running the pipeline or using the repo, ensure you have:

- Jenkins (LTS recommended) with the following plugins: Pipeline, Docker Pipeline (or Docker), Git.
- Docker Engine installed and accessible from the Jenkins agent (or use Docker-in-Docker carefully).
- Java JDK (matching the version used in `pom.xml`).
- Maven (or use the Maven wrapper if added).
- Credentials for Docker registry (if you plan to push images).

---

## Quick local steps (dev machine)

1. Clone the repo:

```bash
git clone https://github.com/theonlywitcher/CI-CD-Pipeline-for-Maven-Project-with-Docker-Jenkins.git
cd CI-CD-Pipeline-for-Maven-Project-with-Docker-Jenkins
```

2. Build with Maven:

```bash
mvn clean package
```

3. Build Docker image (replace `<tag>`):

```bash
docker build -t mygroup/myapp:<tag> -f dockerfile .
```

4. Run the container (example):

```bash
docker run --rm -p 8080:8080 mygroup/myapp:<tag>
```

Adjust ports and entrypoint according to the `dockerfile` and the app's configuration.

---

## Jenkins pipeline (high level)

The included `Jenkinsfile` should implement these typical stages (read and adapt to the actual file in the repo):

1. **Checkout** — pull source from Git.
2. **Build** — `mvn clean package` to compile and package the app.
3. **Test** — run unit tests with Maven (`mvn test`).
4. **Package** — ensure `target/*.jar` or artifact is created.
5. **Docker Build** — build image using `docker build` (or `docker.build` in the pipeline).
6. **Push** — push image to registry (Docker Hub, ECR, GCR) if credentials are configured.
7. **Deploy (optional)** — run container on a host, or trigger a deployment job.

Make sure your Jenkins agent has Docker client access and the necessary environment variables or credentials set. Example Jenkins pipeline snippet:

```groovy
pipeline {
  agent any
  environment {
    IMAGE_NAME = 'mygroup/myapp'
    IMAGE_TAG = "${env.BUILD_NUMBER}"
  }
  stages {
    stage('Checkout') { steps { checkout scm } }
    stage('Build') { steps { sh 'mvn clean package -DskipTests' } }
    stage('Test')  { steps { sh 'mvn test' } }
    stage('Docker Build') { steps { sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} -f dockerfile ." } }
    stage('Push') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
          sh 'docker push ${IMAGE_NAME}:${IMAGE_TAG}'
        }
      }
    }
  }
}
```

> Tip: If your Jenkins runs in a container, either mount the Docker socket or use a dedicated Docker agent.

---

## Dockerfile notes

- Confirm the `FROM` base image and Java version used in the `dockerfile` (adjust to match `pom.xml`).
- Prefer multi-stage builds to keep images small (build with Maven in a build stage, produce a runtime image with only the JAR and JRE).

Example multi-stage pattern (recommended):

```dockerfile
# build stage
FROM maven:3.8.8-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

# runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
```

---

## Environment variables / credentials

- `docker-creds` — Jenkins credentialsId for Docker registry (username/password).
- `DOCKER_REGISTRY` (optional) — registry host (e.g., `docker.io`, `ghcr.io`).
- `MAVEN_SETTINGS` (optional) — if you need private Maven repos.

Set these up in Jenkins (Credentials > System) and reference them in the pipeline.

---

## Testing & Quality

- Add unit tests under `src/test/java` and run with `mvn test`.
- Consider adding static analysis (SpotBugs, PMD, Checkstyle) as pipeline stages.
- Add a stage to publish test results and code coverage reports (e.g., JaCoCo) to Jenkins.

---

## Common enhancements

- Use a `Jenkinsfile` library or `script.groovy` to factor common steps (seems this repo already contains `script.groovy`).
- Add GitHub Actions or CircleCI configs as alternatives to Jenkins.
- Add a `Makefile` or wrapper scripts for local convenience.
- Add a `LICENSE` file and contribution guidelines (CONTRIBUTING.md).

---

## Contributing

1. Fork the repo.
2. Make changes on a feature branch.
3. Open a pull request describing your changes.

If you want, I can also draft a shorter Arabic README variant or a `CONTRIBUTING.md` and `DockerHub`/Jenkins credentials guidance.

---

## Where this doc came from

This README was created by inspecting the repository's public file list and structure. Key references: repository root listing.

---

If you want modifications (Arabic text, shorter version, or a README focused on `Jenkinsfile` internals), tell me which and I will update it.

