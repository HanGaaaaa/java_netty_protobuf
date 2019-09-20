package kinghua.protodemo;

import io.netty.channel.Channel;

import java.lang.reflect.Method;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessage;
import kinghua.protodemo.MessageBase.ClientPacket;
import kinghua.protodemo.MessageBase.ClientPacketHeader;

/**
 * 客户端协议转换器，用于获取客户端协议对应处理器
 */
public class ClientPacketTransfer implements PacketTransfer<ClientPacket> {

	/**
	 * 协议-处理器映射容器
	 */
	private PacketHandlerMapperMgr mgr;

	/**
	 * 构造方法
	 * @param mgr 对应协议的映射容器
	 */
	public ClientPacketTransfer(PacketHandlerMapperMgr mgr) {
		this.mgr = mgr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends GeneratedMessage> AbstractClientPacketHandler<T> transfer(Channel channel, ClientPacket packet) throws Exception {

		ClientPacketHeader header = packet.getHeader();

		PacketHandlerMapper mapper = mgr.getMapper(header.getType());
		if (mapper == null) {
			return null;
		}
		Class<?> msgClass = mapper.getMsgClass();
		Class<?> handleClass = mapper.getHandleClass();

		Method parser = msgClass.getMethod("parseFrom", ByteString.class);
		parser.setAccessible(true);

		ByteString data = packet.getContent();
		T o = (T) parser.invoke(null, data);

		AbstractClientPacketHandler<T> handle = (AbstractClientPacketHandler<T>) handleClass.newInstance();
		handle.setHeader(header);
		handle.setMsg(o);
		handle.setChannel(channel);

		return handle;

	}
}
