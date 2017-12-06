# Integration Tester Maven Plugin
This plugin enables you to execute Integration Tester assests as part of your Maven build.
### Samples
To run these samples you need to:
1. Install Maven 3.2+
2. Install either Integration Tester v9.1.1.1+ or Integration Tester Agent v9.1.1.1+
3. Install RTCP 9.1.1.1+ and start the server
4. Fetch these resources:
* __pom-stub-junit-test.xml__ (sample pom to run a stub while running a junit)
* __pom-test.xml__ (sample pom to run a test)
* __pom-stub-test.xml__ (sample pom to run a stub while running a test)
* __src/__ (sample junit integration test used to hit a running stub)
* __Sample/__  (sample project built from [QueryPhoneBook](https://developer.ibm.com/testing/docs/starter-editions/ibm-rational-integration-tester-starter-edition/rit-se-article-1-creating-and-running-a-test/))
4. Put them in a directory.
5. Open a command window in the same directory.
6. To enable Maven to execute the resources on the machine, set an environment variable to the install directory of either Integration Tester or Integration Tester Agent:
```sh
set INTEGRATION_TESTER_HOME=<integration tester installation path>
or
set INTEGRATION_TESTER_AGENT_HOME=<integration tester agent installation path>
```
7. To enable Maven to download Maven plugins from RTCP we must add the certificate used by RTCP to the JDKs trust store. To get the certifcate used by RTCP browse to RTCP (eg. https://localhost:5443). If a security warning is shown add an exception to ignore it and click on the HTTPS certificate chain next to the web address. If you are using FireFox you can then click "More Information" > "Security" > "View Certificate" > "Details" > "Export.." and then click save in the "File Save" dialog to save the certificate file (eg. integration_tester.crt) in your machine. If you are using another browser, please check the browser steps to export the certificate to file.
8. Find where the JDK, that Maven uses, is installed. We will assume the JDK is installed at __%JAVA_HOME%__
9. Next import the certificate file (eg. integration_tester.crt) into 'cacerts' file. This command needs in to run by an Administrator in a Command Prompt.
```sh
%JAVA_HOME%\jre\bin\keytool -import -alias integration_tester -keystore %JAVA_HOME%\jre\lib\security\cacert -file integration_tester.crt
```
10. You will be asked for password and the default value is: __changeit__
11. If the __alias__ '_integration_tester_' already exists in the keystore, enter the below command to remove the old alias and repeat the above two steps (9) & (10), then continue onto next step (12).
```sh
%JAVA_HOME%\jre\bin\keytool -delete -alias integration_tester -keystore %JAVA_HOME%\jre\lib\security\cacert
```
12. Run Maven to update the pom file(s) to use the plugin version currently available on RTCP.
```sh
mvn versions:update-properties -Dincludes=com.hcl.products.test.it -f <POM_FILE>
```
_eg._
```sh
mvn versions:update-properties -Dincludes=com.hcl.products.test.it -f pom-test.xml
```
13. You can then run Maven for the pom files.
```sh
mvn verify -f <the-pom-file.xml> -X
```
_eg._
```sh
mvn verify -f pom-test.xml -X
```
14. If the pom file runs both tests and stubs you can improve performance (by using a single instance of RunTests for both stubs and tests in the same project) setting the following environment variable. (This assumes the plugin version and that it has been pulled to the local filesystem from RTCP as a result of running Maven previously.)
```sh
set MAVEN_OPTS=-Xbootclasspath/a:%HOMEDRIVE%%HOMEPATH%\.m2\repository\com\hcl\products\test\it\it-comms\1.911.1\it-comms-1.911.1.jar
```
