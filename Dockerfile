FROM tomcat:8.0.39-jre8
RUN ls /usr/local/tomcat/conf/
RUN cat /usr/local/tomcat/conf/tomcat-users.xml
COPY ./target/sims-login-service-1.0.0.war /usr/local/tomcat/webapps/sims-login-service.war
VOLUME /opt/tomcat/
ENV CATALINA_OPTS="-Xms64m -Xmx512m -XX:+UseTLAB -XX:+ResizeTLAB -XX:ReservedCodeCacheSize=64m  -XX:+UseCodeCacheFlushing"
