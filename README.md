# java-chat-app
A simple WebSocket client-server application (Command line interact) written in Java featuring packet handling, command interaction, and MySQL integration.

# Requirements & library
- openjdk 26.0.1 ++
- gson (json parser)
- mysql-connector (mysql)
- Java-WebSocket
- slf4j-api (get along with java web socket)


# Packet struct explanation
1. Client
   - Broadcast message to all current online
     ```json
       {
        "type":"chat",
        "message":"hello",
        "destination":"all"
       }
     ```
    - Broadcast message to Specific user (not implemented yet cuz im lazy lol)
      
      ```json
       {
        "type":"chat",
        "message":"hello",
        "destination":"user1"
       }
      ```
      
     - Create Account
        ```json
         {
           "type":"create_account",
           "username":"galvin",
           "pass":"galvin123"
          }
        ```
     
     - Login
        ```json
        {
          "type":"login",
          "username":"galvin",
          "pass":"galvin123"
         }
        ```
3. Server


# MySQL Setup

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    
    username VARCHAR(50) NOT NULL UNIQUE,
    
    password VARCHAR(255) NOT NULL,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    total_message INT DEFAULT 0,

    is_online BOOLEAN DEFAULT FALSE,

    last_login TIMESTAMP NULL,

    role ENUM('user', 'admin') DEFAULT 'user'
);
```

# How to build

since all the libraries already put along with the source code just
```cli
   bash build.sh
```
make sure to use newest java version or if you having problem with ur builder u can try the windows version (the .bat one) 

```bat
   @echo off
   
   echo Building...
   
   if not exist bin mkdir bin
   
   javac -cp "lib/*;src" -d bin ^
   src/Server.java ^
   src/user_handler/User.java ^
   src/packet_handler/Message.java ^
   src/utilities_handler/MysqlUtility.java
   
   if %errorlevel% == 0 (
       echo Build sukses!
       echo Running...
   
       java -cp "lib/*;bin" Server
   ) else (
       echo Build gagal!
   )
   
   pause
```
