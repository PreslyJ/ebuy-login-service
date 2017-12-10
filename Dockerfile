FROM java:8 
VOLUME /tmp
ADD ./target/ebuy-login-service-1.0.1.jar  ebuy-login-service.jar
RUN sh -c 'touch /ebuy-login-service.jar'
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java  -jar   /ebuy-login-service.jar" ]
