pipeline {
    agent any

    stages {
        stage('1. Descargar Código') {
            steps {
                echo 'Descargando la última versión del código desde GitLab...'
                checkout scm
            }
        }

        stage('2. Construir Imagen') {
            steps {
                echo 'Construyendo las imágenes de Docker en el servidor corporativo...'
                sh 'docker-compose build'
            }
        }

        stage('3. Ejecutar Pruebas Unitarias') {
            steps {
                echo 'Corriendo las pruebas unitarias estándar con Maven dentro del contenedor...'
                sh 'docker-compose run --rm app-test mvn clean test'
            }
        }

        stage('4. Desplegar / Levantar Servicios') {
            steps {
                echo 'Pruebas exitosas. Desplegando y levantando la aplicación...'
                sh 'docker-compose down -v'
                sh 'docker-compose up -d'
            }
        }
    }

    post {
        failure {
            echo 'Fallo detectado en alguna etapa del Pipeline. Limpiando contenedores...'
            sh 'docker-compose down'
        }
        success {
            echo '¡Pipeline exitoso! La aplicación Spring Boot ha sido verificada y desplegada.'
        }
    }
}
