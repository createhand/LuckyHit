package kr.co.toto.base.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import kr.co.toto.base.exception.BizException;
import kr.co.toto.base.persistence.IBatisDAO;
import kr.co.toto.base.persistence.IBatisDAOImpl;
import kr.co.toto.base.persistence.domain.GameMt;
import kr.co.toto.base.persistence.domain.MatchRecordMt;
import kr.co.toto.base.persistence.domain.TeamMt;
import kr.co.toto.comn.model.TAData;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.ParamMap;


/**
 * <pre>
 * 토토게임 관련 Service
 *
 * </pre>
 *
 * @title CommonService.java
 * @project TOTO
 * @date 2013. 4. 14. 오후 12:10:31
 * @version 1.0
 * @author seochan
 *
 */
@Service
public class CommonService {
	private static final Log log = LogFactory.getLog(CommonService.class);
		
	
	//
	/**
     *팀 입력
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int insertTeam(HashMap<String, String> game) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	int result = 0;
    	result += dao.update("TEAM_MT.insertTeam", game);
    	return result;
    }
    
    /**
     * 리그의 가장 최근시즌 조회
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public String selectLatestSeasonInfo(String lgCd) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (String)dao.select("TEAM_MT.selectLatestSeasonInfo", lgCd);
    }
    
    /**
     * 해당시즌 팀 순위 및 득실점
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public List<TAData> selectTeamSeasonInfo(HashMap<String, String> params) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (List<TAData>)dao.selectList("TEAM_MT.selectTeamSeasonInfo", params);
    }
    
    /**
     * 팀조회
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public TeamMt selectTeam(String tmCd) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (TeamMt)dao.select("TEAM_MT.select", tmCd);
    }
    
    /**
     * 팀목록조회
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public List<TeamMt> selectTeamList(ParamMap params) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (List<TeamMt>)dao.selectList("TEAM_MT.selectList", params);
    }
    
    /**
     * 팀코드조회(betman 팀명)
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public String selectTeamCodeByBetman(String tmNm) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (String)dao.select("TEAM_MT.selectTeamCodeByBetman", tmNm);
    }
    
    /**
     * 팀코드조회(daum 팀명)
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public String selectTeamCodeByDaum(String tmNm) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (String)dao.select("TEAM_MT.selectTeamCodeByDaum", tmNm);
    }
    
    
    /**
     * 경기조회
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public MatchRecordMt selectRecord(String mcCd) throws Exception {    	
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (MatchRecordMt)dao.select("MATCH_RECORD_MT.select", mcCd);
    }
    
    /**
     * 게임조회
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public GameMt selectGame(String gmCd) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (GameMt)dao.select("GAME_MT.select", gmCd);
    }
    
    /**
     * 게임목록조회(단건)
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public HashMap<String, String> selectGameListNo(HashMap<String, String> map) throws Exception {    	
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (HashMap<String, String>)dao.select("GAME_MT.selectListNo", map);
    }
    
    /**
     * 게임목록조회(다건)
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public List<HashMap<String, String>> selectGameAllListNo(HashMap<String, String> map) throws Exception {    	
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (List<HashMap<String, String>>)dao.select("GAME_MT.selectListNo", map);
    }
    
    /**
     * 종료게임수 체크
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int selectEndCnt(String gmCd) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (Integer)dao.select("GAME_MT.selectEndCnt", gmCd);
    }

}
