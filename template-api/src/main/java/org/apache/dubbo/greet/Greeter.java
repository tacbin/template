    package org.apache.dubbo.greet;

import org.apache.dubbo.common.stream.StreamObserver;
import com.google.protobuf.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.concurrent.CompletableFuture;

public interface Greeter {

    String JAVA_SERVICE_NAME = "org.apache.dubbo.greet.Greeter";
    String SERVICE_NAME = "api.Greeter";

        /**
         * <pre>
         *  Sends a greeting
         * </pre>
         */
    org.apache.dubbo.greet.User sayHello(org.apache.dubbo.greet.HelloRequest request);

    default CompletableFuture<org.apache.dubbo.greet.User> sayHelloAsync(org.apache.dubbo.greet.HelloRequest request){
        return CompletableFuture.completedFuture(sayHello(request));
    }

    /**
    * This server stream type unary method is <b>only</b> used for generated stub to support async unary method.
    * It will not be called if you are NOT using Dubbo3 generated triple stub and <b>DO NOT</b> implement this method.
    */
    default void sayHello(org.apache.dubbo.greet.HelloRequest request, StreamObserver<org.apache.dubbo.greet.User> responseObserver){
        sayHelloAsync(request).whenComplete((r, t) -> {
            if (t != null) {
                responseObserver.onError(t);
            } else {
                responseObserver.onNext(r);
                responseObserver.onCompleted();
            }
        });
    }



        /**
         * <pre>
         *  Sends a greeting via stream
         * </pre>
         */
    StreamObserver<org.apache.dubbo.greet.HelloRequest> sayHelloStream(StreamObserver<org.apache.dubbo.greet.User> responseObserver);



}
