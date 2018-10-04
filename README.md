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

### Client Connection
- Use netcat to connect to Server
```
nc localhost 8080
```
### Client Request
1. INDEX|package_name|dependencies
    - denpencies can be empty, separate multiple dependencies by comma ","
    - ```INDEX|aa|bb,cc```
1. QUERY|package_name|
    - ```QUERY|aa|```
1. REMOVE|package_name|
    - ```REMOVE|aa|```
