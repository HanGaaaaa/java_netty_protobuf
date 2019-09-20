package kinghua.protodemo;

import com.google.protobuf.GeneratedMessage;


/**
 * 服务器消息处理器
 * @param <T> 消息类型，应该是ClientPacket子类
 */
public abstract class ClientPacketHandler<T extends GeneratedMessage> extends AbstractClientPacketHandler<T> {

	public PlayerClient getClient() {
		return (PlayerClient) getChannel().attr(AbstractClient.CLIENT_KEY).get();
	}
}
