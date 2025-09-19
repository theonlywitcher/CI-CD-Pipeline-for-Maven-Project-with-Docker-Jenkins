# CI/CD Pipeline for Maven Project with Docker & Jenkins

> **Portfolio project — demonstration purposes only, not intended for production use.**

---

## Overview

This repository is a **showcase project** that demonstrates how to build a simple CI/CD pipeline for a Maven-based Java application using **Jenkins** and **Docker**.

The goal is to highlight skills in:

* Designing Jenkins pipelines (`Jenkinsfile`).
* Automating builds and tests with **Maven**.
* Containerizing applications with **Docker**.
* Integrating CI/CD stages (build → test → package → image → push).

This is not a production-ready setup. It’s a portfolio piece to illustrate DevOps practices.

---

## Pipeline Stages (High-Level)

1. **Checkout** — pull source code from GitHub.
2. **Build** — compile and package the app with Maven.
3. **Test** — run unit tests.
4. **Docker Build** — create a Docker image from the artifact.
5. **Push** — upload the image to a container registry (optional demo).

---

## Technologies Used

* **Java & Maven** — for building and packaging the app.
* **Jenkins** — for pipeline orchestration.
* **Docker** — for containerization.
* **Groovy** — for pipeline scripting.

---

## Why this project matters

This repo is meant to demonstrate:

* Knowledge of modern CI/CD flows.
* Ability to integrate Jenkins with Docker and Maven.
* Clear understanding of automation and DevOps principles.

---

## Note

This project is **for demonstration and portfolio purposes only**. It is not configured for real-world deployment, and instructions for running are intentionally kept minimal.

If you’re a recruiter, hiring manager, or peer reviewing this repo: the focus is on **skills demonstration**, not production readiness.
