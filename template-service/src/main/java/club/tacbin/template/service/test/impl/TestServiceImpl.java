package club.tacbin.template.service.test.impl;

import club.tacbin.template.service.test.ITestService;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.greet.Greeter;
import org.apache.dubbo.greet.HelloRequest;
import org.apache.dubbo.greet.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Administrator
 */
@DubboService(interfaceName = Greeter.JAVA_SERVICE_NAME)
public class TestServiceImpl implements ITestService {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Override
    public User sayHello(HelloRequest request) {
        logger.info("info信息");
        logger.warn("warn信息");
        logger.error("error信息");
        return User.newBuilder().setAge(1).setName("tom").build();
    }

    @Override
    public StreamObserver<HelloRequest> sayHelloStream(StreamObserver<User> responseObserver) {
        return null;
    }
}
