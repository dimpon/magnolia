@echo off

start java -Xms128m -Xmx256m -jar ./magnolia-eureka/target/magnolia-eureka.jar
start java -Xms128m -Xmx256m -jar ./magnolia-config/target/magnolia-config.jar

start java -jar ./magnolia-auth/target/magnolia-auth.jar

rem start java -jar ./magnolia-turbine/target/magnolia-turbine.jar


rem start java  -jar ./magnolia-service/target/magnolia-service.jar