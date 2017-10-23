package kr.co.toto.biz.game.service;

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
public class GameService {
	
	private static final Log log = LogFactory.getLog(GameService.class);
    
	
	/**
     * 해당 팀의 경기중 이변이 있던 경기 목록을 조회한다
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public List<TAData> selectAmazingList(String tmCd) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (List<TAData>)dao.selectList("GAME.selectAmazingList", tmCd);
    }
	/**
     * 전체 게임목록을 조회한다
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public TAData selectGameBetList(List<GameDetailListDt> gameDetailList, String url) throws Exception {
    	
    	TAData returnMap = new TAData();
    	String result = BizUtil.getUrlSource(url, "EUC-KR");
    	
   		//string to DOM parsing
    	Document doc = Jsoup.parse(result);
    	
    	if(StringUtils.isBlank(result)) {
    		throw new Exception(url+"\n게임정보가 없습니다.");
    	}
    	
    	Elements trList = doc.getElementsByTag("tr");
    	
    	//총 발매금액 및 총 베팅수
    	String rate = doc.getElementsByClass("rate").text();
    	rate = rate.replaceAll("[^\\d.]", "");
    	double totalBetCnt = Long.parseLong(rate)/1000;
    	
    	for(Element tr : trList) {
    		
    		Elements tdList = tr.getElementsByTag("td");
    		Iterator<Element> list = tdList.iterator();
    		String gameListNo = null;
    		String winBet = null, drawBet = null, loseBet = null;
    		
    		if(tdList.size() == 16 ) {
    			int i = 0;
        		while(list.hasNext()) {
        			i++;
        			Element tdObj = list.next();
        			String tdText = tdObj.text();
        			
        			//경기번호
        			if(i == 1) {
        				if(tdText.length() == 3) gameListNo = tdText.substring(0, 1);
        				else if(tdText.length() == 4) gameListNo = tdText.substring(0, 2);
        			}
        				        			
        			//승리배팅
        			if(i == 8) {
        				winBet = tdText.substring(tdText.indexOf("[")+1, tdText.indexOf("]"));
        				winBet = winBet.replace(",", "");
        			}
        			
        			//무승부배팅
        			if(i == 11) {
        				drawBet = tdText.substring(tdText.indexOf("[")+1, tdText.indexOf("]"));
        				drawBet = drawBet.replace(",", "");
        			}
        			
        			//패배배팅
        			if(i == 14) {
        				loseBet = tdText.substring(tdText.indexOf("[")+1, tdText.indexOf("]"));
        				loseBet = loseBet.replace(",", "");
        			}
        			
        		}
    		}
    		
    		if(gameListNo != null) {
    			int i = new BizUtil().convertInt(gameListNo)-1;
    			GameDetailListDt dt = gameDetailList.get(i);
    			dt.setWinBetCnt(new BizUtil().convertInt(winBet));
    			dt.setDrawBetCnt(new BizUtil().convertInt(drawBet));
    			dt.setLoseBetCnt(new BizUtil().convertInt(loseBet));        			
    			gameDetailList.set(i, dt);
    		}
    	}
    	
    	returnMap.set("gameDetailList", gameDetailList);
    	returnMap.set("totalBetCnt", totalBetCnt);
    	
    	return returnMap;
    }
    
	/**
     * 전체 게임목록을 조회한다
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public List<HashMap> selectGameList(TAData map) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (List<HashMap>)dao.selectList("GAME.selectGameList", map);
    }
    
    /**
     * 픽이 있는 게임 목록 조회한다
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public List<TAData> selectPickGameList(ParamMap map) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (List<TAData>)dao.selectList("GAME.selectPickGameList", map);
    }
    
    /**
     * 픽이 있는 게임 목록 조회한다
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public TAData selectPickGameInfo(ParamMap map) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (TAData)dao.selectList("GAME.selectPickGameList", map);
    }
    
    /**
     * 픽이 있는 게임 목록 카운트
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int selectPickGameListCount(ParamMap map) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (Integer)dao.select("GAME.selectPickGameListCount", map);
    }
    
    /**
     * 특정회차 게임목록을 조회한다
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public List<GameDetailListDt> selectGameDetailList(ParamMap params) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (List<GameDetailListDt>)dao.selectList("GAME.select", params);
    }
    
    
    /**
     * 최근 게임코드, 회차, 종료일자
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public HashMap selectLatestGame() throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (HashMap)dao.select("GAME.latestGame");
    }
    
    /**
     * 최근 픽한 게임코드, 회차, 종료일자
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public HashMap selectLatestPick(ParamMap params) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (HashMap)dao.select("GAME.latestPick", params);
    }
    
    /**
     * 팀 점수(계산식)
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public double getTeamPoint(TeamPointDt objTeamPoint) throws Exception {
    	
    	
    	double latestPoint = 0, latestPointAtHome = 0, againstPoint = 0, againstPointAtHome = 0, teamPoint = 0;

    	
    	/****************** 최근전적 ******************/
    	//최근전적점수 적용
    	List<String> latestRecord = objTeamPoint.getLatestRecord();
    	if(latestRecord == null) latestRecord = new ArrayList<String>();
    	
    	for(int i=0;i<latestRecord.size();i++) {
    		if(latestRecord.get(i).equals(DomainConst.RECORD_WIN)) latestPoint += DomainConst.LATEST_WIN_POINT;
    		else if(latestRecord.get(i).equals(DomainConst.RECORD_LOSE)) latestPoint += DomainConst.LATEST_LOSE_POINT;
    		else latestPoint += DomainConst.LATEST_DRAW_POINT;    		
    	}
    	

    	//홈에서 최근전적점수 적용
    	List<String> latestRecordAtHome = objTeamPoint.getLatestRecordAtHome();
    	if(latestRecordAtHome == null) latestRecordAtHome = new ArrayList<String>();    	
    	for(int i=0;i<latestRecordAtHome.size();i++) {
    		if(latestRecordAtHome.get(i).equals(DomainConst.RECORD_WIN)) latestPointAtHome += DomainConst.LATEST_WIN_POINT;
    		else if(latestRecordAtHome.get(i).equals(DomainConst.RECORD_LOSE)) latestPointAtHome += DomainConst.LATEST_LOSE_POINT;
    		else latestPointAtHome += DomainConst.LATEST_DRAW_POINT;    		
    	}

    	
    	//최근전적점수 가중치 적용
    	latestPoint = (latestPoint + latestPointAtHome) * DomainConst.LATEST_WEIGHTED_VALUE;
    	
    	
    	/****************** 상대전적 ******************/
    	//최근상대전적점수 적용
    	List<String> againstRecord = objTeamPoint.getAgainstRecord();
    	if(againstRecord == null) againstRecord = new ArrayList<String>();
    	for(int i=0;i<againstRecord.size();i++) {
    		if(againstRecord.get(i).equals(DomainConst.RECORD_WIN)) againstPoint += DomainConst.AGAINST_WIN_POINT;
    		else if(againstRecord.get(i).equals(DomainConst.RECORD_LOSE)) againstPoint += DomainConst.AGAINST_LOSE_POINT;
    		else againstPoint += DomainConst.AGAINST_DRAW_POINT;    		
    	}
    	
    	//홈에서 최근상대전적점수 적용
    	List<String> againstRecordAtHome = objTeamPoint.getAgainstRecordAtHome();
    	for(int i=0;i<againstRecordAtHome.size();i++) {
    		if(againstRecordAtHome.get(i).equals(DomainConst.RECORD_WIN)) againstPointAtHome += DomainConst.AGAINST_WIN_POINT;
    		else if(againstRecordAtHome.get(i).equals(DomainConst.RECORD_LOSE)) againstPointAtHome += DomainConst.AGAINST_LOSE_POINT;
    		else againstPointAtHome += DomainConst.AGAINST_DRAW_POINT;    		
    	}
    	
    	//최근상대전적점수 가중치 적용
    	againstPoint = (againstPoint + againstPointAtHome) * DomainConst.AGAINST_WEIGHTED_VALUE;
    	
    	
    	/****************** 합산 ******************/
    	//팀최종점수
    	teamPoint = latestPoint + againstPoint;
    	
    	return teamPoint;
    }
    

    /**
     * 승,무,패 목록에서 점수산출
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public double getPointByList(List<String> list) throws Exception {
    	double listPoint = 0;
    	
    	if(list == null) list = new ArrayList<String>();
    	for(int i=0;i<list.size();i++) {
    		if(list.get(i).equals(DomainConst.RECORD_WIN)) listPoint += DomainConst.LATEST_WIN_POINT;
    		else if(list.get(i).equals(DomainConst.RECORD_LOSE)) listPoint += DomainConst.LATEST_LOSE_POINT;
    		else listPoint += DomainConst.LATEST_DRAW_POINT;    		
    	}
    	return listPoint;
    }
    
    
    /**
     * 게임입력
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int insertGame(HashMap<String, String> game) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	int result = 0;
    	result += dao.update("GAME_MT.insertGame", game);
    	return result;
    }
    
    /**
     * 게임수정
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int updateGame(HashMap<String, String> game) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	int result = 0;
    	result += dao.update("GAME_MT.updateGame", game);
    	return result;
    }    
    
    /**
     * 게임목록 입력
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int insertGameList(List<HashMap<String, String>> gameList) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	int result = 0;
    	for(int i =0;i<gameList.size();i++) {
    			result += dao.update("GAME_MT.insertGameList", gameList.get(i));
    	}    	
    	return result;
    }
    
    /**
     * 게임목록 업데이트(선택률)
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int updateGameList(List<HashMap<String, String>> gameList) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	int result = 0;
    	for(int i =0;i<gameList.size();i++) {
    			result += dao.update("GAME_MT.updateGameList", gameList.get(i));
    	}    	
    	return result;
    }
    
    
    
    /**
     * 게임픽 입력
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int insertPick(HashMap<String, String> game) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	int result = 0;
    	result += dao.update("GAME_PICK_MT.insertPick", game);
    	return result;
    }
    
    
    /**
     * 게임픽 목록 입력
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int insertPickList(List<HashMap<String, String>> gameList) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	int result = 0;
    	for(int i =0;i<gameList.size();i++) {
    			result += dao.update("GAME_PICK_MT.insertPickList", gameList.get(i));
    	}    	
    	return result;
    }
    
    /**
     * 게임픽 고유번호
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public String selectMaxPostNo(ParamMap map) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (String)dao.select("GAME_PICK_MT.selectMaxPostNo", map);
    }
    
    
    /**
     * 픽게임 목록
     * 
     * @param user
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<HashMap> selectGamePickList(ParamMap params) throws Exception {
        IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
        return (List<HashMap>)dao.selectList("GAME_PICK_MT.selectPickList", params);        
    }
    
    /**
     * 픽게임 정보
     * 
     * @param user
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public HashMap selectGamePick(ParamMap params) throws Exception {
        IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
        return (HashMap)dao.select("GAME_PICK_MT.selectPick", params);        
    }
    
    
    /**
     * 픽게임 포스팅 목록
     * 
     * @param user
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<HashMap> selectGamePostList() throws Exception {
        IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
        return (List<HashMap>)dao.selectList("GAME_PICK_MT.selectGamePostList");        
    }
}
