echo "COMPILANDO PROJETO!!"
mvn clean package

echo "CRIANDO IMAGEM!!"
docker build -t springapi .

echo "SUBINDO CONTAINER"
docker run -d -p 8080:8080 springapi


