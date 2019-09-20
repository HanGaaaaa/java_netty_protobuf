package kinghua.protodemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.protobuf.GeneratedMessage;

/**
 * 接收器，负责从绑定端口接收消息，交由协议处理器处理
 * @param <I> 消息类型
 * @param <H> 消息处理器类型
 */
public class Acceptor<I extends GeneratedMessage, H extends AcceptorProtocolHandler<I>> {

	private static final Logger logger = LoggerFactory.getLogger(Acceptor.class);
	private int port;
	private int parentSize;
	private int childrenSize;
	private Class<H> handleClass;
	private Class<I> packetClass;
	private IClientFactory<? extends AbstractClient> clientFactory;
	private PacketTransfer<I> transfer;

	/**
	 * 接收器启动方法，设置启动器各项参数并绑定接收端口
	 * @throws Exception
	 */
	public void start() throws Exception {

		check();

		NioEventLoopGroup parent = new NioEventLoopGroup(parentSize);
		NioEventLoopGroup children = new NioEventLoopGroup(childrenSize);

		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(parent, children);
		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {

			@Override
			protected void initChannel(NioSocketChannel ch) throws Exception {

				ChannelPipeline p = ch.pipeline();
				// 解码
				Method method = packetClass.getMethod("getDefaultInstance");
				@SuppressWarnings("unchecked")
				I value = (I) method.invoke(null);

				p.addLast("lengthDecoder", new ProtobufLengthDecoder());
				p.addLast("decoder", new ProtobufDecoder(value));
				p.addLast("lengthEecoder", new ProtobufLengthEncoder());
				p.addLast("encoder", new ProtobufEncoder());

				Constructor<H> constructor = handleClass.getConstructor(IClientFactory.class, PacketTransfer.class);
				// 业务逻辑处理
				p.addLast("handler", constructor.newInstance(clientFactory, transfer));
			}

		});

		serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {

			public void operationComplete(Future<? super Void> future) throws Exception {
				if (!future.isSuccess()) {
					logger.error("监听端口[" + port + "]失败", future.cause());
					System.exit(1);
				}
				logger.info("监听端口：[{}]成功", port);
			}
		});
	}
	
	/**
	 * 检查各项参数是否有效
	 * @throws Exception
	 */
	private void check() throws Exception {

		if (port == 0) {
			throw new Exception("port can not be 0");
		}

		if (handleClass == null) {
			throw new Exception("handleClass can not null");
		}

		if (packetClass == null) {
			throw new Exception("headerClass can not null");
		}

		if (clientFactory == null) {
			throw new Exception("clientFactory can not be null");
		}

		if (transfer == null) {
			throw new Exception("mgr can not be null");
		}
	}
	
	/**
	 * 设置接收端口
	 * @param port 端口号
	 * @return this
	 */
	public Acceptor<I, H> port(int port) {
		this.port = port;
		return this;
	}

	/**
	 * 处理接收的线程数
	 * @param parentSize 线程数
	 * @return this
	 */
	public Acceptor<I, H> parentSize(int parentSize) {
		this.parentSize = parentSize;
		return this;
	}

	/**
	 * 处理消息的线程数
	 * @param childrenSize 线程数
	 * @return this
	 */
	public Acceptor<I, H> childrenSize(int childrenSize) {
		this.childrenSize = childrenSize;
		return this;
	}

	/**
	 * 设置消息处理器类型
	 * @param handleClass 处理器类型
	 * @return this
	 */
	public Acceptor<I, H> handleClass(Class<H> handleClass) {
		this.handleClass = handleClass;
		return this;
	}

	/**
	 * 设置消息类型
	 * @param packetClass 消息类型
	 * @return this
	 */
	public Acceptor<I, H> packetClass(Class<I> packetClass) {
		this.packetClass = packetClass;
		return this;
	}

	/**
	 * 设置客户端对象的工厂
	 * @param clientFactory 工厂
	 * @return this
	 */
	public Acceptor<I, H> clientFactory(IClientFactory<? extends AbstractClient> clientFactory) {
		this.clientFactory = clientFactory;
		return this;
	}

	/**
	 * 设置转换器，用于获取对应消息的处理器
	 * @param transfer 转换器
	 * @return this
	 */
	public Acceptor<I, H> transfer(PacketTransfer<I> transfer) {
		this.transfer = transfer;
		return this;
	}

}
