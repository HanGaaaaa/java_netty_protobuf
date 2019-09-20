package kinghua.protodemo;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kinghua.protodemo.MessageBase.ClientPacket;
import kinghua.protodemo.MessageBase.ClientPacketHeader;

/**
 * 处理玩家发送过来的协议
 */
public class ClientAcceptorProtocolHandler extends AcceptorProtocolHandler<ClientPacket> {

	private static final Logger logger = LoggerFactory.getLogger(ClientAcceptorProtocolHandler.class);

	/**
	 * 构造函数
	 * @param clientFactory 客户端信息类工厂
	 * @param transfer 转换器
	 */
	public ClientAcceptorProtocolHandler(IClientFactory<PlayerClient> clientFactory, PacketTransfer<ClientPacket> transfer) {
		super(clientFactory, transfer);
	}

	@Override
	public void dispatch(Channel channel, ClientPacket packet) {
		ClientPacketHeader clientHeader = packet.getHeader();
		int pid = clientHeader.getType();
		logger.info("[SYG] From Client, Pid:[{}]",pid);
		super.dispatch(channel, packet);
		
		//添加多服务器在后边添加
	}
}
