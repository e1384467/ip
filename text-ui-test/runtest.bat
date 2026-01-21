@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin Jerry < input.txt > ACTUAL.TXT
REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
if exist ACTUAL.TXT del ACTUAL.TXT

java -classpath ..\bin Jerry < input-invalid-commands.txt > ACTUAL.TXT
FC ACTUAL.TXT EXPECTED-invalid-commands.TXT
if exist ACTUAL.TXT del ACTUAL.TXT

java -classpath ..\bin Jerry < input-mark-type-commands.txt > ACTUAL.TXT
FC ACTUAL.TXT EXPECTED-mark-type-commands.TXT
if exist ACTUAL.TXT del ACTUAL.TXT

java -classpath ..\bin Jerry < input-add-task-type-commands.txt > ACTUAL.TXT
FC ACTUAL.TXT EXPECTED-add-task-type-commands.TXT
if exist ACTUAL.TXT del ACTUAL.TXT
