package com.optigra.funnypictures.service.user;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.optigra.funnypictures.dao.user.UserDao;
import com.optigra.funnypictures.model.user.DefaultUserDetails;
import com.optigra.funnypictures.model.user.User;

public class RepositoryUserDetailsService implements UserDetailsService {
	@Resource(name = "userDao")
	private UserDao userDao;
 
    @Override	
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByEmail(username);
 
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
 
        DefaultUserDetails principal = DefaultUserDetails.getBuilder()
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
 
        return principal;
    }
}
