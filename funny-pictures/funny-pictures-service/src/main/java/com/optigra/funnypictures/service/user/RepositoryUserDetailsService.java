package com.optigra.funnypictures.service.user;

import com.optigra.funnypictures.dao.user.UserDao;
import com.optigra.funnypictures.model.user.DefaultUserDetails;
import com.optigra.funnypictures.model.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

/**
 * @author oleh.zasadnyy
 */
public class RepositoryUserDetailsService implements UserDetailsService {
	@Resource(name = "userDao")
	private UserDao userDao;

    /**
     * Constructor.
     * @param userDao injected dao for persistence operation
     */
	public RepositoryUserDetailsService(final UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Find user by given username (email).
     * @param username username of User
     * @return UserDetails
     */
    @Override	
    public UserDetails loadUserByUsername(final String username) {
        User user = userDao.findByEmail(username);
 
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
 
        return DefaultUserDetails.getBuilder()
                .id(user.getId())
                .username(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .role(user.getRole())
                .socialSignInProvider(user.getSignInProvider())
                .createDate(user.getCreateDate())
                .updateDate(user.getUpdateDate())
                .build();
    }
}
