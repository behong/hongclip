package com.hongclip.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hongclip.domain.users.User;

public class MyBatisTest {

	private static final Logger log = LoggerFactory.getLogger(MyBatisTest.class);

	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void setup() throws IOException {
		String resource = "mybatis-config-test.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void gettingStarted() throws IOException {
		SqlSession session = sqlSessionFactory.openSession();
		// SqlSession JDBC 에 컨넥션 역활
		try {
			User user = session.selectOne("UserMapper.findById", "nayha");
			log.debug("USER: {}", user);
		} finally {
			session.close();
		}
	}

	@Test
	public void insert() throws IOException {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			User user = new User("userId1", "password", "name", "email@dsaf.net");

			session.insert("UserMapper.create", user);

			User actual = session.selectOne("UserMapper.findById", user.getUserId());

			log.debug("actual: {}", actual);
			log.debug("user: {}", user);
			
			assertThat(actual, is(user));

		} finally {
			session.close();
		}

	}

}
