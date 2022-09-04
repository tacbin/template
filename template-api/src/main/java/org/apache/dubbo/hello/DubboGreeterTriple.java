    package org.apache.dubbo.hello;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.PathResolver;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.ServerService;
import org.apache.dubbo.rpc.TriRpcStatus;
import org.apache.dubbo.rpc.model.MethodDescriptor;
import org.apache.dubbo.rpc.model.ServiceDescriptor;
import org.apache.dubbo.rpc.model.StubMethodDescriptor;
import org.apache.dubbo.rpc.model.StubServiceDescriptor;
import org.apache.dubbo.rpc.stub.BiStreamMethodHandler;
import org.apache.dubbo.rpc.stub.ServerStreamMethodHandler;
import org.apache.dubbo.rpc.stub.StubInvocationUtil;
import org.apache.dubbo.rpc.stub.StubInvoker;
import org.apache.dubbo.rpc.stub.StubMethodHandler;
import org.apache.dubbo.rpc.stub.StubSuppliers;
import org.apache.dubbo.rpc.stub.UnaryStubMethodHandler;

import com.google.protobuf.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.concurrent.CompletableFuture;

public final class DubboGreeterTriple {

    public static final String SERVICE_NAME = Greeter.SERVICE_NAME;

    private static final StubServiceDescriptor serviceDescriptor = new StubServiceDescriptor(SERVICE_NAME,Greeter.class);

    static {
        StubSuppliers.addSupplier(SERVICE_NAME, DubboGreeterTriple::newStub);
        StubSuppliers.addSupplier(Greeter.JAVA_SERVICE_NAME,  DubboGreeterTriple::newStub);
        StubSuppliers.addDescriptor(SERVICE_NAME, serviceDescriptor);
        StubSuppliers.addDescriptor(Greeter.JAVA_SERVICE_NAME, serviceDescriptor);
    }

    @SuppressWarnings("all")
    public static Greeter newStub(Invoker<?> invoker) {
        return new GreeterStub((Invoker<Greeter>)invoker);
    }

    private static final StubMethodDescriptor greetMethod = new StubMethodDescriptor("greet",
    org.apache.dubbo.hello.HelloRequest.class, org.apache.dubbo.hello.HelloReply.class, serviceDescriptor, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.hello.HelloRequest::parseFrom,
    org.apache.dubbo.hello.HelloReply::parseFrom);




    public static class GreeterStub implements Greeter{
        private final Invoker<Greeter> invoker;

        public GreeterStub(Invoker<Greeter> invoker) {
            this.invoker = invoker;
        }

        @Override
        public org.apache.dubbo.hello.HelloReply greet(org.apache.dubbo.hello.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, greetMethod, request);
        }

        @Override
        public void greet(org.apache.dubbo.hello.HelloRequest request, StreamObserver<org.apache.dubbo.hello.HelloReply> responseObserver){
            StubInvocationUtil.unaryCall(invoker, greetMethod , request, responseObserver);
        }



    }

    public static abstract class GreeterImplBase implements Greeter, ServerService<Greeter> {

        @Override
        public final Invoker<Greeter> getInvoker(URL url) {
            PathResolver pathResolver = url.getOrDefaultFrameworkModel()
            .getExtensionLoader(PathResolver.class)
            .getDefaultExtension();
            Map<String,StubMethodHandler<?, ?>> handlers = new HashMap<>();

            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/greet" );

            BiConsumer<org.apache.dubbo.hello.HelloRequest, StreamObserver<org.apache.dubbo.hello.HelloReply>> greetFunc = this::greet;
            handlers.put(greetMethod.getMethodName(), new UnaryStubMethodHandler<>(greetFunc));




            return new StubInvoker<>(this, url, Greeter.class, handlers);
        }


        @Override
        public org.apache.dubbo.hello.HelloReply greet(org.apache.dubbo.hello.HelloRequest request){
            throw unimplementedMethodException(greetMethod);
        }





        @Override
        public final ServiceDescriptor getServiceDescriptor() {
            return serviceDescriptor;
        }
        private RpcException unimplementedMethodException(StubMethodDescriptor methodDescriptor) {
            return TriRpcStatus.UNIMPLEMENTED.withDescription(String.format("Method %s is unimplemented",
                "/" + serviceDescriptor.getInterfaceName() + "/" + methodDescriptor.getMethodName())).asException();
        }
    }

}
