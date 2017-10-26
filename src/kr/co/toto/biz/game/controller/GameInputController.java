/**
 * 토토게임경기 내용을 입력한다
 */
package kr.co.toto.biz.game.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.toto.base.controller.AbstractController;
import kr.co.toto.base.persistence.domain.DomainConst;
import kr.co.toto.base.persistence.domain.GameMt;
import kr.co.toto.base.persistence.domain.TeamMt;
import kr.co.toto.base.service.CommonService;
import kr.co.toto.biz.game.service.GameService;
import kr.co.toto.comn.model.TAData;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.BizUtil;
import kr.co.toto.util.ParamMap;

/**
 * @author seochan
 *
 */
@Controller
public class GameInputController extends AbstractController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
    /**
     * 게임입력
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gameInput", method = RequestMethod.GET)
    public String input001(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
        return getViewName(request);
    }
    
    /**
     * 게임정보수집
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gameCollecting", method = RequestMethod.POST)
    public String gameCollecting(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	CommonService commService = (CommonService) BeanFinder.getBean(CommonService.class);
    	GameService gameService = (GameService) BeanFinder.getBean(GameService.class);
    	int iResult = 0, uResult = 0;
    	ParamMap map = new ParamMap(params);
    	List<String> errMsg = new ArrayList<String>();
    	
    	int insertGameCnt = 0;
    	int insertMatchCnt = 0;
    	
    	int updateGameCnt = 0;
    	int updateMatchCnt = 0;
    	
        try {
        	
        	//gameSeq start parameter        	
        	int gameStSeq = Integer.parseInt(map.getString("gameStSeq"));
        	//gameSeq end parameter        	
        	int gameEnSeq = Integer.parseInt(map.getString("gameEnSeq"));
        	
        	if(gameEnSeq < gameStSeq) {
        		throw new Exception("수집하려는 게임시작순번이 게임종료순번보다 큽니다.");
        	}
        	
        	for(;gameStSeq<=gameEnSeq;gameStSeq++) {
        		
	        	String url = DomainConst.GAME_BASE_URL+gameStSeq;
	
	        	//import gameinfo html
	        	String result = null;
	        	
	        	try {
	        		result = BizUtil.getUrlSource(url, "EUC-KR");
	        	} catch(Exception e) {
	        		System.out.println(e);
	            	errMsg.add(e.toString());
	            	continue;
	        	}
	        	
	        	//string to DOM parsing
	        	Document doc = Jsoup.parse(result);
	        	
	        	if(StringUtils.isBlank(result)) {
	        		errMsg.add(gameStSeq+"회차 게임정보가 없습니다.");
	        		continue;
	        	}
	        	
	        	Elements trList = doc.getElementsByTag("tr");
	        	
	        	//총 발매금액
	        	String rate = doc.getElementsByClass("rate").text();
	        	
	//        	logger.info(doc.toString());
	        	
	        	HashMap<String, String> game = new HashMap<String, String>();
	        	List<HashMap<String, String>> insertRecordList = new ArrayList<HashMap<String, String>>();
	            List<HashMap<String, String>> updateRecordList = new ArrayList<HashMap<String, String>>();
	            
	            //게임 마감 일
	            Element periodTr = trList.get(1);
	            Elements periodTd = periodTr.getElementsByTag("td");
	            String period = periodTd.get(1).text();
	            String gameEndDate = period.replaceAll("[^\\d.]", "");
	            gameEndDate = "20"+gameEndDate.substring(10, 16);
	            
	            //게임 회차
	            Elements gameInfoFormList = doc.getElementsByTag("form");
	            Element gameInfoForm = gameInfoFormList.get(0);
	            Elements gameInfoFormInputList = gameInfoForm.getElementsByTag("input");
	            Element outerRoundInfo = gameInfoFormInputList.get(3);
	            String gameTurn = outerRoundInfo.attr("value");
	            
	        	String gameType = "G011";
	        	String gameName = "축구토토 승무패";
	        	
	        	String gmCd = gameType+StringUtils.leftPad(String.valueOf(gameStSeq), 15, "0");
	        	
	            game.put("gmCd", gmCd);
	            game.put("gmType", gameType);
	            game.put("gmName", gameName);
	            game.put("gmEndDate", gameEndDate);
	            game.put("gmTurn", gameTurn);
	            game.put("gmSeq", String.valueOf(gameStSeq));
	            game.put("gmEnd", "N");
	        	
	        	for(Element tr : trList) {
	        		
	        		Elements tdList = tr.getElementsByTag("td");
	        		Iterator<Element> list = tdList.iterator();
	        		String gameListNo = null, matchDate = null, home = null, away = null;
	        		String winBet = null, drawBet = null, loseBet = null, stadium = null;
	        		HashMap<String, String> tmp = new HashMap<String, String>();
	        		
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
		        			
		        			//일시
		        			if(i == 2) {
		        				matchDate = tdText;
		        			}
		        			
		        			//경기장
		        			if(i == 3) {
		        				stadium = tdText;
		        			}
		        			
		        			//홈팀
		        			if(i == 4) {
		        				home = tdText;
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
		        			
		        			//어웨이팀
		        			if(i == 15) {
		        				away = tdText;
		        			}
		        		}
		        		
		        		logger.info("("+gameEndDate.substring(2, 4)+"년도 "+gameTurn+"회차)");
		        		
		        		/*
		        		 * 데이터 임의 변경
		        		 * 
		        		 * betman 사이트 데이터 오류로
		        		 * 1. 2016060 전북 vs 광주(X) -> 광주 vs 전북(O)
		        		 * 
		        		 * 경기취
		        		 * 2. 2016년 19회차 9번 경기(20160515 맨유vs본머스) 경기 취소
		        		 * (20160518 맨유vs본머스) 경기 변경
		        		 */
		        		
		        		String tmCdH = commService.selectTeamCodeByBetman(home);
		        		String tmCdA = commService.selectTeamCodeByBetman(away);
		        		
		        		
		        		
		        		if(tmCdH == null) {
//		        			errMsg.add("<b>"+home + "</b> 검색된 홈팀이 없습니다 ("+gameEndDate.substring(2, 4)+"년도 "+gameTurn+"회차)");
		        			errMsg.add("<b>"+home + "</b>");
		        			continue;
		        		}
		        		
		        		if(tmCdA == null) {
//		        			errMsg.add("<b>"+away + "</b> 검색된 어웨팀이 없습니다 ("+gameEndDate.substring(2, 4)+"년도 "+gameTurn+"회차)");
		        			errMsg.add("<b>"+away + "</b>");
		        			continue;
		        		}
		        		
	//	        		DateFormat dateFormat = new SimpleDateFormat("YY-MM-DD(u) HH:MM", Locale.KOREA);
	//	        		Date dt = dateFormat.parse(matchDate);
	//	        		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	//	        		matchDate = formatter.format(dt);
		        		
		        		//경기일
		        		matchDate = "20"+matchDate.substring(0, 8).replaceAll("-", "");
		        		
		        		TeamMt homeTeamMt = commService.selectTeam(tmCdH);
		        		String mcCd = homeTeamMt.getLgCd()+tmCdH+matchDate;
		        		
//		        		MatchRecordMt matchRecordMt = commService.selectRecord(mcCd);
		        		//if(matchRecordMt != null && matchRecordMt.getMcEnd().equals("Y")) {
//		        		if(matchRecordMt != null) {
		        			tmp.put("gmRtWin", winBet);
		        			tmp.put("gmRtDraw", drawBet);
		        			tmp.put("gmRtLose", loseBet);
//		        		}
		        		
		        		tmp.put("gmCd", gmCd);
		        		tmp.put("gmListNo", gameListNo);
		        		tmp.put("mcCd", mcCd);
		        		
		        		
		        		HashMap<String, String> tmpMap = new HashMap<String, String>();
	        			tmpMap.put("gmCd", gmCd);
	        			tmpMap.put("gmListNo", gameListNo);
	        			TAData gameListNoObj = commService.selectGameListNo(tmpMap);
	        			
	        			if(gameListNoObj != null) {
	        				updateRecordList.add(tmp);
		        		} else {
		        			insertRecordList.add(tmp);
		        		}
	        		}
	        	}
	        	        	
	        	iResult = gameService.insertGameList(insertRecordList);
	        	uResult= gameService.updateGameList(updateRecordList);
	        	insertMatchCnt += iResult;
	        	updateMatchCnt += uResult;
	        	
	        	GameMt selectGame = commService.selectGame(gmCd);
	        	
	        	if(selectGame == null) {
					insertGameCnt++;
					gameService.insertGame(game);
				} else {
					
					//게임종료여부 체크
					if(StringUtils.equals("Y", map.getString("gmEndChk"))) {
						int cnt = commService.selectEndCnt(gmCd);
						if(cnt == 14) {
							game.put("gmEnd", "Y");
						}
					}
					

					
					updateGameCnt++;
					gameService.updateGame(game);
				}
        	}
        	
        } catch(Exception e) {
        	System.out.println(e);
        	errMsg.add(e.toString());
        }
    	
        HashSet<String> listSet = new HashSet<String>(errMsg);
    	List<String> errMsgList = new ArrayList<String>(listSet);
        
        model.addAttribute("errMsg", errMsgList);
    	model.addAttribute("insertMatchCnt", insertMatchCnt);
    	model.addAttribute("updateMatchCnt", updateMatchCnt);
    	model.addAttribute("insertGameCnt", insertGameCnt);
    	model.addAttribute("updateGameCnt", updateGameCnt);
        return getViewName(request);    	
    }
    
}
