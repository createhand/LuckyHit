package kr.co.toto.comn.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.co.toto.base.persistence.IBatisDAO;
import kr.co.toto.base.persistence.IBatisDAOImpl;
import kr.co.toto.util.BeanFinder;

import org.springframework.stereotype.Service;





@Service
public class CodeService {

	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getComnCdList(Map<String,String> params) throws SQLException{
		IBatisDAO iBatisDAO = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);

		return (List<Map<String,String>>)iBatisDAO.selectList("COMN_CD.selectForCodeHelper", params);
	}
	
   
    
}
