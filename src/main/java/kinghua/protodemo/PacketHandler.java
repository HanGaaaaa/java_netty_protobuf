package kinghua.protodemo;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.protobuf.GeneratedMessage;

/**
 * 消息处理器
 * @param <T> 消息类型
 */
public abstract class PacketHandler<T extends GeneratedMessage> implements Runnable {

	/**
	 * 消息发送的时间，用于判断消息的顺序
	 */
	public static ThreadLocal<Long> timeThreadLocal = new ThreadLocal<Long>();

	protected static final Logger logger = LoggerFactory.getLogger(PacketHandler.class);

	/**
	 * 要处理消息的消息头
	 */
	private GeneratedMessage header;
	/**
	 * 消息体
	 */
	private T msg;
	/**
	 * 消息来源channel
	 */
	private Channel channel;
	
	public void run() {
		try {
			setTimeThreadLocal(timeThreadLocal);
			runImpl();
		}catch(GameException e){

		}catch (Throwable e) {
			logger.error("execute packet handler error.", e);
		}
	}

	/**
	 * 实际处理逻辑，由子类实现
	 * @throws Throwable
	 */
	protected abstract void runImpl() throws Throwable;

	/**
	 * 设置收到消息的时间，用于判断消息的顺序
	 * @param timeThreadLocal 收到消息的时间戳
	 */
	protected abstract void setTimeThreadLocal(ThreadLocal<Long> timeThreadLocal);

	/**
	 * 获取消息头
	 * @return header 消息头
	 */
	public GeneratedMessage getHeader() {
		return header;
	}

	/**
	 * 设置消息头
	 * @param header 消息头
	 */
	public void setHeader(GeneratedMessage header) {
		this.header = header;
	}

	/**
	 * 获取连接对象
	 * @return channel连接对象
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * 设置连接对象
	 * @param channel
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	/**
	 * 设置消息体
	 * @param msg
	 */
	public void setMsg(T msg) {
		this.msg = msg;
	}

	/**
	 * 获取消息体
	 * @return
	 */
	public T getMsg() {
		return msg;
	}
	
	public abstract void writeMsg(int pid, GeneratedMessage msg);

}

