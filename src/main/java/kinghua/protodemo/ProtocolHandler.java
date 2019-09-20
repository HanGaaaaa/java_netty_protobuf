package kinghua.protodemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import com.google.protobuf.GeneratedMessage;

/**
 * 协议处理器
 * @param <I> 读取到的消息类型
 * @param <O> 对应的处理器类型
 */
public abstract class ProtocolHandler<I extends GeneratedMessage> extends ChannelInboundHandlerAdapter implements PacketDispacther<I> {

	private static Logger logger=LoggerFactory.getLogger(ProtocolHandler.class);
	
	private PacketTransfer<I> transfer;

	/**
	 * 构造方法
	 * @param transfer 转换器，用协议ID获取处理器对象
	 */
	public ProtocolHandler(PacketTransfer<I> transfer) {
		super();
		this.setTransfer(transfer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof GeneratedMessage) {
			Channel channel = ctx.channel();
			dispatch(channel, (I) msg);
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	public void dispatch(Channel channel, I packet) {
		try {
			PacketHandler<?> handler = getTransfer().transfer(channel, packet);
			if (handler != null) {
				handler.run();
			}
		} catch (Exception e) {
			logger.error("协议处理异常",e);
		}
	}

	/**
	 * 获取转换器
	 * @return 转换器
	 */
	public PacketTransfer<I> getTransfer() {
		return transfer;
	}

	/**
	 * 设置转换器
	 * @param transfer 转换器
	 */
	public void setTransfer(PacketTransfer<I> transfer) {
		this.transfer = transfer;
	}

}

