package kinghua.protodemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import kinghua.protodemo.MessageBase.ClientPacket;

/**
 * GateServer启动类
 */
public class Server {

	private static final Logger logger = LoggerFactory.getLogger(Server.class);

	public static Server instance;

	private ApplicationContext applicationContext;

	/**
	 * 构造方法，启动了连接器和接收器
	 */
	public Server() {
		applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		@SuppressWarnings("unchecked")
		PacketTransfer<ClientPacket> clientPacketTransfer = applicationContext.getBean("clientPacketTransfer", PacketTransfer.class);

		// 监听玩家客户端连接
		Acceptor<ClientPacket, ClientAcceptorProtocolHandler> clientAcceptor = new Acceptor<>();
		clientAcceptor
		.port(Config.CLIENT_PORT)
		.parentSize(Config.CLIENT_ACCEPTOR_THREAD)
		.childrenSize(Config.CLIENT_IO_THREAD)
		.handleClass(ClientAcceptorProtocolHandler.class)
		.packetClass(ClientPacket.class)
		.clientFactory(new PlayerClientFactory())
		.transfer(clientPacketTransfer);
		try {
			clientAcceptor.start();
		} catch (Exception e) {
			logger.error("", e);
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		instance = new Server();
	}

}

