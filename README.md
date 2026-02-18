# Big Company Salaries Analysis
## Overview 
This project analyzes the organizational structure and salary distribution of a company to identify potential improvements and policy violations. 
Specifically, it checks two key rules: 
1. **Manager salary compliance:**
   * Every manager must earn **at least 20% more** than the average salary of their direct subordinates.
   * A manager must **not exceed 50% more** than the average salary of their direct subordinates.
2. **Reporting line length:**
   * Employees should not have **more than 4 managers between them and the CEO** to avoid overly long reporting chains. The application reads employee data from a CSV file, performs analysis, and prints violations to the console.

## Technology Stack 
* **Java 11** – chosen for enterprise environments for stability and compatibility with older corporate JDK setups.
* **Maven** – project structure, dependency management, and build automation.
* **JUnit 5** – unit testing to validate salary and reporting line rules.

## Project Structure
* big-company
  * src/main/java
    * common
      * Constants.java
      * Util.java
    * model
      * Employee.java
      * ManagerLimit.java
      * SalaryViolation.java
    * service
      * CompanyAnalyzer.java
      * EmployeeReader.java
    * App.java
  * src/main/resources
    * employees.csv
  * src/test/java
    * BigCompanySalariesTest.java
  * pom.xml

### Key classes 
* **Employee:** Represents an employee with ID, name, salary, and manager reference.
* **ManagerLimit:** Represents a violation of reporting line length.
* **SalaryViolation:** Represents a violation of salary policy.
* **CompanyAnalyzer:** Core analyzer class with logic for salary checks and reporting line checks.
* **EmployeeReader:** Process all the data from employess from csv fields to objects and lists.
* **App:** Reads the CSV and prints analysis results to the console.

## Special Considerations 
### CEO Case 
* The **CEO is skipped** for salary violations because they have no manager.
* In reporting line checks, all employees including the CEO are considered for counting managers between subordinates and the CEO.

### Enterprise Java 11 Focus 
* Java 11 is widely adopted in corporate environments and ensures compatibility with older systems.
* ``var`` keyword is used sparingly to improve readability while keeping types explicit when clarity is important.
* Maven handles dependency management and standard project layout, making the project easy to integrate with CI/CD pipelines.

## Usage 
1. **Build the project:**
``
mvn clean compile
``
2. **Run the analysis:**
``
java -cp target/classes App src/main/resources/employees.csv
``
3. **Run tests:**
``
mvn test
``
4. **Sample output:**
````
INFO - Salary Violations
INFO - TOO LOW
    ID: 2 - Maria One earns 40000.00, average subordinate salary = 45000.00, should be minimum 54000.00, missing 14000.00
INFO - TOO HIGH
INFO - Number of Managers between
    ID: 7 - Worker Y has 6 managers between them and CEO by 2 levels
````
## Future Work 
* **GUI / Web Interface:** Display violations in a dashboard format.
* **Configurable thresholds:** Allow 20%-50% salary rule and max manager depth to be configured via properties file.
* **CSV import/export enhancements:** Handle multiple formats, data validation, missing values.
* **Performance optimization:** Currently works for ≤1000 employees; could extend to tens of thousands with caching or tree-based traversal.
* **Unit tests for edge cases:** Loops in manager relationships, missing managers, zero subordinates.
