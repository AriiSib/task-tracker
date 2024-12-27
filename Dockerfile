FROM tomcat:10.1.24-jdk21

WORKDIR /usr/local/tomcat

COPY target/task-tracker.war webapps/task-tracker.war

CMD ["catalina.sh", "run"]