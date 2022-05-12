pipeline {

    agent {
        label 'linux || master'
    }
    tools {
        jdk 'jdk-ac-8'
    }
    environment {
        GIT_SHORT_COMMIT = sh(script: 'git rev-parse --short $GIT_COMMIT',returnStdout: true).trim()
    }

    options {
        buildDiscarder(logRotator(
                daysToKeepStr: '3',
                numToKeepStr: '5',
                artifactDaysToKeepStr: '3',
                artifactNumToKeepStr: '5'
        ))
    }

    stages {
        stage('Git Clone') {
            steps {
                checkout scm
                echo "Branch name: ${BRANCH_NAME}"
            }
        }

        stage('Maven package without test') {
            steps {
                withMaven(mavenSettingsConfig: '5b5852ea-1ff9-41fd-b754-b3c17bdbbe20') {
                    sh './mvnw -Dmaven.test.failure.ignore=true -U clean package -Dmaven.test.skip=true'
                }
            }
        }

        stage('Run tests with coverage, record results') {
            steps {
                withMaven(mavenSettingsConfig: '5b5852ea-1ff9-41fd-b754-b3c17bdbbe20') {

                    sh './mvnw -Dmaven.test.failure.ignore=true jacoco:prepare-agent test'

                    sh './mvnw -Dmaven.test.failure.ignore=true jacoco:report'
                }
                jacoco()

                junit allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml'
            }
        }
        stage('Validate & Publish') {
            parallel {
                stage('SonarQube Analysis') {
                    tools {
                        jdk "jdk-ac-11"
                    }
                    steps {
                        echo "Running SonarQube Analysis... from ${env.BRANCH_NAME}"
                        withSonarQubeEnv('sonar') {
                            withMaven(mavenSettingsConfig: '5b5852ea-1ff9-41fd-b754-b3c17bdbbe20') {
                                sh '''
                                    ./config/ci/scan/sonar_scanner.sh
                                '''
                            }
                        }
                    }
                    post {
                        success {
                            echo 'Succeeded.'
                        }
                        failure {
                            echo 'Failed.'
                        }
                    }
                }
                stage('Build and Publish Docker') {
                    when {
                        expression {
                            return env.BRANCH_NAME == 'develop';
                        }
                    }
                    stages{
                        stage('Build Docker') {
                            steps {
                                echo "Building Docker... from ${env.BRANCH_NAME}"
                                sh '''
                                ./config/ci/build/build_docker.sh
                                '''
                            }
                            post {
                                success {
                                    echo 'Success.'
                                }
                                failure {
                                    echo 'Failed.'
                                }
                            }
                        }
                        stage('Publish Docker') {
                            steps {
                                echo "Publishing to ECR... from ${env.BRANCH_NAME}"
                                sh '''
                                ./config/ci/publish/publish_dev_ecr.sh
                                '''
                            }
                            post {
                                success {
                                    echo 'Success.'
                                }
                                failure {
                                    echo 'Failed.'
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            echo 'Finished. Cleaning up.'
            deleteDir()
        }
    }
}
