package com.hongclip.web.users;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hongclip.dao.users.UserDao;
import com.hongclip.domain.users.Authenticate;
import com.hongclip.domain.users.User;

@Controller
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	//dao 와 의존관계 설정 
	@Autowired
	private UserDao UserDao;
	
	//직접 설정하는 의존관계
	/*
	private UserDao userDao;
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	*/

	@RequestMapping("/users/form")
	public String form(Model model) {
		model.addAttribute("user", new User());
		return "users/form";
	}
	
	//개인정보 수정
	@RequestMapping("/users/{userId}/form")
	public String updateform(@PathVariable("userId") String userId, Model model) {
		if(userId == null){
			throw new IllegalArgumentException("사용자 아이디가 필요합니다");
		}
		User user = UserDao.findById(userId);
		
		model.addAttribute("user", user);
		return "users/form";
	}
	
	//수정 액션
	@RequestMapping(value = "/users/create", method = RequestMethod.PUT)
	public String update(@Valid User user ,BindingResult bindingResult,HttpSession session) {
		log.debug("USER PUT : {}" , user );
		
		if (bindingResult.hasErrors()){
			log.debug("Binding Result Has error!!!");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for( ObjectError error : errors){
				log.debug("error {},{} : " ,error.getCode(),error.getDefaultMessage());
			}
			// 애러 발생시 회원가입 화면으로 이동
			return "users/form";
		}
		
		//세션 정보가 있는지 체크
		Object temp = session.getAttribute("userId");
		if(temp == null ){
			throw new NullPointerException();
		}
		
		//현재 아이디와 업데이트 아이디가 동일한지 비교
		/*
		String userId = (String) temp;
		if(!user.matchUserId(userId)){
			throw new NullPointerException();
		}
		*/
		
		UserDao.update(user);
		log.debug("Update DB  :  {}" , UserDao.findById(user.getUserId()));
		return "redirect:/";
	}
	
	

	@RequestMapping(value = "/users/create", method = RequestMethod.POST)
	public String create(@Valid User user ,BindingResult bindingResult,Model model) {
		log.debug(" /users/create USER : {}" , user );
		
		if (bindingResult.hasErrors()){
			log.debug("Binding Result Has error!!!");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for( ObjectError error : errors){
				log.debug("error {},{} : " ,error.getCode(),error.getDefaultMessage());
			}
			// 애러 발생시 회원가입 화면으로 이동
			return "users/form";
		}
		// DB 아이디 중복 체크
		if(  user.getUserId().equals(UserDao.findById(user.getUserId()).getUserId()) ){
			model.addAttribute("errorMessage", "DB 아이디 중복 입니다");
			return "users/form";
		}
		UserDao.create(user);
		log.debug("DB  :  {}" , UserDao.findById(user.getUserId()));
		
		return "redirect:/";
	}
	
	@RequestMapping("/users/login/form")
	public String loginform(Model model) {
		model.addAttribute("authenticate", new Authenticate());
		return "users/login";
	}
	
	//로그인 처리
	@RequestMapping("/users/login")
	public String login(@Valid Authenticate authenticate ,BindingResult bindingResult,Model model ,HttpSession session) {
		
		if(bindingResult.hasErrors()){
			return "users/login";
		}
	
		User user = UserDao.findById(authenticate.getUserId());
		
		if( user == null ){
			model.addAttribute("errorMessage", "존재하지 않는 사용자 입니다");
			return "users/login";
		}
		
		/*		
		if(!user.getPassword().equals(authenticate.getPassword())){
			// 패스워드가 틀립니다. 
		}
		*/
		if(!user.matchPassword(authenticate)){
			model.addAttribute("errorMessage", "비밀번호가 틀립니다.");
			return "users/login";
		}
		log.debug(" ***** user {} : " ,user.toString());
		
		// 세션에 사용자 정보 저장
		session.setAttribute("userId", user.getUserId());
		
		return "redirect:/";
	}
	
	//로그아웃 처리
	@RequestMapping("/users/logout")
	public String logout(@Valid Authenticate authenticate ,BindingResult bindingResult,Model model ,HttpSession session) {
		
		session.removeAttribute("userId");
	
		return "redirect:/";
	}

}
