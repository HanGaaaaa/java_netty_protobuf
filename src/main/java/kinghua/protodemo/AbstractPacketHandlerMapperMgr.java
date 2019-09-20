package kinghua.protodemo;

import java.util.HashMap;
import java.util.Map;

/**
 * PacketHandlerMapper映射管理器基类
 */
public abstract class AbstractPacketHandlerMapperMgr implements PacketHandlerMapperMgr {

	private final Map<Integer, PacketHandlerMapper> mappers = new HashMap<>();

	@Override
	public void registerMapper(PacketHandlerMapper mapper) {
		mappers.put(mapper.getPid(), mapper);
	}

	@Override
	public PacketHandlerMapper getMapper(int pid) {
		return mappers.get(pid);
	}

}

