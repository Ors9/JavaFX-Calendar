@echo off
set JAVAFX_LIB="C:\Program Files\Java\javafx-sdk-23.0.2\lib"

javac --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml *.java
java --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml MyCalendar
pause
