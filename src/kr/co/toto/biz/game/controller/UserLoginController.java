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
public class UserLoginController extends AbstractController {
	
    /**
     * 로그인 페이지
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userLogin", method = RequestMethod.GET)
    public String userLogin(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
        return getViewName(request);
    }

    /**
     * 가입 페이지
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userRegist", method = RequestMethod.GET)
    public String userRegist(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
        return getViewName(request);
    }
}
