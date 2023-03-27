### Prerequisites:
* java 17 installed
* maven installed

### How to start application?
* execute `mvn package` to create/re-build application jar file
* execute `java -jar target/visma-app-1.0.jar`

### Data file
User have to enter data file name, if it was not entered then application will read from default 'data.csv' file.

Application will check root of project for a file, if not found then from root package.
If application started from jar, when it will check root jar's directory if not found then inside jar's root.


