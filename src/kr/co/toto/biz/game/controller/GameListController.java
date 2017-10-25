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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

/**
 * @author seochan
 *
 */
@Controller
public class GameListController extends AbstractController {
	
	protected final Log logger = LogFactory.getLog(getClass());
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
    	TAData paramMap = new TAData(params);
    	
    	List<TAData> gameDetailList = new ArrayList<TAData>();
    	GameMt gameMt = new GameMt();
    	String gmCd = paramMap.getString("gmCd");
    	
    	//전체 게임목록 조회
    	List<TAData> gameList = service.selectGameList(new TAData());
    	
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
    		TAData gameDetailInfo = gameDetailList.get(i);
	    	
    		/*####################### 팀 시즌정보 #######################*/
	    	String snCd = commService.selectLatestSeasonInfo(gameDetailInfo.getString("lgCd"));
	    	HashMap<String, String> seasonParams = new HashMap<String, String>();
	    	seasonParams.put("lgCd", gameDetailInfo.getString("lgCd"));
	    	seasonParams.put("snCd", snCd);
	    	
	    	//홈팀시즌정보
	    	List<TAData> seasonInfoList = commService.selectTeamSeasonInfo(seasonParams);
	    	
	    	for(TAData teamSeasonInfo : seasonInfoList) {
	    		//홈팀 시즌정보
	    		if(StringUtils.equals(teamSeasonInfo.getString("TM_CD"), gameDetailInfo.getString("homeTeamCode"))) {
	    			gameDetailInfo.set("homeTeamSeasonInfo", teamSeasonInfo);
	    		}
	    		//어웨이팀 시즌정보
	    		if(StringUtils.equals(teamSeasonInfo.getString("TM_CD"), gameDetailInfo.getString("awayTeamCode"))) {
	    			gameDetailInfo.set("awayTeamSeasonInfo", teamSeasonInfo);
	    		}
	    	}
	    	
	    	/*####################### 이변 경기 목록 #######################*/
//	    	gameDetailInfo.set("homeTeamAmzaingList", service.selectAmazingList(gameDetailInfo.getString("homeTeamCode")));
//	    	gameDetailInfo.set("awayTeamAmzaingList", service.selectAmazingList(gameDetailInfo.getString("awayTeamCode")));
	    	
	    	
	    	/*####################### 최근 경기 조회 #######################*/
	    	TAData recordParam = new TAData();
	    	//최근 경기 수(12경기)
	    	recordParam.put("queryCnt", DomainConst.RECORD_LATEST_CNT);
	    	recordParam.put("mcDate", gameDetailInfo.getString("matchDate"));
	    	
	    	/***************** 홈팀 *****************/
	    	recordParam.set("tmCd", gameDetailInfo.getString("homeTeamCode"));
	    	List<TAData> homeLatestRecordList = recordService.selectLatestRecordList(recordParam);
	    	
	    	/***************** 어웨이팀 *****************/
	    	recordParam.set("tmCd", gameDetailInfo.getString("awayTeamCode"));
	    	List<TAData> awayLatestRecordList = recordService.selectLatestRecordList(recordParam);
	    	
	    	/*####################### 최근 상대 경기조회 #######################*/
	    	/***************** 홈, 어웨이 *****************/
	    	recordParam.set("tmCd", gameDetailInfo.getString("homeTeamCode"));
	    	recordParam.set("tmCdA", gameDetailInfo.getString("awayTeamCode"));
	    	List<TAData> againstLatestRecordList = recordService.selectLatestRecordList(recordParam);
	    	
	    	//match list set
	    	gameDetailInfo.set("homeTeamlatestRecordList", homeLatestRecordList);
	    	gameDetailInfo.set("awayTeamlatestRecordList", awayLatestRecordList);
	    	gameDetailInfo.set("againstRecordList", againstLatestRecordList);

	    	
	    	
	    	/*####################### 계산식 적용 #######################*/
	    	/*
	    	 ************* 홈팀 점수산출(계산식 대입) *************
	    	 */
	    	//최근 경기(6경기)
	    	TAData homeTeamResult = service.getResultStr(gameDetailInfo.getString("homeTeamCode"), 0, homeLatestRecordList, 6);
	    	//최근 홈경기(6경기)
	    	TAData homeTeamResultAtHome = service.getResultStr(gameDetailInfo.getString("homeTeamCode"), 1, homeLatestRecordList, 6);
	    	//최근 상대경기(6경기)
	    	TAData homeAgainstResult = service.getResultStr(gameDetailInfo.getString("homeTeamCode"), 0, againstLatestRecordList, 6);
	    	//최근 홈에서 상대경기(6경기)
	    	TAData homeAgainstResultAtHome = service.getResultStr(gameDetailInfo.getString("homeTeamCode"), 1, againstLatestRecordList, 6);
	    	
	    	//홈팀 최근 경기 요약
	    	TAData teamSummary = new TAData();
	    	teamSummary.set("homeTeamResult", homeTeamResult);
	    	teamSummary.set("homeTeamResultAtHome", homeTeamResultAtHome);
	    	teamSummary.set("homeAgainstResult", homeAgainstResult);
	    	teamSummary.set("homeAgainstResultAtHome", homeAgainstResultAtHome);
	    	gameDetailInfo.set("homeTeamlatestInfo", teamSummary);
	    	
	    	//최근 6경기 점수산출
	    	double homeTeamLatestPoint = service.getPointByResultList(homeTeamResult.getString("resultStr"));
	    	
	    	//최근 상대팀과 6경기 점수산출
	    	double homeTeamAgainstPoint = service.getPointByResultList(homeAgainstResult.getString("resultStr"));
	    	
	    	
	    	//최근5경기, 상대5경기 가중치적용
	    	TAData teamPoint = new TAData();
	    	//최근 경기
	    	teamPoint.set("latestResult", homeTeamResult.getString("resultStr"));
	    	//최근 홈경기
	    	teamPoint.set("homeLatestResult", homeTeamResultAtHome.getString("resultStr"));
	    	//최근 상대경기
	    	teamPoint.set("againstLatestResult", homeAgainstResult.getString("resultStr"));
	    	//최근 홈에서 상대경기
	    	teamPoint.set("homeAgainstLatestResult", homeAgainstResultAtHome.getString("resultStr"));
	    	
	    	double homeTeamPoint = service.getTeamPoint(teamPoint);
	    	
	    	
	    	/*
	    	 ************* 원정팀 점수산출(계산식 대입) *************
	    	 */
	    	//최근 경기(6경기)
	    	TAData awayTeamResult = service.getResultStr(gameDetailInfo.getString("awayTeamCode"), 0, awayLatestRecordList, 6);
	    	//최근 홈경기(6경기)
	    	TAData awayTeamResultAtHome = service.getResultStr(gameDetailInfo.getString("awayTeamCode"), 1, awayLatestRecordList, 6);
	    	//최근 상대경기(6경기)
	    	TAData awayAgainstResult = service.getResultStr(gameDetailInfo.getString("awayTeamCode"), 0, againstLatestRecordList, 6);
	    	//최근 홈에서 상대경기(6경기)
	    	TAData awayAgainstResultAtHome = service.getResultStr(gameDetailInfo.getString("awayTeamCode"), 1, againstLatestRecordList, 6);
	    	
	    	//어웨이팀 최근 경기 요약
	    	teamSummary = new TAData();
	    	teamSummary.set("awayTeamResult", awayTeamResult);
	    	teamSummary.set("awayTeamResultAtHome", awayTeamResultAtHome);
	    	teamSummary.set("awayAgainstResult", awayAgainstResult);
	    	teamSummary.set("awayAgainstResultAtHome", awayAgainstResultAtHome);
	    	gameDetailInfo.set("awayTeamlatestInfo", teamSummary);
	    	
	    	//최근 6경기 점수산출
	    	double awayTeamLatestPoint = service.getPointByResultList(awayTeamResult.getString("resultStr"));
	    	
	    	//최근 상대팀과 6경기 점수산출
	    	double awayTeamAgainstPoint = service.getPointByResultList(awayAgainstResult.getString("resultStr"));
	    	
	    	
	    	//최근5경기, 상대5경기 가중치적용
	    	teamPoint = new TAData();
	    	//최근 경기
	    	teamPoint.set("latestResult", awayTeamResult.getString("resultStr"));
	    	//최근 홈경기
	    	teamPoint.set("homeLatestResult", awayTeamResultAtHome.getString("resultStr"));
	    	//최근 상대경기
	    	teamPoint.set("againstLatestResult", awayAgainstResult.getString("resultStr"));
	    	//최근 홈에서 상대경기
	    	teamPoint.set("homeAgainstLatestResult", awayAgainstResultAtHome.getString("resultStr"));
	    	
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
	    		gameDetailInfo.set("expectMatchResultCode", DomainConst.RECORD_WIN);
	    		gameDetailInfo.set("expectMatchResult", commService.selectTeam(gameDetailInfo.getString("homeTeamCode")).getTmName());
	    	} else if(teamSum < DomainConst.EXPECTED_DRAW_RANGE_TO) {
	    		gameDetailInfo.set("expectMatchResultCode", DomainConst.RECORD_LOSE);
	    		gameDetailInfo.set("expectMatchResult", commService.selectTeam(gameDetailInfo.getString("awayTeamCode")).getTmName());
	    	} else { 
	    		gameDetailInfo.set("expectMatchResultCode", DomainConst.RECORD_DRAW);
	    		gameDetailInfo.set("expectMatchResult", "무승부");
	    	}
	    	
	    	if(latestPoint >= DomainConst.SIMPLE_ABS_RANGE_FROM) {
	    		gameDetailInfo.set("expectMatchResultCode", DomainConst.RECORD_WIN);
	    		gameDetailInfo.set("expectMatchResult", commService.selectTeam(gameDetailInfo.getString("homeTeamCode")).getTmName());
	    	} else if(latestPoint <= DomainConst.SIMPLE_ABS_RANGE_TO) {
	    		gameDetailInfo.set("expectMatchResultCode", DomainConst.RECORD_LOSE);
	    		gameDetailInfo.set("expectMatchResult", commService.selectTeam(gameDetailInfo.getString("awayTeamCode")).getTmName());
	    	} else if(aginstPoint >= DomainConst.SIMPLE_ABS_RANGE_FROM) {
	    		gameDetailInfo.set("expectMatchResultCode", DomainConst.RECORD_WIN);
	    		gameDetailInfo.set("expectMatchResult", commService.selectTeam(gameDetailInfo.getString("homeTeamCode")).getTmName());
	    	} else if(aginstPoint <= DomainConst.SIMPLE_ABS_RANGE_TO) {
	    		gameDetailInfo.set("expectMatchResultCode", DomainConst.RECORD_LOSE);
	    		gameDetailInfo.set("expectMatchResult", commService.selectTeam(gameDetailInfo.getString("awayTeamCode")).getTmName());
	    	}
	    	
    	}
    	
    	//게임 URL
    	String url = DomainConst.GAME_BASE_URL+gameMt.getGmSeq();
    	TAData returnMap = service.selectGameBetList(gameDetailList, url);
    	gameDetailList = (List<TAData>)returnMap.get("gameDetailList");
    	double totalBetCnt = returnMap.getDouble("totalBetCnt");
    	
    	model.addAttribute("gameDetailList", gameDetailList);
    	model.addAttribute("gameList", gameList);
    	model.addAttribute("gameMt", gameMt);
    	model.addAttribute("gmCd", gmCd);
    	model.addAttribute("totalBetCnt", totalBetCnt);
        return getViewName(request);
    }

}
