# Package Indexer
## Description
## Usage
### Build
- THis package uses `gradle` for build. Build it with
```
gradle build
```
### Docker
- If you have docker installed and running, you can run the server by
```
Docker build .
Docker run -p 8080:8080 <image id>
```
### Run using JAVA
- If you don't have Docker, you can run it directly using JAVA
```
java -jar build/libs/package-indexer-0.0.1-SNAPSHOT.jar
```
