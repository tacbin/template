echo %cd%\src\main\java

echo %cd%\src\main\proto

docker run -dt --rm  -v %cd%\src\main:/target/generated-sources/protobuf  -v %cd%\src\main\proto:/src/main/proto tacbin123/java-protoc-gen:1.2

echo %cd%\src\main\go

echo %cd%\src\main\proto

docker run -dt --rm  -v %cd%\src\main\go:/go/result  -v %cd%\src\main\proto:/go/pb tacbin123/go1.17:1.2