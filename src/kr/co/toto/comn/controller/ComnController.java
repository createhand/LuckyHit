package kr.co.toto.comn.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.toto.base.controller.AbstractController;
import kr.co.toto.util.ParamMap;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ComnController extends AbstractController {
	private static final Log log = LogFactory.getLog(ComnController.class);

    /**
     * <pre>
     * 404에러 페이지
     * </pre>
     *
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/error404")
    public String comn404err(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {

        //otp입력팝업.
        ParamMap paramMap = new ParamMap(params);
        return getViewName(request);
    }
    
    
    /**
     * <pre>
     * 500에러 페이지
     * </pre>
     *
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/error500")
    public String comn500err(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {

        //otp입력팝업.
        ParamMap paramMap = new ParamMap(params);
        return getViewName(request);
    }
    
    
    /**
     * <pre>
     * 소스작성
     * </pre>
     *
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ws")
    public String comnWriteSource(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {

        //otp입력팝업.
        ParamMap paramMap = new ParamMap(params);
        return getViewName(request);
    }    
}
