package com.bookstore.backend.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bookstore.backend.domain.User;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Collection<? extends GrantedAuthority> authorities; 
	private String password; 
	private String username;
	
	public CustomUserDetails(User user) {
		this.username = user.getUsername(); 
		this.password = user.getPassword();
		this.authorities = translate(user.getRoles());
	}

	private Collection<? extends GrantedAuthority> translate(Set<Role> roles) { 
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>(); 
		
		for (Role role : roles) { 
			String name = role.getName().toUpperCase(); 
			if (!name.startsWith("ROLE_")) { 
				name = "ROLE_" + name; 
			} 
			grantedAuthorities.add(new SimpleGrantedAuthority(name)); 
		} 
		
		return grantedAuthorities; 
	} 
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
