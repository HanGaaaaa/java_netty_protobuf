package kinghua.protodemo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.AttributeKey;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.protobuf.GeneratedMessage;

/**
 * 连接信息类，保存当前连接相关信息
 */
public abstract class AbstractClient {

	private static final Logger logger = LoggerFactory.getLogger(AbstractClient.class);
	
	/**
	 * 将连接客户端对象存入连接channel时使用的key
	 */
	public static final AttributeKey<AbstractClient> CLIENT_KEY = AttributeKey.valueOf("client_key");

	/**
	 * 客户端对象ID生成器
	 */
	private static AtomicLong ID_FACTORY = new AtomicLong(1);

	/**
	 * 客户端对象类型
	 */
	public static enum ClientType {
		/**
		 * 无类型
		 */
		UNKOWN, 
		/**
		 * 玩家连接对象
		 */
		PLAYER_CLIENT, 
	}

	private final long clientId;

	private final ClientType type;

	private final Channel channel;

	private final InetSocketAddress address;

	/**
	 * 连接客户端对象的构造方法
	 * @param type 客户端类型
	 * @param channel 连接对象
	 */
	public AbstractClient(ClientType type, Channel channel) {
		this.clientId = ID_FACTORY.getAndIncrement();
		this.type = type;
		this.channel = channel;
		this.address = (InetSocketAddress) channel.remoteAddress();
	}
	
	/**
	 * 发送消息给客户端
	 * @param packet 要发送的消息
	 */
	public void writePacket(GeneratedMessage packet) {
		if (!channel.isActive()) {
			logger.warn("channel[{}] is inActive when write msg to the channel.", address);
			return;
		}
		channel.writeAndFlush(packet);
	}

	/**
	 * 关闭客户端连接
	 * @return ChannelFuture
	 */
	public ChannelFuture close() {
		return channel.close();
	}

	/**
	 * 这个方法在客户端关闭时回调，业务代码不能调用该方法。 当需要在连接关闭时做其他的操作可以重写该方法
	 */
	public abstract void clientClosed();

	/**
	 * 这个方法在客户端创建时回调，业务代码不能调用该方法。 当需要在创建后做其他的操作可以重写该方法
	 */
	public abstract void clientCreated();
	
	/**
	 * 获取连接客户端ID
	 * @return clientId 连接ID
	 */
	public long getClientId() {
		return clientId;
	}

	/**
	 * 获取客户端类型
	 * @return type 客户端对象类型
	 */
	public ClientType getType() {
		return type;
	}

	/**
	 * 获取连接对象
	 * @return channel 连接对象
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * 获取连接地址
	 * @return address 连接地址
	 */
	public InetSocketAddress getAddress() {
		return address;
	}

}

