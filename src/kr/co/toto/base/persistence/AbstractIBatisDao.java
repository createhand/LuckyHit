package kr.co.toto.base.persistence;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

/**
 * <pre>
 * 스프링프레임웍의 SqlMapClientTemplate을 맴버변수로 선언한 IBaitsDao 추상클래스
 * </pre>
 *
 * @title AbstractIBatisDao.java
 * @project Default
 * @date 2012. 9. 21. 오후 7:30:33
 * @version 1.0
 * @author hjlee
 *
 */
public abstract class AbstractIBatisDao {

	protected SqlMapClientTemplate template;

    /**
     * <pre>
     * SqlMapClientTemplate을 설정한다
     * </pre>
     *
     * @param template SqlMapClientTemplate
     */
	public void setTemplate(SqlMapClientTemplate template) {
		this.template = template;
	}
}
