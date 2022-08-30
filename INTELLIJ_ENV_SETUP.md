# Instructions for importing the project into IntelliJ

1. Install IntelliJ: https://www.jetbrains.com/idea/download/
2. Clone the project into IntelliJ:
    * Open IntelliJ, and from the welcome screen, click __Get from VCS__ (this should be to the right of __New Project__ and __Open__) to bring up the __Get from Version Control__ window
        * If you already have an IntelliJ project open, you can access this window by navigating to __Git > Clone__ (if you are already working on another Git project) or __VCS > Get from Version Control__
    * Enter the project URL (https://github.com/googleinterns/wifirtt.git).
    * Click __Clone__ to clone the repository.
3. In the __File > Project Structure__ window:
    * Under the __Project__ tab, ensure that the Java JDK is selected. In the __Modules__ tab, also ensure that the SDK is selected under __Dependencies__.
    * In the __Modules__ tab, under __Sources__, expand the directory structure shown and mark the `WifiART/src/test` directory as __Tests__.
    * In the __Libraries__ tab, add these libraries from Maven:
        * `org.jetbrains.annotations`
        * `org.junit.jupiter`
    * Make sure to click __Apply__ to apply the changes.
4. In the __File > Settings__ window:
    * Navigate to the Java Compiler tab: __Build, Execution, Deployment > Compiler > Java Compiler__
    * Ensure that the __Project bytecode version__ matches the Java JDK chosen. E.g. choose __`11`__ if you are using Java 11 as the SDK.
    * Click __Apply__ to apply the changes.
5. Run Tests: 
    * In the project directory panel on the left, right-click on the `test` directory (it should have a green folder icon), and select __Run 'All Tests'__.
    * Verify that all tests pass.
6. Run Code: 
    * Run the `main` method in `ArtMvc.java`
    * Use the example below to validate the installation.
    * Check that the generated buffer matches the output shown.

__INPUT:__
![LCI input screenshot](./LCI_input.png?raw=true)

__OUTPUT:__
![LCI output screenshot](./LCI_output.png?raw=true)
