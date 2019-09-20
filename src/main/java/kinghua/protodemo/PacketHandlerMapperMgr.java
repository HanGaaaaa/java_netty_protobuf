package kinghua.protodemo;

/**
 * 消息编号-消息结构-消息处理类映射(PacketHandlerMapper)管理器接口
 * 提供注册映射和获取映射方法
 * @see com.syg.netcore.packethandler.mapper.PacketHandlerMapper
 */
public interface PacketHandlerMapperMgr {

	/**
	 * 初始化隐射器管理器
	 * @throws Exception 
	 */
	public void init() throws Exception;
	
	/**
	 * 注册映射
	 * @param mapper 要注册的映射对象
	 */
	public void registerMapper(PacketHandlerMapper mapper) ;
	
	/**
	 * 获取映射
	 * @param pid 协议号
	 * @return 映射对象
	 */
	public PacketHandlerMapper getMapper(int pid) ;
}
