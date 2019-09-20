package kinghua.protodemo;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.protobuf.GeneratedMessage;
import kinghua.protodemo.MessageBase.ClientPacket;
import kinghua.protodemo.MessageBase.ClientPacketHeader;


/**
 * 客户端连接信息
 */
public class PlayerClient extends AbstractClient {
	
	private static final Logger logger = LoggerFactory.getLogger(PlayerClient.class);

	private String username;
	private String password;
	private PlayerClientStatus status;


	public static enum PlayerClientStatus {
		/**
		 * 未授权
		 */
		unauthorized,
		/**
		 * 已授权
		 */
		authorized,
	}

	/**
	 * 构造方法，初始化状态及心跳时间
	 * @param channel 连接对象
	 */
	public PlayerClient(Channel channel) {
		super(ClientType.PLAYER_CLIENT, channel);
	}

	@Override
	public void clientCreated() {
		ClientManager.getInstance().addClient(this);
		logger.info("玩家连接被创建:remote address={}", getAddress());
	}

	@Override
	public void clientClosed() {
		logger.info("玩家连接被关闭:remote address={},", getAddress());
		
		// 断线玩家从列表中去掉
		ClientManager clientManager = ClientManager.getInstance();
		clientManager.removeClient(this);
	}


	/**
	 * 给客户端发送消息
	 * @param PID 协议ID
	 * @param ReqId 顺序ID
	 * @param Time 发送时间戳
	 * @param packet 消息
	 */
	public void SendClientPacket(int PID, GeneratedMessage packet){
		ClientPacket.Builder packetBuilder = ClientPacket.newBuilder();
		ClientPacketHeader.Builder headerBuilder = ClientPacketHeader.newBuilder();
		headerBuilder.setType(PID);
		packetBuilder.setHeader(headerBuilder);
		packetBuilder.setContent(packet.toByteString());
		writePacket(packetBuilder.build());
	}
	
	@Override
	public void writePacket(GeneratedMessage packet){
		logger.info("[SYG]To Client,Pid:[{}]",((ClientPacket)packet).getHeader(),getType());
		logger.info("[SYG]To Client,ClientId:[{}]",getClientId());
		logger.info("[SYG]To Client,UserId:[{}]",getUsername());
		super.writePacket(packet);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public PlayerClientStatus getStatus() {
		return status;
	}

	public void setStatus(PlayerClientStatus status) {
		this.status = status;
	}

}
