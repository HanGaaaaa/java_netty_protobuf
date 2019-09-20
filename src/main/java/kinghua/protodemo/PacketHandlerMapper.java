package kinghua.protodemo;

/**
 * 消息编号，消息结构与消息处理映射。
 * @see com.syg.netcore.packethandler.mapper.PacketHandlerMapperMgr
 */
public class PacketHandlerMapper {

	private int pid;
	private Class<?> handleClass;
	private Class<?> msgClass;

	/**
	 * 获取绑定的协议号
	 * @return 协议号
	 */
	public int getPid() {
		return pid;
	}
	
	/**
	 * 设置绑定协议号
	 * @param pid 协议号
	 */
	public void setPid(int pid) {
		this.pid = pid;
	}

	/**
	 * 获取对应处理器类型
	 * @return 处理器类型
	 */
	public Class<?> getHandleClass() {
		return handleClass;
	}

	/**
	 * 设置对应处理器类型
	 * @param handleClass 处理器类型
	 */
	public void setHandleClass(Class<?> handleClass) {
		this.handleClass = handleClass;
	}

	/**
	 * 获取消息类型
	 * @return 消息类型
	 */
	public Class<?> getMsgClass() {
		return msgClass;
	}

	/**
	 * 设置消息类型
	 * @param msgClass 消息类型
	 */
	public void setMsgClass(Class<?> msgClass) {
		this.msgClass = msgClass;
	}
}
