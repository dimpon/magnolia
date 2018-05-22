@echo off


start java -Xms128m -Xmx256m -jar -Dspring.cloud.bootstrap.name=bootstrap-8807 ./magnolia-service/target/magnolia-service.jar > out
rem start java -Xms128m -Xmx256m -jar -Dspring.cloud.bootstrap.name=bootstrap-8808 ./magnolia-service/target/magnolia-service.jar
start java -Xms128m -Xmx256m -jar ./magnolia-client/target/magnolia-client.jar
start java -Xms128m -Xmx256m -jar ./magnolia-web/target/magnolia-web.jar
