package com.optigra.funnypictures.dao.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.optigra.funnypictures.dao.AbstractDao;
import com.optigra.funnypictures.model.user.User;
import com.optigra.funnypictures.queries.Queries;
import com.optigra.funnypictures.queries.Query;

/**
 * User Dao.
 * 
 * @author oleh.zasadnyy
 *
 */
@Repository("userDao")
public class DefaultUserDao extends AbstractDao<User, Long> implements UserDao {

	@Override
	public Class<User> getEntityClass() {
		return User.class;
	}

	@Override
	public User findByEmail(final String email) {
		Queries query = Queries.GET_USER_BY_EMAIL;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email);
		Query<User> finalQuery = new Query<>(getEntityClass(), query.getQuery(), parameters);
		return getPersistenceManager().executeSingleResultQuery(finalQuery);
	}
	
	
}
