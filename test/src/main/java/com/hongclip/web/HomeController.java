package com.hongclip.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongclip.domain.users.Filelist;
import com.hongclip.domain.users.User;

@Controller
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping("/")
	public String home(){
		log.debug("logback Setting Success!!!!" );
		return "home";
	}
	
	@RequestMapping(value ="/json" ,produces = "application/json")
	public ArrayList<Filelist> json( ){
		
		User user = new User();
		
		user.setUserId("test1");
		user.setEmail("test@email.com");
		user.setName("테스트");
		
		ArrayList<Filelist> filelists = new ArrayList<Filelist>();
		Filelist filelist = new Filelist();
		
		filelist.setFileSeq(1);
		filelist.setFileName("홍성인");
		
		filelists.add(filelist);
		
		filelist.setFileSeq(2);
		filelist.setFileName("홍지유");
		
		filelists.add(filelist);
		
		log.debug("json" + filelists );
		
		return filelists;
	}
	
	@RequestMapping(value="/jsonTest") 
	public String doC(Model model){
	    // model은 view 단으로 데이터를 넘기기 위한 Map형 자료구조이다
	    String msg = "9999년99월99일";
	    model.addAttribute( "serverTime", msg );
	 
	    ProductVO product = new ProductVO( "Sample Product C", 19999 );
	    model.addAttribute( "product", product );
	    
	    HashMap<String, Object> ppp = new HashMap<String, Object>();
	    ppp.put("name", "MapDATA2" );
	    ppp.put("age", 1345 );
	    
	    model.addAttribute( "ppp", ppp );
	    // views 아래의 'home.jsp'를 호출
	    return "json";	    
	}
	
	@RequestMapping(value="/jsonTest2" ,method = RequestMethod.GET)
	public @ResponseBody Object doE(){
	    log.info( "doE called......................." );
	 
	    // Map 데이터타입으로 출력 : 생성은 HashMap으로 하고
	    HashMap<String, Object> ppp = new HashMap<String, Object>();
	    ppp.put("name", "Map DATA2" );
	    ppp.put("age", 1345 );
	    //product.put("memo", new String("Rock, Pop") );
	 
	    return ppp;
	}	

	

}
