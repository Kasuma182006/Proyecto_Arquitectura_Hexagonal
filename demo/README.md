# Proyecto Spring Boot - Despliegue con Docker

Este proyecto está preparado para ejecutarse de forma totalmente contenedorizada mediante **Docker** y **Docker Compose**.

## 🚀 Requisitos Previos
[Rancher Desktop](https://rancherdesktop.io/) instalado.

## 🛠️ Instrucciones de Uso

### 1. Levantar el entorno (App + PostgreSQL)

Ejecuta el siguiente comando en la raíz del proyecto para construir la imagen y levantar ambos contenedores:

```bash
docker-compose up --build -d
