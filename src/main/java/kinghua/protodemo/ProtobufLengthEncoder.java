package kinghua.protodemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 协议长度编码器，添加协议长度在开头
 */
public class ProtobufLengthEncoder extends MessageToByteEncoder<ByteBuf> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
	
		out.writeInt(msg.readableBytes());
		out.writeBytes(msg);
	}
}
