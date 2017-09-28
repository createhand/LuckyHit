package kr.co.toto.base.persistence;

import java.util.List;

/**
 * <pre>
 * DAO 인터페이스
 * </pre>
 *
 * @title IBatisDAO.java
 * @project SMBC_INBK_BATCH
 * @date 2012. 9. 21. 오후 7:32:36
 * @version 1.0
 * @author hjlee
 *
 */
public interface IBatisDAO {
	/**
	 * 단건을 조회한다.
	 * @param sqlmapId sqlmap id
	 * @return 조회결과
	 */
	public Object select(String sqlmapId);

	/**
	 * 단건을 조회한다.
	 * @param sqlmapId sqlmap id
	 * @param params 조회파라미터
	 * @return 조회결과
	 */
	public Object select(String sqlmapId, Object params);

	/**
	 * 리스트를 조회한다.
	 * @param sqlmapId sqlmap id
	 * @return 조회결과
	 */
	public List<?> selectList(String sqlmapId);

	/**
	 * 리스트를 조회한다.
	 * @param sqlmapId sqlmap id
	 * @param params 조회파라미터
	 * @return 조회결과
	 */
	public List<?> selectList(String sqlmapId, Object params);

	
	/**
	 * 저장 또는 갱신처리를 수행한다.
	 * @param sqlmapId sqlmap id
	 * @return 저장/갱신 처리수
	 */
	public int update(String sqlmapId);

	/**
	 * 저장 또는 갱신처리를 수행한다.
	 * @param sqlmapId
	 * @param data 파라미터
	 * @return 저장/갱신 처리수
	 */
	public int update(String sqlmapId, Object data);

	/**
	 * 삭제처리를 수행한다.
	 * @param sqlmapId sqlmap id
	 * @return 삭제 처리수
	 */
	public int delete(String sqlmapId);

	/**
	 * 삭제처리를 수행한다.
	 * @param sqlmapId sqlmap id
	 * @param params 파라미터
	 * @return 삭제 처리수
	 */
	public int delete(String sqlmapId, Object params);
}
