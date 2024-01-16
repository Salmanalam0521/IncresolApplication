package com.incresol.app.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.incresol.app.entities.OrgRoles;
import com.incresol.app.entities.User;
import com.incresol.app.repositories.OrgRolesRepository;
import com.incresol.app.repositories.OrgUserRepository;
import com.incresol.app.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService, UserDetails {

	private Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private OrgRolesRepository orgRolesRepository;
	@Autowired
	private OrgUserRepository orgUserRepository;

	private User user;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		user = userRepo.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("User not Found with user name " + email);
		}

		int maxLoginAttempts = 3;
		if (user.getFailedLoginAttempts() >= maxLoginAttempts && user.getLockedUntil() == null) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime lockedUntil = now.plusMinutes(1);
			user.setLockedUntil(lockedUntil);
			user.setAccountNonLocked(false);
			userRepo.save(user);
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				getAuthorities());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		int mainRole = orgUserRepository.findDistinctMainRoleByUser(user);
		String subRoles = orgUserRepository.findDistinctSubRolesByUser(user);
		List<OrgRoles> roles = new ArrayList<>();
		OrgRoles mainRoleDB = orgRolesRepository.findById(mainRole);
		roles.add(mainRoleDB);
		
		if(subRoles!=null) {
			String[] stringArray = subRoles.split(",");

			int[] intArray = new int[stringArray.length];

			for (int i = 0; i < intArray.length ; i++) {

				intArray[i] = Integer.parseInt(stringArray[i]);

			}

			for (int i = 0; i < intArray.length; i++) {
				System.out.println(intArray[i]);
				roles.add(orgRolesRepository.findById(intArray[i]));
			}
			
		}
		
		System.out.println("List of Roles " + roles);
		

		Set<GrantedAuthority> authorities = new HashSet<>();
		for (OrgRoles role : roles) {
			System.out.println("===================== Role_" + role.getShortId());
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleDescription()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {

		return user.getPassword();
	}

	@Override
	public String getUsername() {

		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {

		if (user.getAccountExpiredDate() == null) {

			return true;
		} else {
			return !(user.getAccountExpiredDate().isBefore(LocalDateTime.now()));
		}
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {

		if (user.getPasswordExpiredDate() == null) {
			return true;
		} else {
			boolean value = !(user.getPasswordExpiredDate().isBefore(LocalDateTime.now()));
			return value;
		}
	}

	@Override
	public boolean isEnabled() {

		return user.isEnabled();
	}

}
