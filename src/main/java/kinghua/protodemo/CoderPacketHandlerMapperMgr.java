package kinghua.protodemo;

/**
 * 编码方式映射管理器
 */
public abstract class CoderPacketHandlerMapperMgr extends AbstractPacketHandlerMapperMgr {

	/**
	 * 注册映射
	 * @param pid 协议ID
	 * @param msgClass 协议类型
	 * @param handleClass 处理器类型
	 */
	public void registerMapper(int pid, Class<?> msgClass, Class<?> handleClass) {
		PacketHandlerMapper mapper = new PacketHandlerMapper();
		mapper.setPid(pid);
		mapper.setMsgClass(msgClass);
		mapper.setHandleClass(handleClass);
		registerMapper(mapper);
	}

}