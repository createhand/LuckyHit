/**
 * 토토게임경기 표시한다
 */
package kr.co.toto.biz.game.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.toto.base.controller.AbstractController;
import kr.co.toto.base.persistence.domain.DomainConst;
import kr.co.toto.base.persistence.domain.GameMt;
import kr.co.toto.base.service.CommonService;
import kr.co.toto.biz.game.persistence.domain.GameDetailListDt;
import kr.co.toto.biz.game.persistence.domain.TeamPointDt;
import kr.co.toto.biz.game.service.GameService;
import kr.co.toto.biz.record.service.RecordService;
import kr.co.toto.comn.model.TAData;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.ParamMap;

/**
 * @author seochan
 *
 */
@Controller
public class GameListController extends AbstractController {
	
    /**
     * 게임상세내역
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gameDetailList", method = RequestMethod.GET)
    public String game001(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	RecordService recordService = (RecordService) BeanFinder.getBean(RecordService.class);    	
    	GameService service = (GameService) BeanFinder.getBean(GameService.class);
    	CommonService commService = (CommonService) BeanFinder.getBean(CommonService.class);
    	ParamMap paramMap = new ParamMap(params);
    	
//    	List<GameDetailListDt> returnList = new ArrayList<GameDetailListDt>();
    	List<GameDetailListDt> gameDetailList = new ArrayList<GameDetailListDt>();
    	GameMt gameMt = new GameMt();
    	String gmCd = paramMap.getString("gmCd");
    	
    	//전체 게임목록 조회
    	List<HashMap> gameList = service.selectGameList(new ParamMap(""));
    	
    	if(gmCd.equals("")) {
	    	//최근 게임회차
	    	HashMap latest = (HashMap)service.selectLatestGame();
	    	gmCd = (String)latest.get("gmCd");
	    	paramMap.put("gmCd", gmCd);
    	}
    	
    	//최근 게임에 해당하는 경기목록
    	gameDetailList = service.selectGameDetailList(paramMap);
    	gameMt = commService.selectGame(gmCd);
    	
    	
    	
    	for(int i=0;i<gameDetailList.size();i++) {
    		GameDetailListDt tmp = gameDetailList.get(i);
	    	ParamMap tmCd = new ParamMap(new HashMap());
	    	
	    	/* 팀정보 */
	    	tmp.setHomeTeamInfo(commService.selectTeam(tmp.getHomeTeamCode()));
	    	tmp.setAwayTeamInfo(commService.selectTeam(tmp.getAwayTeamCode()));
	    	
	    	/* 팀 시즌정보 */
	    	String snCd = commService.selectLatestSeasonInfo(tmp.getLgCd());
	    	HashMap<String, String> seasonParams = new HashMap<String, String>();
	    	seasonParams.put("lgCd", tmp.getLgCd());
	    	seasonParams.put("snCd", snCd);
	    	seasonParams.put("tmCd", tmp.getHomeTeamCode());
	    	
	    	//홈팀시즌정보
	    	List<TAData> seasonInfo = commService.selectTeamSeasonInfo(seasonParams);
	    	if(seasonInfo.size() == 1) {
	    		tmp.setHomeTeamSeaonInfo(seasonInfo.get(0));
	    	}
	    	//원정팀시즌정보
	    	seasonParams.put("tmCd", tmp.getAwayTeamCode());
	    	seasonInfo = commService.selectTeamSeasonInfo(seasonParams);
	    	if(seasonInfo.size() == 1) {
	    		tmp.setAwayTeamSeaonInfo(seasonInfo.get(0));
	    	}
	    	
	    	
	    	
	    	//최근 경기 수
	    	tmCd.put("queryCnt", DomainConst.RECORD_LATEST_CNT);
	    	tmCd.put("mcDate", tmp.getMatchDate());
	    	
	    	/* 전적 */
	    	//홈팀 최근 경기 전적조회
	    	tmCd.put("tmCd", tmp.getHomeTeamCode());
	    	tmCd.put("mcDate", tmp.getMatchDate());
	    	tmp.setHomeTeamlatestRecord(recordService.selectLatestRecord(tmCd));
	    	
	    	//어웨이팀 최근 경기 전적조회
	    	tmCd.put("tmCd", tmp.getAwayTeamCode());
	    	tmp.setAwayTeamlatestRecord(recordService.selectLatestRecord(tmCd));
	    	
	    	//홈팀 상대전적 조회
	    	tmCd.put("tmCd", tmp.getHomeTeamCode());
	    	tmCd.put("tmCdA", tmp.getAwayTeamCode());
	    	tmp.setHomeTeamAgainstRecord(recordService.selectLatestRecord(tmCd));
	    	
	    	//어웨이팀 상대전적 조회
	    	tmCd.put("tmCd", tmp.getAwayTeamCode());
	    	tmCd.put("tmCdA", tmp.getHomeTeamCode());
	    	tmp.setAwayTeamAgainstRecord(recordService.selectLatestRecord(tmCd));

	    	
	    	/* 골득실 */
	    	//홈팀 최근경기 골득실
	    	tmCd.put("tmCd", tmp.getHomeTeamCode());
	    	tmCd.remove("tmCdA");
	    	tmp.setHomeTeamlatestScore(recordService.selectLatestScore(tmCd));
	    	
	    	//어웨이팀 최근경기 골득실
	    	tmCd.put("tmCd", tmp.getAwayTeamCode());
	    	tmp.setAwayTeamlatestScore(recordService.selectLatestScore(tmCd));
	    	
	    	//홈팀 상대전적 골득실
	    	tmCd.put("tmCd", tmp.getHomeTeamCode());
	    	tmCd.put("tmCdA", tmp.getAwayTeamCode());
	    	tmp.setHomeTeamAgainstScore(recordService.selectLatestScore(tmCd));
	    	
	    	//어웨이팀 상대전적 골득실
	    	tmCd.put("tmCd", tmp.getAwayTeamCode());
	    	tmCd.put("tmCdA", tmp.getHomeTeamCode());
	    	tmp.setAwayTeamAgainstScore(recordService.selectLatestScore(tmCd));
	    	
	    	
	    	/*
	    	 ************* 홈팀 점수산출(계산식 대입) *************
	    	 */
	    	//홈팀 : 최근 5경기 점수
	    	double homeTeamLatestPoint = service.getPointByList(tmp.getHomeTeamlatestRecord());
	    	
	    	//홈팀 : 최근 상대팀과 5경기 점수
	    	double homeTeamAgainstPoint = service.getPointByList(tmp.getHomeTeamAgainstRecord());
	    	
	    	
	    	
	    	//홈팀 : 최근5경기, 상대5경기 가중치적용
	    	TeamPointDt teamPoint = new TeamPointDt();
	    	teamPoint.setAgainstRecord(tmp.getHomeTeamAgainstRecord());
	    	teamPoint.setLatestRecord(tmp.getHomeTeamlatestRecord());
	    	
	    	paramMap = new ParamMap(new HashMap());	    	
	    	//최근 경기 수
	    	paramMap.put("queryCnt", DomainConst.RECORD_LATEST_CNT);
	    	paramMap.put("mcDate", tmp.getMatchDate());
	    	paramMap.put("homeYn", "Y");
   	
	    	//홈팀 : 홈에서 최근 5경기 
	    	paramMap.put("tmCd", tmp.getHomeTeamCode());
	    	teamPoint.setLatestRecordAtHome(recordService.selectLatestRecord(paramMap));
	    	
	    	//홈팀 : 홈에서 상대팀과 최근 5경기
	    	paramMap.put("tmCdA", tmp.getAwayTeamCode());
	    	teamPoint.setAgainstRecordAtHome(recordService.selectLatestRecord(paramMap));
	    	
	    	double homeTeamPoint = service.getTeamPoint(teamPoint); 
	    	
    	
	    	
	    	/*
	    	 ************* 원정팀 점수산출(계산식 대입) *************
	    	 */
	    		    	
	    	//원정팀 : 최근 5경기
	    	teamPoint.setLatestRecord(tmp.getAwayTeamlatestRecord());
	    	double awayTeamLatestPoint = service.getPointByList(tmp.getAwayTeamlatestRecord());
	    	
	    	//원정팀 : 최근 상대팀과 5경기
	    	teamPoint.setAgainstRecord(tmp.getAwayTeamAgainstRecord());
	    	double awayTeamAgainstPoint = service.getPointByList(tmp.getAwayTeamAgainstRecord());
	    	
	    	
	    	
	    	//원정팀 : 최근5경기, 상대5경기 가중치적용
	    	teamPoint = new TeamPointDt();
	    	teamPoint.setAgainstRecord(tmp.getAwayTeamAgainstRecord());
	    	teamPoint.setLatestRecord(tmp.getAwayTeamlatestRecord());
	    	
	    	paramMap = new ParamMap(new HashMap());
	    	//최근 경기 수
	    	paramMap.put("queryCnt", DomainConst.RECORD_LATEST_CNT);
	    	paramMap.put("mcDate", tmp.getMatchDate());
	    	paramMap.put("homeYn", "Y");
	    	
	    	//원정팀 : 홈에서 최근 5경기
	    	paramMap.put("tmCd", tmp.getAwayTeamCode());
	    	teamPoint.setLatestRecordAtHome(recordService.selectLatestRecord(paramMap));
	    	
	    	//원정팀 : 홈에서 상대팀과 최근 5경기
	    	paramMap.put("tmCdA", tmp.getHomeTeamCode());
	    	teamPoint.setAgainstRecordAtHome(recordService.selectLatestRecord(paramMap));	    	
	    	
	    	double awayTeamPoint = service.getTeamPoint(teamPoint); 


	    	
	    	double latestPoint = homeTeamLatestPoint - awayTeamLatestPoint;
	    	double aginstPoint = homeTeamAgainstPoint - awayTeamAgainstPoint;
	    	
	    	//홈팀, 원정팀 최종점수
	    	double teamSum = homeTeamPoint - awayTeamPoint;
	    	
//	    	System.out.println(i+1+"경기 최근전적점수 = 홈:"+homeTeamLatestPoint+" 원정: "+awayTeamLatestPoint+" = "+latestPoint);
//	    	System.out.println(i+1+"경기 상대전적점수 = 홈:"+homeTeamAgainstPoint+" 원정: "+awayTeamAgainstPoint+" = "+aginstPoint);
//	    	System.out.println(i+1+"원공식 홈:"+homeTeamPoint+" 원정: "+awayTeamPoint+" = "+teamSum);
//	    	System.out.println("======================================");
	    	
	    	//예상픽 결정
	    	if(teamSum > DomainConst.EXPECTED_DRAW_RANGE_FROM) {
	    		tmp.setExpectMatchResultCode("H");
	    		tmp.setExpectMatchResult(commService.selectTeam(tmp.getHomeTeamCode()).getTmName());
	    	} else if(teamSum < DomainConst.EXPECTED_DRAW_RANGE_TO) {
	    		tmp.setExpectMatchResultCode("A");
	    		tmp.setExpectMatchResult(commService.selectTeam(tmp.getAwayTeamCode()).getTmName());
	    	} else { 
	    		tmp.setExpectMatchResultCode(DomainConst.RECORD_DRAW);
	    		tmp.setExpectMatchResult("무승부");
	    	}
	    	
	    	if(latestPoint >= DomainConst.SIMPLE_ABS_RANGE_FROM) {
	    		tmp.setExpectMatchResultCode("H");
	    		tmp.setExpectMatchResult(commService.selectTeam(tmp.getHomeTeamCode()).getTmName());
	    	} else if(latestPoint <= DomainConst.SIMPLE_ABS_RANGE_TO) {
	    		tmp.setExpectMatchResultCode("A");
	    		tmp.setExpectMatchResult(commService.selectTeam(tmp.getAwayTeamCode()).getTmName());
	    	} else if(aginstPoint >= DomainConst.SIMPLE_ABS_RANGE_FROM) {
	    		tmp.setExpectMatchResultCode("H");
	    		tmp.setExpectMatchResult(commService.selectTeam(tmp.getHomeTeamCode()).getTmName());
	    	} else if(aginstPoint <= DomainConst.SIMPLE_ABS_RANGE_TO) {
	    		tmp.setExpectMatchResultCode("A");
	    		tmp.setExpectMatchResult(commService.selectTeam(tmp.getAwayTeamCode()).getTmName());
	    	}
	    	
//	    	returnList.add(tmp);
    	}
    	
    	//게임 URL
    	String url = DomainConst.GAME_BASE_URL+gameMt.getGmSeq();
    	TAData returnMap = service.selectGameBetList(gameDetailList, url);
    	gameDetailList = (List<GameDetailListDt>)returnMap.get("gameDetailList");
    	double totalBetCnt = returnMap.getDouble("totalBetCnt");
    	
    	model.addAttribute("gameDetailList", gameDetailList);
    	model.addAttribute("gameList", gameList);
    	model.addAttribute("gameMt", gameMt);
    	model.addAttribute("gmCd", gmCd);
    	model.addAttribute("totalBetCnt", totalBetCnt);
        return getViewName(request);
    }

}
