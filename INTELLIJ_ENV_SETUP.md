# Instructions for importing the project into IntelliJ

The following instructions assume that you already have IntelliJ installed, with Java 8 or later.

1. Clone the repo: `git clone https://github.com/googleinterns/wifirtt`.
2. Open IntelliJ, and at the welcome screen, select __Import Project__.
3. In the pop-up window, choose the folder `wifirtt/WifiART` and click __Open__.
4. In the wizard, select __Create project from existing sources__, and proceed through the wizard. The sources directory (__src__) should be automatically detected, and the SDK should be automatically selected as well.
5. Once you have finished going through the wizard, the code will not run because JUnit dependencies still are not included. Go to one of the lines with an error, such as a `@Test` annotation, and click on the red lightbulb that appears. Choose an option such as __Add 'JUnit5.3' to classpath__ to add JUnit.
6. At this point, everything will appear fine, but IntelliJ will typically set JUnit's scope to __Test__, so the project will not compile. Go to __File__ >> __Project Structure__ >> __Modules__, and make sure that the __Scope__ to the right of "JUnit5.3" is set to __Compile__.
7. After completing the above steps, you should be able to right-click on the ArtMvc class in the project structure pane on the left, and select __Run 'ArtMvc.main()'__ to run the program.
