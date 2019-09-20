package kinghua.protodemo;

import com.google.protobuf.GeneratedMessage;
import kinghua.protodemo.MessageBase.ClientPacket;
import kinghua.protodemo.MessageBase.ClientPacketHeader;

/**
 * gate<-->client消息处理器
 * @param <T> 消息类型，因为是发给游戏客户端的消息，应该是ClientPacket的子类
 */
public abstract class AbstractClientPacketHandler<T extends GeneratedMessage> extends PacketHandler<T>{
	
	@Override
	public ClientPacketHeader getHeader() {
		return (ClientPacketHeader) super.getHeader();
	}

	/**
	 * 向客户端发送消息
	 * @param reqId 消息序列ID，用于判断顺序
	 * @param pid 协议ID
	 * @param msg 要发送的消息
	 */
	public void writeMsg(int pid, GeneratedMessage msg) {
		ClientPacket.Builder packet = ClientPacket.newBuilder();
		ClientPacketHeader.Builder newHeader = ClientPacketHeader.newBuilder();
		
		newHeader.setType(pid);
		packet.setContent(msg.toByteString());
		getChannel().writeAndFlush(packet);
		logger.info("[SYG]To Client,Pid:[{}]",pid);
	}
	
}
