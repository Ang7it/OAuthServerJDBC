package com.example.auth.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails,Serializable{

	private static final long serialVersionUID = -128220113331204929L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "email")
	private String email;
	@Column(name = "enabled")
	private boolean enabled;
	@Column(name = "accountNonExpired")
	private boolean accountNonExpired;
	@Column(name = "accountNonLocked")
	private boolean accountNonLocked;
	@Column(name = "credentialsNonExpired")
	private boolean credentialsNonExpired;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_user",joinColumns = { @JoinColumn(name="user_id",referencedColumnName = "id")},
				inverseJoinColumns = {@JoinColumn(name="role_id",referencedColumnName = "id")})
	private List<Role> roles;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities=new ArrayList<>();
		this.roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			role.getPermissions().forEach(permission->{
				authorities.add(new SimpleGrantedAuthority(permission.getName()));
			});
		});
	
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
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
