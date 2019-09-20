package kinghua.protodemo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import com.google.protobuf.GeneratedMessage;

/**
 * 接收协议处理器
 * @param <I> 接收协议的类型
 */
public abstract class AcceptorProtocolHandler<I extends GeneratedMessage> extends ProtocolHandler<I> {

	private IClientFactory<? extends AbstractClient> clientFactory;

	/**
	 * 构造方法
	 * @param clientFactory 连接客户端对象的工厂
	 * @param transfer 转换器，用ID获取处理器对象
	 */
	public AcceptorProtocolHandler(IClientFactory<? extends AbstractClient> clientFactory, PacketTransfer<I> transfer) {
		super(transfer);
		this.clientFactory = clientFactory;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		AbstractClient client = clientFactory.create(channel);
		channel.attr(AbstractClient.CLIENT_KEY).set(client);
		client.clientCreated();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		AbstractClient client = (AbstractClient) channel.attr(AbstractClient.CLIENT_KEY).get();
		client.clientClosed();
	}

}

