package com.hongclip.dao.users;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.hongclip.domain.users.User;

public class UserDao extends JdbcDaoSupport {

	@PostConstruct
	public void initialize() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("hongclip.sql"));
		DatabasePopulatorUtils.execute(populator, getDataSource());
		logger.info("database initialized sucess!");
	}

	public User findById(String userId) {

		String sql = "select * from USERS where userId = ?";

		RowMapper<User> rowMapper = new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
						rs.getString("email"));

			}
		};

		try {
			return getJdbcTemplate().queryForObject(sql, rowMapper, userId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void create(User user) {
		String sql = "insert into USERS values (?,?,?,?)";
		getJdbcTemplate().update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
		// return
		// getJdbcTemplate().update(sql,user.getUserId(),user.getPassword(),user.getName(),user.getEmail())

	}

	public void update(User user) {

		String sql = "update USERS set password = ?,name = ?,email = ?  where userid =  ?";
		getJdbcTemplate().update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());

	}

}
