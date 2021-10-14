pipeline {
    agent any 
    tools {
        maven 'Maven 3.8.1'
        jdk 'jdk8'
    }
    stages {
        stage ('Build') {
            steps {    
                sh ' mvn clean install -DskipTests'
            }
        }
        /*stage ('Test') {
            steps {    
                sh ' mvn test'
            }
        }*/       
        stage ('Imagem docker') {
            steps {
                sh 'docker build . -t vonex/sms_fornecedor:${BUILD_NUMBER}'
            }
        }
        stage ('Run docker') {
            steps {
                sh ' docker stop sms-fornecedor' 
                sh ' docker rm sms-fornecedor'
                sh ' docker container run --network intranet -h sms-fornecedor -d --name sms-fornecedor -p 8084:8084 vonex/sms_fornecedor:${BUILD_NUMBER}'
            }
        }        
    }
}


