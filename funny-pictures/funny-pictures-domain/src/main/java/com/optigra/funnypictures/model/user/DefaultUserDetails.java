package com.optigra.funnypictures.model.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

public class DefaultUserDetails extends SocialUser implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String firstName;

	private String lastName;

	private Role role;

	private SocialMediaService socialSignInProvider;

	private Date createDate;

	private Date updateDate;

	public DefaultUserDetails(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public static class Builder {

		private Long id;

		private String username;

		private String firstName;

		private String lastName;

		private String password;

		private Role role;

		private SocialMediaService socialSignInProvider;

		private Date createDate;

		private Date updateDate;

		private Set<GrantedAuthority> authorities;

		public Builder() {
			this.authorities = new HashSet<>();
		}

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder password(String password) {
			if (password == null) {
				password = "SocialUser";
			}

			this.password = password;
			return this;
		}

		public Builder role(Role role) {
			this.role = role;

			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
					role.toString());
			this.authorities.add(authority);

			return this;
		}

		public Builder socialSignInProvider(
				SocialMediaService socialSignInProvider) {
			this.socialSignInProvider = socialSignInProvider;
			return this;
		}

		public Builder createDate(Date createDate) {
			this.createDate = createDate;
			return this;
		}

		public Builder updateDate(Date updateDate) {
			this.updateDate = updateDate;
			return this;
		}

		public DefaultUserDetails build() {
			DefaultUserDetails user = new DefaultUserDetails(username,
					password, authorities);

			user.id = id;
			user.firstName = firstName;
			user.lastName = lastName;
			user.role = role;
			user.socialSignInProvider = socialSignInProvider;
			user.createDate = createDate;
			user.updateDate = updateDate;

			return user;
		}
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Role getRole() {
		return role;
	}

	public SocialMediaService getSocialSignInProvider() {
		return socialSignInProvider;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime
				* result
				+ ((socialSignInProvider == null) ? 0 : socialSignInProvider
						.hashCode());
		result = prime * result
				+ ((updateDate == null) ? 0 : updateDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultUserDetails other = (DefaultUserDetails) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (role != other.role)
			return false;
		if (socialSignInProvider != other.socialSignInProvider)
			return false;
		if (updateDate == null) {
			if (other.updateDate != null)
				return false;
		} else if (!updateDate.equals(other.updateDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DefaultUserDetails [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", role=" + role
				+ ", socialSignInProvider=" + socialSignInProvider
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ "]";
	}

}
