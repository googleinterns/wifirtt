# Instructions for generating a JAR file in IntelliJ

The following instructions assume that you already have IntelliJ installed, with Java 8 or later, and that you have already completed the steps outlined in `INTELLIJ__ENV__SETUP.md`.

1. Go to __File__ >> __Project Structure__ >> __Artifacts__ and click the __+__ icon. Under the __Add__ menu, select __JAR__ >> __From modules with dependencies...__
2. In the __Create JAR from Modules__ window that pops up, the module should automatically be selected as __WifiART__. Choose __ArtMvc__ as the main class. Select __Include tests__ to add tests to the JAR. Press __OK__ to close the window.
3. Click __Apply__ in the bottom-right corner of the __Project Structure__ window and press __OK__ to close the window.
4. Go to __Build__ >> __Build Artifacts__, and in the menu that appears, select __WifiART:JAR__ >> __Build to generate the JAR file__.
5. The JAR file output will be located at `WifiART/out/artifacts/WifiART__jar/WifiART.jar`.
6. To run the JAR application, go to the command line and run the command `java -jar path/to/.../WifiART.jar`, including the path to the JAR from your current directory. The GUI should pop up as a Java app.
