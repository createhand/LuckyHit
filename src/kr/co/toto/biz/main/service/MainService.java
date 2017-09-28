package kr.co.toto.biz.main.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.toto.base.persistence.IBatisDAO;
import kr.co.toto.base.persistence.IBatisDAOImpl;
import kr.co.toto.base.persistence.domain.MatchRecordMt;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.PagingList;
import kr.co.toto.util.ParamMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <pre>
 * 메인페이지 Service
 *
 * </pre>
 *
 * @title MainService.java
 * @project TOTO
 * @date 2013. 4. 14. 오후 12:10:31
 * @version 1.0
 * @author seochan
 *
 */
@Service
public class MainService {
	private static final Log log = LogFactory.getLog(MainService.class);
		
    /**
     * 경기결과 조회
     * 
     * @param user
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public List<MatchRecordMt> selectMatchReocordInfo(String mcCd) throws Exception {
    	// 경기결과를 조회한다.
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (List<MatchRecordMt>)dao.selectList("MATCH_RECORD_MT.select", mcCd);
    }

}
