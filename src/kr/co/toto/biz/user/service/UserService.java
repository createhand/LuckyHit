package kr.co.toto.biz.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import kr.co.toto.base.exception.BizException;
import kr.co.toto.base.persistence.IBatisDAO;
import kr.co.toto.base.persistence.IBatisDAOImpl;
import kr.co.toto.base.persistence.domain.DomainConst;
import kr.co.toto.biz.game.persistence.domain.GameDetailListDt;
import kr.co.toto.biz.game.persistence.domain.TeamPointDt;
import kr.co.toto.comn.model.TAData;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.BizUtil;
import kr.co.toto.util.ParamMap;


/**
 * <pre>
 * 토토게임 관련 Service
 *
 * </pre>
 *
 * @title GameService.java
 * @project TOTO
 * @date 2013. 4. 14. 오후 12:10:31
 * @version 1.0
 * @author seochan
 *
 */
@Service
public class UserService {
	
	private static final Log log = LogFactory.getLog(UserService.class);
    
	
	/**
     * 사용자 조회
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public TAData selectUserInfo(String userId) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (TAData)dao.select("USER.selectUser", userId);
    }
    
    /**
     * 사용자 등록
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int insertUser(TAData userInfo) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return dao.update("USER.insertUser", userInfo);
    }
    
}
