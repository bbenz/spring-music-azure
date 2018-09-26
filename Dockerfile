FROM java
VOLUME /tmp
ADD /build/libs/*.jar /app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar", "--server.port=80" ]
EXPOSE 80