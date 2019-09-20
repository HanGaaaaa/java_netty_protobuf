package kinghua.protodemo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kinghua.protodemo.MessageBase.ClientPacket;

/**
 * 连接客户端管理器
 */
public class ClientManager {
	
	
	/**
	 * 所有连接
	 */
	private Map<Long, AbstractClient> allClients = new ConcurrentHashMap<>();

	/**
	 * 所有玩家
	 */
	private Map<Long, PlayerClient> playerClients = new ConcurrentHashMap<>();

	private ClientManager() {
	}
	
	/**
	 * 获取单例对象
	 * @return 单例对象
	 */
	public static ClientManager getInstance() {
		return Singleton.instance;
	}

	private static class Singleton {
		private static ClientManager instance = new ClientManager();
	}

	/**
	 * 添加一个未登录客户端对象
	 * @param client 要添加的客户端对象
	 */
	public void addClient(AbstractClient client) {
		allClients.put(client.getClientId(), client);
	}

	/**
	 * 移除一个未登录的客户端对象
	 * @param client 要移除的客户端对象
	 */
	public void removeClient(AbstractClient client) {
		allClients.remove(client.getClientId());
	}

	/**
	 * 从未登录列表中查找指定ID的玩家客户端对象
	 * @param clientId 指定的客户端ID
	 * @return playClient 要查找的客户端对象
	 */
	public PlayerClient getPlayerClientByClientId(long clientId) {
		AbstractClient c = allClients.get(clientId);
		if (c == null || !(c instanceof PlayerClient)) {
			return null;
		}
		return (PlayerClient) c;
	}

	/**
	 * 玩家客户端被授权
	 * @param clientId 要授权的客户端ID
	 * @param accountId 要授权的玩家ID
	 */
	public void authorizedPlayerClient(long clientId, long accountId) {
		AbstractClient c = allClients.get(clientId);
		PlayerClient playerClient = null;
		if (c != null && c instanceof PlayerClient) {
			playerClient = (PlayerClient) c;
			playerClients.put(accountId, playerClient);
		}
	}
	
	/**
	 * 取消玩家客户端授权
	 * @param accountId 玩家ID
	 * @return playerClient 取消的玩家客户端对象
	 */
	public PlayerClient cancelPlayerClientAuthorized(long accountId) {
		return playerClients.remove(accountId);
	}

	/**
	 * 获取已登录的玩家客户端
	 * @param accountId 玩家ID
	 * @return playerClient 玩家客户端对象
	 */
	public PlayerClient getAuthorizedPlayerClient(long accountId) {
		return playerClients.get(accountId);
	}

}

