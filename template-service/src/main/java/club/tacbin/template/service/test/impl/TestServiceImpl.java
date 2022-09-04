package club.tacbin.template.service.test.impl;

import club.tacbin.template.service.test.ITestService;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.greet.Greeter;
import org.apache.dubbo.greet.HelloRequest;
import org.apache.dubbo.greet.User;


import java.util.concurrent.CompletableFuture;

/**
 * @author Administrator
 */
@DubboService(interfaceName = Greeter.JAVA_SERVICE_NAME)
public class TestServiceImpl implements ITestService {


    @Override
    public User sayHello(HelloRequest request) {
        return User.newBuilder().setAge(1).setName("tom").build();
    }

    @Override
    public StreamObserver<HelloRequest> sayHelloStream(StreamObserver<User> responseObserver) {
        return null;
    }
}
