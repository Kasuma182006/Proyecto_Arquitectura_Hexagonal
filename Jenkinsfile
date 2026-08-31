pipeline {
    agent any

    stages {

        stage('Create image') {
            steps {
                echo 'Construyendo las imágenes de Docker en el servidor corporativo...'
                sh 'docker compose -f demo/docker-compose.yml build'
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Corriendo las pruebas unitarias estándar con Maven dentro del contenedor...'
                sh 'docker compose -f demo/docker-compose.yml run --rm app-test mvn clean test'
            }
        }

        stage('Deploy / Spin Up Services') {
            steps {
                echo 'Pruebas exitosas. Desplegando y levantando la aplicación...'
                sh 'docker compose -f demo/docker-compose.yml down -v'
                sh 'docker compose -f demo/docker-compose.yml up -d'
            }
        }
    }

    post {
        failure {
            echo 'Fallo detectado en alguna etapa del Pipeline. Limpiando contenedores...'
            sh 'docker compose -f demo/docker-compose.yml down'
        }
        success {
            echo 'Pipeline exitoso. La aplicación Spring Boot ha sido verificada y desplegada.'
        }
    }
}
