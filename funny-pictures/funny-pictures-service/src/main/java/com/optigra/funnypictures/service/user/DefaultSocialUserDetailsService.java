package com.optigra.funnypictures.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * @author oleh.zasadnyy
 */
public class DefaultSocialUserDetailsService implements SocialUserDetailsService {

	private UserDetailsService userDetailsService;

    /**
     * Constructor.
     * @param userDetailsService
     */
    public DefaultSocialUserDetailsService(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Load SocialUserDetails by given userId (username/email).
     * @param userId
     * @return SocialUserDetails
     */
    @Override
    public SocialUserDetails loadUserByUserId(final String userId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        return (SocialUserDetails) userDetails;
    }

}
