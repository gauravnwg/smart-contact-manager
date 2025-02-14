package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	@Id
	private String userId;
	@Column(name = "user_name")
	private String name;
	private String email;
	@Getter(value = AccessLevel.NONE)
	private String password;
	@Column(length = 1000)
	private String about;
	@Column(length = 1000)
	private String profilePic;
	private String phoneNumber;
	// information
	private boolean enabled = false;
	private boolean emailVerified = false;
	private boolean phoneVerified = false;
	@Enumerated(value = EnumType.STRING)
	private Providers provider = Providers.SELF;
	private String providerUserId;

	// add more field if you needed

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Contact> contacts = new ArrayList<>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roleList = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//list of roles [ADMIN, USER]
		Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());
		return roles;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

}
