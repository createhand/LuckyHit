/********************************************************* 
  @프로그램명   :  ServletContextResourceFactoryBean
  @TRIN ID      :  
  @프로그램목적 :  servlet context의 특정 resource를 가리키는 URL을 반환하는 FactoryBean
  @적용일시     :  2008/08/12
  @히스토리관리 :
      수정일         변경자       내용
   ------------------------------------------------------
      수정일      변경한 개발자명  변경한 내용

**********************************************************/

package kr.co.toto.util;

import java.net.URL;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.web.context.ServletContextAware;

/**
 * servlet context의 특정 resource를 가리키는 URL을 반환하는 FactoryBean
 * 
 * @author 박성철
 *
 */
public class ServletContextResourceFactoryBean implements FactoryBean, ServletContextAware {
	
	private final static Log LOG = LogFactory.getLog(ServletContextResourceFactoryBean.class);
	
	private ServletContext servletContext;
	private String path;

	/**
	 * 지정된 리소스의 URL을 반환
	 */
	public Object getObject() throws Exception {

		URL url = this.servletContext.getResource(path);
		if(url == null)
		{
			String msg = "Could not found resource '"+path+"' from servlet context.";
			LOG.error(msg);
			throw new NullPointerException(msg);
		}
		return url;
	}
	
	/**
	 * getObject()로 반환되는 객체 타입인 java.net.URL을 반환
	 */
	public Class getObjectType() {
		return URL.class;
	}
	
	/**
	 * 반환되는 객체가 싱글톤인지 여부를 반환. 여기서는 false를 반환한다.
	 */
	public boolean isSingleton() {
		return false;
	}

	/**
	 * ServletContext를 지정. 스프링 컨테이너가 초기에 servlet context를 알려준다.
	 */
	public void setServletContext(ServletContext servletContext) {
		LOG.debug("set servletcontext :"+servletContext.getServerInfo());
		this.servletContext = servletContext;
	}

	/**
	 * URL을 얻으려고 하는 자원의 경로명.
	 * @param path
	 */
    public void setPath(String path) {
    	LOG.debug("path = "+path);
    	this.path = path;
    }
}