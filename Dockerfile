#
# Build stage
#
FROM maven AS build
COPY src /home/app/src
COPY pom.xml /home/app/
RUN mvn -f /home/app clean package

#
# Package stage
#
FROM openjdk
COPY --from=build /home/app/target/gametracker-0.0.1-SNAPSHOT.jar /usr/local/lib/gametracker.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/gametracker.jar"]