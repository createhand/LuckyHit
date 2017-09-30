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
import kr.co.toto.biz.record.service.RecordService;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.BizUtil;
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
    	
    	CommonService commService = (CommonService) BeanFinder.getBean(CommonService.class);
    	GameService gameService = (GameService) BeanFinder.getBean(GameService.class);
    	RecordService recordService = (RecordService) BeanFinder.getBean(RecordService.class);
    	ParamMap map = new ParamMap(params);
    	
    	String gmCd = map.getString("gmCd");
    	String gmPostNo = map.getString("gmPostNo");
    	
    	List<String> errMsg = new ArrayList<String>();    	
    	List<HashMap> selectedGame = new ArrayList<HashMap>();
    	List<HashMap> gameList = new ArrayList<HashMap>();
    	
        try {
        	
        	gameList = gameService.selectPickGameList(map);
        	
        	if(gmCd.equals("")) {
	        	HashMap latest = gameService.selectLatestPick(map);
	        	gmCd = latest.get("gmCd").toString();
	        	map.put("gmCd", gmCd);
        	}
        	
        	selectedGame = recordService.selectHitResult(map);
        	
        } catch(Exception e) {
        	System.out.println(e);
        	errMsg.add(e.toString());
        }
    	
        model.addAttribute("selectedGame", selectedGame);
    	model.addAttribute("gameList", gameList);
    	model.addAttribute("gmCd", gmCd);
        return getViewName(request);
    }

}
