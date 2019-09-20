package kinghua.protodemo;

import io.netty.channel.Channel;

import com.google.protobuf.GeneratedMessage;

/**
 * 消息分发器
 * @param <T> 消息类型
 */
public interface PacketDispacther <T extends GeneratedMessage>{

	/**
	 * 分发指定Channel的消息
	 * @param channel 连接对象
	 * @param packet 要分发的消息
	 */
	public void dispatch(Channel channel, T packet);

}