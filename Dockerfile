FROM java:8 
VOLUME /tmp
ADD ./target/sims-login-service-0.0.1-SNAPSHOT.jar  sims-login-service.jar
RUN sh -c 'touch /sims-subscriber-api.jar'
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -Xms512m -Xmx1024m -XX:+UseTLAB -XX:+ResizeTLAB -XX:ReservedCodeCacheSize=128m  -XX:+UseCodeCacheFlushing  -jar  -Dserver.contextPath=/sims-login-service /sims-login-service.jar" ]
