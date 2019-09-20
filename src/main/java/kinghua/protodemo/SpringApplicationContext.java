package kinghua.protodemo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring工具类，用于获取容器中的Bean
 */
public class SpringApplicationContext implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	/**
	 * 按类型获取Bean
	 * @param requiredType 要获取的Bean的类型
	 * @return Bean
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return context.getBean(requiredType);
	}

	/**
	 * 按名字和类型获取Bean
	 * @param name Bean名字
	 * @param requiredType Bean类型
	 * @return Bean
	 */
	public static <T> T getBean(String name, Class<T> requiredType) {
		return context.getBean(name, requiredType);
	}

}
