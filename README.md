# java-chat-app
A simple WebSocket client-server application written in Java featuring packet handling, command interaction, and MySQL integration.

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
          "username":"create_account",
          "pass":"galvin"
         }
        ```
3. Server

  
