package kinghua.protodemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

/**
 * 协议长度解码器，读取消息开头的长度信息
 */
public class ProtobufLengthDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		in.markReaderIndex();

		if (in.readableBytes() < 4) {
			return;
		}

		int length = in.readInt();

		if (length < 0) {
			in.resetReaderIndex();
			throw new CorruptedFrameException("length less than 0");
		}

		if (in.readableBytes() < length) {
			in.resetReaderIndex();
			return;
		}
		out.add(in.readBytes(length));
		return;
	}

}

