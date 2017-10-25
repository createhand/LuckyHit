/**
 * 토토게임경기 내용을 입력한다
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
import kr.co.toto.biz.game.service.GameService;
import kr.co.toto.comn.model.TAData;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.ParamMap;

/**
 * @author seochan
 *
 */
@Controller
public class GameCalcController extends AbstractController {
	
    /**
     * 게임정보수집
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gameCalc", method = RequestMethod.GET)
    public String input002(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	CommonService commService = (CommonService) BeanFinder.getBean(CommonService.class);
    	GameService gameService = (GameService) BeanFinder.getBean(GameService.class);
    	TAData paramMap = new TAData(params);
    	List<String> errMsg = new ArrayList<String>();
    	List<TAData> selectedGame = null;
    	List<TAData> gameList = null;
    	TAData returnMap = null;
    	
    	GameMt gameInfo = new GameMt();
    	
    	double totalBetCnt = 0;
    	
        try {
        	
        	String gmCd = paramMap.getString("gmCd");
        	if(gmCd.equals("")) {
    	    	//최근 게임회차
        		TAData latest = (TAData)gameService.selectLatestGame();	    	
    	    	gmCd = (String)latest.get("gmCd");
        	}
        	
        	gameInfo = commService.selectGame(gmCd);
        	String url = DomainConst.GAME_BASE_URL+gameInfo.getGmSeq();
        	
        	paramMap.put("gmCd", gmCd);
        	selectedGame = gameService.selectGameDetailList(paramMap);
        	
        	gameList = gameService.selectGameList(new TAData());
        	
        	returnMap = gameService.selectGameBetList(selectedGame, url);
        	selectedGame = (List<TAData>)returnMap.get("gameDetailList");
        	totalBetCnt = returnMap.getDouble("totalBetCnt");
        	
        } catch(Exception e) {
        	System.out.println(e);
        	errMsg.add(e.toString());
        }
    	
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("selectedGame", selectedGame);
        model.addAttribute("gameList", gameList);
    	model.addAttribute("totalBetCnt", totalBetCnt);
    	model.addAttribute("gmInfo", gameInfo);
        return getViewName(request);
    }

}
