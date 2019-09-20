package kinghua.protodemo;

import io.netty.channel.Channel;


/**
 * 客户端信息类工厂
 */
public class PlayerClientFactory implements IClientFactory<PlayerClient> {

	@Override
	public PlayerClient create(Channel channel) {
		PlayerClient client = new PlayerClient(channel);

		return client;
	}

}
