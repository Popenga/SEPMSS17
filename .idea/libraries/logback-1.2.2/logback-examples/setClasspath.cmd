
@echo off
REM This script will add logback jars to your classpath.

set LB_HOME=C:\Users\krirs\IdeaProjects\SEPMSS17\.idea\libraries\logback-1.2.2
REM echo %LB_HOME%

set CLASSPATH=%CLASSPATH%;%LB_HOME%/logback-classic-1.2.2.jar
set CLASSPATH=%CLASSPATH%;%LB_HOME%/logback-core-1.2.2.jar
set CLASSPATH=%CLASSPATH%;%LB_HOME%/logback-examples/logback-examples-1.2.2.jar
set CLASSPATH=%CLASSPATH%;%LB_HOME%/logback-examples/lib/slf4j-api-1.7.25.jar

REM echo %CLASSPATH%
