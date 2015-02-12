package com.optigra.funnypictures.dao.user;

import com.optigra.funnypictures.dao.Dao;
import com.optigra.funnypictures.model.user.User;


public interface UserDao extends Dao<User, Long> {
	
	/**
	 * Return User Entity by given email
	 * @param email
	 * @return User Entity
	 */
	public User findByEmail(String email);
}
