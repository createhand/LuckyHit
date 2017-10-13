/**
 * 토토게임경기 내용을 입력한다
 */
package kr.co.toto.biz.game.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
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
import kr.co.toto.base.persistence.domain.MatchRecordMt;
import kr.co.toto.base.persistence.domain.TeamMt;
import kr.co.toto.base.service.CommonService;
import kr.co.toto.biz.game.persistence.domain.GameDetailListDt;
import kr.co.toto.biz.game.service.GameService;
import kr.co.toto.biz.record.controller.RecordInputController;
import kr.co.toto.biz.record.service.RecordService;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.BizUtil;
import kr.co.toto.util.DateUtil;
import kr.co.toto.util.ParamMap;

/**
 * @author seochan
 *
 */
@Controller
public class GameResultController extends AbstractController {
	
    /**
     * 게임정보수집
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gameResult", method = RequestMethod.GET)
    public String input002(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	GameService gameService = (GameService) BeanFinder.getBean(GameService.class);
    	RecordService recordService = (RecordService) BeanFinder.getBean(RecordService.class);
    	ParamMap map = new ParamMap(params);
    	
    	String gmCd = map.getString("gmCd");
    	String gmPostNo = map.getString("gmPostNo");
    	
    	List<String> errMsg = new ArrayList<String>();    	
    	List<HashMap> selectedGame = new ArrayList<HashMap>();
    	List<HashMap> gameList = new ArrayList<HashMap>();
    	HashMap selectGameInfo = new HashMap();
    	
        try {
        	
        	gameList = gameService.selectPickGameList(map);
        	
        	if(gmCd.equals("")) {
	        	HashMap latest = gameService.selectLatestPick(map);
	        	gmCd = latest.get("gmCd").toString();
	        	map.put("gmCd", gmCd);
        	}
        	
        	selectedGame = recordService.selectHitResult(map);
        	
        	if(StringUtils.isBlank(gmPostNo)) {
        		gmPostNo = String.valueOf((Integer)selectedGame.get(0).get("gmPostNo"));
        	}
        	
        	for(HashMap pickInfo : gameList) {
        		if(StringUtils.equals(gmPostNo, String.valueOf((Integer)pickInfo.get("gmPostNo"))) &&
        				StringUtils.equals(gmCd, String.valueOf((String)pickInfo.get("gmCd")))) {
        			selectGameInfo = pickInfo;
        			break;
        		}
        	}
        	
        	//종료된 경기 체크
        	int endCnt = 0;
        	
        	//종료경기 수집처리(현시각 기준으로 종료된 경기 카운팅 후 수집)
        	for(HashMap matchInfo : selectedGame) {
        		//경기 종료여부 체크
        		if(StringUtils.equals(DomainConst.NO, matchInfo.get("matchEnd").toString())) {
        			
        			String mcDate = matchInfo.get("matchDate").toString();
        			String mcTime = matchInfo.get("matchTime").toString();
        			
        			if(StringUtils.isBlank(mcDate) || StringUtils.isBlank(mcTime)) {
        				continue;
        			}
        			
        			String nowTime = DateUtil.getCurrentDateTime().substring(0, 12);
        			int aftTime = DateUtil.getDayDiff(mcDate+mcTime, nowTime, "yyyyMMddHHmm");
        			
        			//경기시작 후 120분이상 경과된 경기
        			if(aftTime > 120) {
        				endCnt++;
        			}
        		}
        	}
        	
        	if(endCnt > 0) {
	        	//종료된 경기 수집일자 범위
	        	String endMatchStDt = selectGameInfo.get("gmEndDate").toString();
	        	
	        	//경기결과 수집
	        	params.put("league", "all");
	        	params.put("stDt", endMatchStDt);
	        	params.put("enDt", DateUtil.getCurrentDate());
	        	RecordInputController rc = new RecordInputController();
	        	rc.record002(request, response, model, params);
	        	
	        	//종료결과 수집 후 재조회
	        	selectedGame = recordService.selectHitResult(map);
        	}
        	
        } catch(Exception e) {
        	System.out.println(e);
        	errMsg.add(e.toString());
        }
    	
    	model.addAttribute("selectGameInfo", selectGameInfo);
        model.addAttribute("selectedGame", selectedGame);
        model.addAttribute("gmPostNo", gmPostNo);
    	model.addAttribute("gameList", gameList);
    	model.addAttribute("gmCd", gmCd);
        return getViewName(request);
    }

}
