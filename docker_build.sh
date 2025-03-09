#./gradlew build
# size=52914

./gradlew bootBuildImage
# size=56783818
# BUILD SUCCESSFUL in 3m 8s

docker build --build-arg JAR_FILE=build/libs/shop_kotlin-0.24.0924.1.jar -t shop_kotlin/app .
docker run -p 8980:8980 -p 8988:8988  docker.io/shop_kotlin/app:latest