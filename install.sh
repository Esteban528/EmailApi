#!/usr/bin/env bash

DIR_NAME="EmailApiSpring"
DIR_PATH="$HOME/$DIR_NAME"
JAR_URL="https://github.com/Esteban528/EmailApi/releases/download/oficial/emailapi-0.0.1-SNAPSHOT.jar"
COMPOSE_URL="https://raw.githubusercontent.com/Esteban528/EmailApi/refs/heads/main/docker-compose.yaml"
ENV_TEMPLATE="https://raw.githubusercontent.com/Esteban528/EmailApi/refs/heads/main/.env-template"

mkdir -p "$DIR_PATH/target"

echo "Downloading the JAR file..."
curl -L -o "$DIR_PATH/target/emailapi-0.0.1-SNAPSHOT.jar" "$JAR_URL"
echo "JAR file downloaded."

echo "Downloading the .env file..."
curl -o "$DIR_PATH/.env" "$ENV_TEMPLATE" || { echo "Error downloading the .env file"; exit 1; }
echo ".env file downloaded."

echo "Downloading the docker-compose.yaml file..."
curl -o "$DIR_PATH/docker-compose.yaml" "$COMPOSE_URL" || { echo "Error downloading the docker-compose.yaml file"; exit 1; }
echo "docker-compose.yaml file downloaded."

if ! command -v docker &> /dev/null; then
    echo "Docker is not installed. Installing Docker..."
    echo "Docker not found. Aborting."
    exit 1
else
    echo "Docker is installed."
fi

if ! command -v docker compose &> /dev/null; then
    echo "Docker Compose is not installed. Installing Docker Compose..."
    echo "Docker Compose not found. Aborting."
    exit 1
else
    echo "Docker Compose is installed."
fi

echo "Installation completed successfully."
echo "Please configure .env file"

cd "$DIR_PATH"
