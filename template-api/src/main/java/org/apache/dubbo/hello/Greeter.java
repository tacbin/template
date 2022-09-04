    package org.apache.dubbo.hello;

import org.apache.dubbo.common.stream.StreamObserver;
import com.google.protobuf.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.concurrent.CompletableFuture;

public interface Greeter {

    String JAVA_SERVICE_NAME = "org.apache.dubbo.hello.Greeter";
    String SERVICE_NAME = "helloworld.Greeter";

    org.apache.dubbo.hello.HelloReply greet(org.apache.dubbo.hello.HelloRequest request);

    default CompletableFuture<org.apache.dubbo.hello.HelloReply> greetAsync(org.apache.dubbo.hello.HelloRequest request){
        return CompletableFuture.completedFuture(greet(request));
    }

    /**
    * This server stream type unary method is <b>only</b> used for generated stub to support async unary method.
    * It will not be called if you are NOT using Dubbo3 generated triple stub and <b>DO NOT</b> implement this method.
    */
    default void greet(org.apache.dubbo.hello.HelloRequest request, StreamObserver<org.apache.dubbo.hello.HelloReply> responseObserver){
        greetAsync(request).whenComplete((r, t) -> {
            if (t != null) {
                responseObserver.onError(t);
            } else {
                responseObserver.onNext(r);
                responseObserver.onCompleted();
            }
        });
    }






}
