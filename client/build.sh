#!/bin/bash
echo "Building client..."
mkdir -p bin
javac -cp "lib/*:src" -d bin src/Client.java
if [ $? -eq 0 ]; then
    echo "Build sukses!"
    echo "Running client..."
    java -cp "lib/*:bin" Client
else
    echo "Build gagal!"
fi