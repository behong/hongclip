package com.hongclip.domain.users;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.AssertFalse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserTest {
	
	private static final Logger log = LoggerFactory.getLogger(UserTest.class);
	
	private static Validator validator;
	
	@BeforeClass
	public static void setup(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void userIdWhenIsEmpty () {
		User user = new User("","password","name","sijm@naver.com");
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		
		for(ConstraintViolation<User> constraintViolation : constraintViolations){
			log.debug("violation error message : {} ", constraintViolation.getMessage());
		}
		
	}
	
	@Test
	public void matchPassword() throws Exception{
		
		String password = "password";
		Authenticate authenticate = new Authenticate("userID",password);
		User user = new User("UserId",password,"name","sijm@naver.com");
		
		assertTrue(user.matchPassword(authenticate));
		
		authenticate = new Authenticate("userID","pass2");
		assertFalse(user.matchPassword(authenticate));
		
	}
	//expected(예상 한) 아래 IllegalArgumentException 발생하면 테스트 성공
	@Test(expected =IllegalArgumentException.class)
	public void updateWhenMismatchUserid() throws Exception{
		
		User user = new User("userId","password","name","sijm@naver.com");
		User updateUser = new User("not_userid","password","upName","sijm@naver.com");
		user.update(updateUser);
		
	}
	
	@Test
	public void update() throws Exception{
		
		User user = new User("userId","password","name","sijm@naver.com");
		User updateUser = new User("userId","password","upName","sijm@naver.com");
		User updatedUser = user.update(updateUser);
		
		assertThat(updatedUser, is(updateUser));
	}

}
