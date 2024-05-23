@echo off
rem Compile all Java files
javac *.java

rem Start BankServer in the current window
start /b cmd /c "java BankServer"

rem Start BankClient in a new terminal window
start cmd /c "java BankClient"
