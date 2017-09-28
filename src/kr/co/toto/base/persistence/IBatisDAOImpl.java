package kr.co.toto.base.persistence;

import java.util.List;

/**
 * <pre>
 * DAO 구현 클래스
 * </pre>
 *
 * @title IBatisDAOImpl.java
 * @project Default
 * @date 2012. 9. 21. 오후 7:33:08
 * @version 1.0
 * @author hjlee
 *
 */
public class IBatisDAOImpl extends AbstractIBatisDao implements IBatisDAO {
    /* (non-Javadoc)
     * @see kr.co.esmbc.inbk.base.persistence.IBatisDAO#select(java.lang.String)
     */
    @Override
    public Object select(String sqlmapId) {
        return template.queryForObject(sqlmapId);
    }

    /* (non-Javadoc)
     * @see kr.co.esmbc.inbk.base.persistence.IBatisDAO#select(java.lang.String, java.lang.Object)
     */
    @Override
    public Object select(String sqlmapId, Object params) {
        return template.queryForObject(sqlmapId, params);
    }

    /* (non-Javadoc)
     * @see kr.co.esmbc.inbk.base.persistence.IBatisDAO#selectList(java.lang.String)
     */
    @Override
    public List<?> selectList(String sqlmapId) {
        return template.queryForList(sqlmapId);
    }

    /* (non-Javadoc)
     * @see kr.co.esmbc.inbk.base.persistence.IBatisDAO#selectList(java.lang.String, java.lang.Object)
     */
    @Override
    public List<?> selectList(String sqlmapId, Object params) {
        return template.queryForList(sqlmapId, params);
    }

    /* (non-Javadoc)
     * @see kr.co.esmbc.inbk.base.persistence.IBatisDAO#update(java.lang.String)
     */
    @Override
    public int update(String sqlmapId) {
        return template.update(sqlmapId);
    }

    /* (non-Javadoc)
     * @see kr.co.esmbc.inbk.base.persistence.IBatisDAO#update(java.lang.String, java.lang.Object)
     */
    @Override
    public int update(String sqlmapId, Object data) {
        return template.update(sqlmapId, data);
    }

    /* (non-Javadoc)
     * @see kr.co.esmbc.inbk.base.persistence.IBatisDAO#delete(java.lang.String)
     */
    @Override
    public int delete(String sqlmapId) {
        return template.delete(sqlmapId);
    }

    /* (non-Javadoc)
     * @see kr.co.esmbc.inbk.base.persistence.IBatisDAO#delete(java.lang.String, java.lang.Object)
     */
    @Override
    public int delete(String sqlmapId, Object params) {
        return template.delete(sqlmapId, params);
    }
}
