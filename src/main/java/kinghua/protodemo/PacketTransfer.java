package kinghua.protodemo;

import io.netty.channel.Channel;

import com.google.protobuf.GeneratedMessage;

/**
 * 转换器，用于获取协议对应处理器
 * @param <I> 协议类型
 */
public interface PacketTransfer<I extends GeneratedMessage> {

	/**
	 * 读取之前绑定的映射关系，获取绑定协议的处理器
	 * @param channel 连接对象
	 * @param in 要处理的消息
	 * @return
	 * @throws Exception
	 */
	public <T extends GeneratedMessage> PacketHandler<T> transfer(Channel channel, I in) throws Exception;

}
