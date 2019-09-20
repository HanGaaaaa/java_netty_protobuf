package kinghua.protodemo;
import kinghua.protodemo.Login.c2s_login;

public class ServerCodePacketHandlerMapperMgr extends CoderPacketHandlerMapperMgr{
	@Override
	public void init() throws Exception {
		registerMapper(PID.c2s_Login, c2s_login.class, LoginHandler.class);
	}
}
