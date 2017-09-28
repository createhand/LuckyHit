package kr.co.toto.biz.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.toto.biz.main.service.MainService;
import kr.co.toto.base.controller.AbstractController;
import kr.co.toto.base.persistence.domain.MatchRecordMt;
import kr.co.toto.comn.service.CodeService;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.JsonResponse;
import kr.co.toto.util.PagingList;
import kr.co.toto.util.ParamMap;
import kr.co.toto.lgin.SessionUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @author seochan
 *
 */
@Controller
public class MainPageController extends AbstractController {
    /**
     * 메인페이지
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main001a(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    
        return getViewName(request);
    }
    
    /**
     * 메인페이지(모바일)
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/Mmain", method = RequestMethod.GET)
    public String Mmain001a(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	
    	
        return getViewName(request);
    }    
}
