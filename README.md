# LinkedIn Automation Framework

This project is an **Automation Framework** developed using **Selenium**, **Java**, and **Maven** for automating **LinkedIn** tasks such as sending connection requests. It leverages **TestNG** for test execution and **Extent Reports** for reporting.

## Key Features:

- **Selenium WebDriver**: Automates the interaction with the LinkedIn web interface for various tasks.
- **Java**: Primary programming language used to develop test cases and framework logic.
- **Maven**: Dependency management tool used for building and managing project dependencies.
- **TestNG**: Test framework used to structure, execute, and manage the tests.
- **Extent Reports**: Provides rich, detailed, and visually appealing reports with test results, logs, and screenshots for failed tests.

### Maven Commands:

1. **`mvn clean install`**:
   - **Clean**: Removes any previous builds in the `target/` folder.
   - **Install**: Downloads the project dependencies and installs them into the local repository. It also packages the project if needed.

2. **`mvn test`**:
   - Runs the test cases based on the configuration specified in `testng.xml` (or any other default configuration).
   - Generates test results and displays them on the console or in generated reports.

3. **`mvn compile`**:
   - This command compiles the Java source files and places them in the `target/classes` directory.

4. **`mvn clean`**:
   - Cleans up the build artifacts by removing files in the `target/` directory.

5. **`mvn site`**:
   - Generates project reports and places them in the `target/site` folder. This includes test reports, project information, dependency reports, and more.
