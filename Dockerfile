#Version 1.0
#ARG baseImageName
#ARG baseImageVersion
#FROM ${baseImageName}:${baseImageVersion}
#FROM cxms-docker.pkg.coding.net/cxms/wh/springbootimage:1.0
FROM java:8


#start
#USER appdeploy
#COPY app/*   /app/deploy/app.zip
#RUN unzip -o /app/deploy/app.zip -d /app/deploy/ && \
#    rm -rf /app/deploy/app.zip
#end
#USER appdeploy

WORKDIR /app/deploy/

ADD ./target/*.zip /app/deploy/
RUN unzip *.zip -d .
RUN rm -rf *.zip
RUN chmod 777 ./bin/start.sh
RUN chmod +x ./bin/stop.sh

#ENV JAVA_HOME=/opt/jdk1.8.0_25/
#ENV PATH=$PATH:$JAVA_HOME/bin
#ENV CLASSPATH=.:$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar
ENV LANG=en_US.UTF-8

EXPOSE 8080

CMD sh /app/deploy/bin/start.sh
