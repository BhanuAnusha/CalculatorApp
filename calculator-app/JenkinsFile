pipeline {
    agent none

    environment {
        SONARQUBE_URL = 'http://host.docker.internal:9000'
        SONARQUBE_TOKEN = credentials('SonarUser')
        DOCKER_HUB_CREDENTIALS = credentials('docker-hub-creds')
        DOCKER_IMAGE = 'kattabhanuanusha/calculatorjavacode'
        DOCKER_TAG = 'latest'
    }

    stages {
        stage('Clean Workspace') {
            agent any
            steps {
                cleanWs()
                git branch: 'main', url: 'https://github.com/BhanuAnusha/CalculatorApp.git'
            }
        }

        stage('Build (Java 11)') {
            agent {
                docker {
                    image 'maven:3.8.6-eclipse-temurin-11'
                    args '-v $HOME/.m2:/root/.m2'
                    reuseNode true
                }
            }
            steps {
                dir('calculator-app') {
                    sh '''
                        # Clean and build with Java 11
                        mvn clean package \
                        -Dmaven.compiler.source=11 \
                        -Dmaven.compiler.target=11 \
                        -Djava.version=11
                    '''
                }
                stash includes: 'calculator-app/target/**', name: 'compiled-artifacts'
            }
        }

        stage('Unit Test (Java 21)') {
            agent {
                docker {
                    image 'maven:3.9.9-eclipse-temurin-21'
                    args '-v $HOME/.m2:/root/.m2'
                    reuseNode true
                }
            }
            steps {
                unstash 'compiled-artifacts'
                dir('calculator-app') {
                    sh 'mvn test -Dtest="com.example.calculator.CalculatorTest"'
                    
                }
            }
            post {
                always {
                    junit 'calculator-app/**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            agent {
                docker {
                    image 'maven:3.8.6-eclipse-temurin-17'
                    args '-v $HOME/.m2:/root/.m2'
                    reuseNode true
                }
            }
            steps {
                dir('calculator-app') {
                    withSonarQubeEnv('SONARQUBE') {
                        sh """
                            mvn sonar:sonar \
                            -Dsonar.projectKey=MyProject \
                            -Dsonar.host.url=${SONARQUBE_URL} \
                            -Dsonar.login=${SONARQUBE_TOKEN}
                        """
                    }
                }
            }
        }

        stage('Build Docker Image') {
            agent any
            steps {
                script {
                    // Verify files before build
                    sh 'ls -la calculator-app/target/'
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}", 'calculator-app')
                    echo "build docker image done"
                }
            }
        }

        stage('Push to Docker Hub') {
            agent any
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-creds') {
                        docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").push()
                        echo "Successfully pushed ${DOCKER_IMAGE}:${DOCKER_TAG}"
                    }
                }
            }
        }
        stage('Deploy to Kubernetes') {
            agent any
            steps {
                script {
                    // Verify kubectl is available
                    sh 'kubectl version --client'
                    
                    // Apply the deployment manifest
                    sh 'kubectl apply -f /var/jenkins_home/deploymentdh.yaml'
                }
            }
        }
    }
}