package kinghua.protodemo;

import io.netty.channel.Channel;

/**
 * 连接客户端对象工厂
 * @param <T> 生产的客户端对象类型
 */
public interface IClientFactory<T extends AbstractClient> {

	/**
	 * 生产连接客户端对象
	 * @param channel 对应的连接对象
	 * @return 生产的连接客户端对象
	 */
	public T create(Channel channel);
}
