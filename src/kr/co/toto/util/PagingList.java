package kr.co.toto.util;

import java.io.Serializable;
import java.util.List;


/**
 * 사용방법
 * 
 * BaseAction의 convertPagingParams()를 통하여 생성된 PagingMap을 인자로 넘겨받아
 * offset을 설정한다.
 * 
 * 
 * @author hjlee
 *
 */
public class PagingList implements Serializable  {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private List<Object> resultList;
    private int offset;
    private int totalCount; // 총 카운트

    

    public PagingList() {
    	//offset =  Integer.parseInt((String)params.get("_page_offset"));
        resultList = null;
        totalCount = 0;
    }

    /**<pre>
     * 생성자
     * 
     * @param resultList
     * @param totalSize
     */
    public PagingList(List<Object> resultList, int totalCount) {

        this();
        this.resultList = resultList;
        this.totalCount = totalCount;
    }

    /**<pre>
     * 결과값 vo의 ArrayList를 리턴한다.
     * 
     * @param
     * @return	ArrayList		result list
     * @thorws
     */
    public List<Object> getResultList() {
        return resultList;
    }

    /**<pre>
     * 전체 개수를 리턴한다.
     * 
     * @param
     * @return	int		전체카운트
     * @throws
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**<pre>
     * 결과 Array List를 입력한다.
     * 
     * @param 	ArrayList resultList		결과리스트
     * @return	void
     * @throws
     */
    public void setResultList(List<Object> resultList) {
        this.resultList = resultList;
    }

    /**<pre>
     * 전체 개수를 입력한다.
     * 
     * @param 	int totalCnt		전체 갯수
     * @return	void
     * @throws
     */
    public void setTotalCount(int totalCnt) {
        this.totalCount = totalCnt;
    }

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
    
}