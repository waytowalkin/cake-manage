# Build a JAR File
FROM maven:3.6.3-jdk-8-slim AS stage1
WORKDIR /home/app
COPY . /home/app/
RUN mvn -f /home/app/pom.xml clean package

# Create an Image
FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY --from=stage1 /home/app/target/cake-manage-0.0.1-SNAPSHOT.jar cake-manage-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["sh", "-c", "java -jar /cake-manage-0.0.1-SNAPSHOT.jar"]
