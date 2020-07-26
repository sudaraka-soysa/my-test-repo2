FROM java8base
ADD spring-boot-starter-parent-*.jar /root
ADD my-test-repo/src/main/resources/application.yml /root
#ADD bootstrap.properties /root
#ADD log4j2.xml /root
RUN cd ../ && \
    cd /root
CMD ["/bin/sh", "-l", "-c", "cd /root && java -jar /root/spring-boot-starter-parent.jar --spring.config.location=/root/application.yml"]
