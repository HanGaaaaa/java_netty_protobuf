package kinghua.protodemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.protobuf.GeneratedMessage;
import kinghua.protodemo.Login.c2s_login;
import kinghua.protodemo.Login.s2c_login;


public class LoginHandler extends ClientPacketHandler<c2s_login>{
	
	private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);
	@Override
	protected void runImpl() throws Throwable {
		// TODO Auto-generated method stub
		c2s_login requert = getMsg();
		String name = requert.getUsername();
		String password = requert.getUserpassword();
		if (name != "123" && password != "123") {
			//正确
			s2c_login.Builder builder = s2c_login.newBuilder();
			builder.setResult(1);
			for (int i = 0; i < 4; i++) {
				kinghua.protodemo.Login.device_info.Builder deviceBuilder = kinghua.protodemo.Login.device_info.newBuilder();
				deviceBuilder.setBattary(5);
				deviceBuilder.setDeviceId(i);
				deviceBuilder.setDeviceIp("192.168.1.81");
				deviceBuilder.setDeviceName("三号线1号机组");
				builder.addDeviceList(deviceBuilder);
			}
			getClient().SendClientPacket(PID.s2c_Login, builder.build());
			//writeMsg(PID.s2c_Login, builder.build());
		}else {
			//错误
			s2c_login.Builder builder = s2c_login.newBuilder();
			builder.setResult(0);
			builder.setReason(1);
			getClient().SendClientPacket(PID.s2c_Login, builder.build());
			//writeMsg(PID.s2c_Login, builder.build());
		}
	}
	@Override
	protected void setTimeThreadLocal(ThreadLocal<Long> timeThreadLocal) {
		// TODO Auto-generated method stub
		
	}
}
