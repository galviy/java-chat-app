
#!/bin/bash
echo "Building..."
mkdir -p bin
javac -cp "lib/*:src" -d bin src/Server.java src/user_handler/User.java src/packet_handler/Message.java src/utilities_handler/MysqlUtility.java
if [ $? -eq 0 ]; then
    echo "Build sukses!"
    echo "Running..."
    java -cp "lib/*:bin" Server
else
    echo "Build gagal!"
fi
