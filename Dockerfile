FROM eclipse-temurin:23-noble AS builder

WORKDIR /src

#copy file
COPY mvnw .
COPY pom.xml .

COPY .mvn .mvn
COPY src src

# make mvnw executable 
RUN chmod a+x mvnw && /src/mvnw package -Dmaven.test.skip=true 

FROM eclipse-temurin:23-jre-noble

WORKDIR /app

#where i am copying from, ie builder followed by location to copy from then what i want to call the copied file
COPY --from=builder  /src/target/projectA-0.0.1-SNAPSHOT.jar app.jar


#check if curl command is available
RUN apt update && apt install -y curl

#if i want to set the port=3000
#i can go to application properties and set server.port=3000
#PORT is for railway
ENV PORT=8080
ENV THEMEALDB.API.URL=https://www.themealdb.com/api/json/v1/1

#REDIS ENV VARIABLES
ENV SPRING_DATA_REDIS_HOST=localhost SPRING_DATA_REDIS_PORT=6379
ENV SPRING_DATA_REDIS_USERNAME= SPRING_DATA_REDIS_PASSWORD=
ENV SPRING_DATA_REDIS_DATABASE=0

EXPOSE ${PORT}

HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD curl -f -s http://localhost:${PORT}/health || exit 1

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar