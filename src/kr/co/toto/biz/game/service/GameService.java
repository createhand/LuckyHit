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
import kr.co.toto.base.service.CommonService;
import kr.co.toto.biz.game.controller.GameInputController;
import kr.co.toto.biz.game.persistence.domain.GameDetailListDt;
import kr.co.toto.biz.game.persistence.domain.TeamPointDt;
import kr.co.toto.comn.model.TAData;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.BizUtil;
import kr.co.toto.util.DateUtil;
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
     * 게임 베팅률 수집
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public void updateGameBetList(TAData gmInfo, String url) throws Exception {
    	
    	List<HashMap<String, String>> matchList = new ArrayList<HashMap<String, String>>();
    	
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
    		
    		if(StringUtils.isNotBlank(gameListNo)) {
    			HashMap<String, String> tmp = new HashMap<String, String>();
    			tmp.put("gmCd", gmInfo.getString("gameCode"));
    			tmp.put("gmListNo", gameListNo);
    			tmp.put("gmRtWin", winBet);
    			tmp.put("gmRtDraw", drawBet);
    			tmp.put("gmRtLose", loseBet);
    			matchList.add(tmp);
    		}
    	}
    	
    	updateGameList(matchList);
    	
    }
    /**
     * 전체 게임목록을 조회한다
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public TAData selectGameBetList(List<TAData> gameDetailList, String url) throws Exception {
    	
    	CommonService commService = (CommonService) BeanFinder.getBean(CommonService.class);
    	
    	TAData returnMap = new TAData();
    	TAData gameInfo = gameDetailList.get(0);
    	
    	//DB에서 베팅률 조회
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("gmCd", gameInfo.getString("gameCode"));
    	List<TAData> list = commService.selectGameAllListNo(map);
    	
    	int minDiff = DateUtil.getDayDiff(list.get(0).getString("latestUpdate"), DateUtil.getToday("yyyyMMddHHmm"), "yyyyMMddHHmm");
    	int dayDiff = DateUtil.getDayDiff(DateUtil.getCurrentDate(), gameInfo.getString("gameEndDate"), "yyyyMMdd");
    	
    	//베팅 수집은 10분에 한번
    	if(dayDiff >= 0 && minDiff >= 10) {
    		updateGameBetList(gameInfo, url);
    		list = commService.selectGameAllListNo(map);
    	}
    	
    	for(TAData info : list) {
    		
    		String gmCd = info.getString("gmCd");
    		String gmListNo = info.getString("gmListNo");
    		
    		for(TAData game : gameDetailList){
    			String detailGmCd = game.getString("gameCode");
    			String detailGmListNo = game.getString("gameListNo");
    			
    			//베팅률 set
    			if(StringUtils.equals(gmCd, detailGmCd)
    					&& StringUtils.equals(gmListNo, detailGmListNo)) {
    				game.set("winBetCnt", info.getInt("gmRtWin"));
    				game.set("drawBetCnt", info.getInt("gmRtDraw"));
    				game.set("loseBetCnt", info.getInt("gmRtLose"));
    				
    				break;
    			}
    		}
    	}
    	
    	gameInfo = gameDetailList.get(0);
    	double totalBetCnt = gameInfo.getInt("winBetCnt") + gameInfo.getInt("drawBetCnt") + gameInfo.getInt("loseBetCnt");
    	
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
    public List<TAData> selectGameList(TAData map) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (List<TAData>)dao.selectList("GAME.selectGameList", map);
    }
    
    /**
     * 픽이 있는 게임 목록 조회한다
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public List<TAData> selectPickGameList(TAData map) throws Exception {
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
    public TAData selectPickGameInfo(TAData map) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (TAData)dao.select("GAME.selectPickGameList", map);
    }
    
    /**
     * 픽이 있는 게임 목록 카운트
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int selectPickGameListCount(TAData map) throws Exception {
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
    public List<TAData> selectGameDetailList(TAData params) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (List<TAData>)dao.selectList("GAME.select", params);
    }
    
    
    /**
     * 최근 게임코드, 회차, 종료일자
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public TAData selectLatestGame() throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (TAData)dao.select("GAME.latestGame");
    }
    
    /**
     * 최근 픽한 게임코드, 회차, 종료일자
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public TAData selectLatestPick(TAData params) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (TAData)dao.select("GAME.latestPick", params);
    }
    
    /**
     * 팀 점수(계산식)
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public double getTeamPoint(TAData objTeamPoint) throws Exception {
    	
    	double latestPoint = 0, latestPointAtHome = 0, againstPoint = 0, againstPointAtHome = 0, teamPoint = 0;
    	
    	/****************** 최근전적 ******************/
    	//최근전적점수 적용
    	String[] latestRecord = objTeamPoint.getString("latestResult").split("");
    	
    	for(int i=0;i<latestRecord.length;i++) {
    		if(latestRecord[i].equals(DomainConst.RECORD_WIN)) latestPoint += DomainConst.LATEST_WIN_POINT;
    		else if(latestRecord[i].equals(DomainConst.RECORD_LOSE)) latestPoint += DomainConst.LATEST_LOSE_POINT;
    		else latestPoint += DomainConst.LATEST_DRAW_POINT;    		
    	}
    	

    	//홈에서 최근전적점수 적용
    	String[] latestRecordAtHome = objTeamPoint.getString("homeLatestResult").split("");
    	for(int i=0;i<latestRecordAtHome.length;i++) {
    		if(latestRecordAtHome[i].equals(DomainConst.RECORD_WIN)) latestPointAtHome += DomainConst.LATEST_WIN_POINT;
    		else if(latestRecordAtHome[i].equals(DomainConst.RECORD_LOSE)) latestPointAtHome += DomainConst.LATEST_LOSE_POINT;
    		else latestPointAtHome += DomainConst.LATEST_DRAW_POINT;    		
    	}

    	
    	//최근전적점수 가중치 적용
    	latestPoint = (latestPoint + latestPointAtHome) * DomainConst.LATEST_WEIGHTED_VALUE;
    	
    	
    	/****************** 상대전적 ******************/
    	//최근상대전적점수 적용
    	String[] againstRecord = objTeamPoint.getString("againstLatestResult").split("");
    	for(int i=0;i<againstRecord.length;i++) {
    		if(againstRecord[i].equals(DomainConst.RECORD_WIN)) againstPoint += DomainConst.AGAINST_WIN_POINT;
    		else if(againstRecord[i].equals(DomainConst.RECORD_LOSE)) againstPoint += DomainConst.AGAINST_LOSE_POINT;
    		else againstPoint += DomainConst.AGAINST_DRAW_POINT;    		
    	}
    	
    	//홈에서 최근상대전적점수 적용
    	String[] againstRecordAtHome = objTeamPoint.getString("homeAgainstLatestResult").split("");
    	for(int i=0;i<againstRecordAtHome.length;i++) {
    		if(againstRecordAtHome[i].equals(DomainConst.RECORD_WIN)) againstPointAtHome += DomainConst.AGAINST_WIN_POINT;
    		else if(againstRecordAtHome[i].equals(DomainConst.RECORD_LOSE)) againstPointAtHome += DomainConst.AGAINST_LOSE_POINT;
    		else againstPointAtHome += DomainConst.AGAINST_DRAW_POINT;    		
    	}
    	
    	//최근상대전적점수 가중치 적용
    	againstPoint = (againstPoint + againstPointAtHome) * DomainConst.AGAINST_WEIGHTED_VALUE;
    	
    	
    	/****************** 합산 ******************/
    	//팀최종점수
    	teamPoint = latestPoint + againstPoint;
    	
    	return teamPoint;
    }

    //경기목록에서 경기결과요약 리턴
    /*returnType
     *0 : 홈, 어웨이 모두 / 1 : 홈팀일때만 / 2: 어웨이 팀일때만
     */
    public TAData getResultStr(String tmCd, int returnType, List<TAData> matchList, int matchCnt) {
    	
    	//선택된 경기 목록
    	List<TAData> selectMatchList = new ArrayList<TAData>();
    	//득점
    	int getScore = 0;
    	//실점
    	int lostScore = 0;
    	//승무패 결과
    	String resultStr = "";
    	//경기 수
    	int cnt = 1;
    	
    	TAData result = new TAData();
    	
    	for(TAData matchInfo : matchList) {
    		
    		if(matchCnt < cnt) break;
    		
    		if(StringUtils.equals(matchInfo.getString("tmCdH"), tmCd)) {
    			if(returnType == 0 || returnType == 1) {
	    			//홈팀일때 경기결과
	    			resultStr += matchInfo.getString("mcResult");
	    			getScore += matchInfo.getInt("scoreH");
	    			lostScore += matchInfo.getInt("scoreA");
	    			
	    			selectMatchList.add(matchInfo);
	    			cnt++;
    			}
    		} else if(StringUtils.equals(matchInfo.getString("tmCdA"), tmCd)) {
    			if(returnType == 0 || returnType == 2) {
	    			//원정팀일때 경기결과
    				
	    			if(StringUtils.equals(matchInfo.getString("mcResult"), DomainConst.RECORD_WIN)) {
	    				resultStr += DomainConst.RECORD_LOSE;
	    			} else if(StringUtils.equals(matchInfo.getString("mcResult"), DomainConst.RECORD_LOSE)) {
	    				resultStr += DomainConst.RECORD_WIN;
	    			} else {
	    				resultStr += DomainConst.RECORD_DRAW;
	    			}
	    			
	    			getScore += matchInfo.getInt("scoreA");
	    			lostScore += matchInfo.getInt("scoreH");
	    			cnt++;
	    			
	    			selectMatchList.add(matchInfo);
    			}
    		}
    	}
    	
    	result.set("matchList", selectMatchList);
    	result.set("resultStr", resultStr);
    	result.set("getScore", getScore);
    	result.set("lostScore", lostScore);
    	
    	return result;
    }
    
    /**
     * 승,무,패 목록에서 점수산출
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public double getPointByResultList(String resultStr) throws Exception {
    	double listPoint = 0;
    	String[] list = resultStr.split("");
    	for(int i=0;i<list.length;i++) {
    		if(list[i].equals(DomainConst.RECORD_WIN)) listPoint += DomainConst.LATEST_WIN_POINT;
    		else if(list[i].equals(DomainConst.RECORD_LOSE)) listPoint += DomainConst.LATEST_LOSE_POINT;
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
    public String selectMaxPostNo(TAData map) throws Exception {
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
    public List<HashMap> selectGamePickList(TAData params) throws Exception {
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
    public HashMap selectGamePick(TAData params) throws Exception {
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
