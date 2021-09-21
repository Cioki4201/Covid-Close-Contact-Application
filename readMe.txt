Name: Paul Ciocan
Student No.: R00186442

I use IntelliJ and it no longer supports creating JavaFX Executable Jar files so you must use this command, apologies for the inconvenience

>Open CMD/Powershell in JAR directory
>Use this command: java --module-path *C:\JavaFX\lib\path\here* --add-modules javafx.controls,javafx.fxml -jar CovidProjectExecutable.jar
>Remove the "*" from the command and add the JavaFX Lib path there