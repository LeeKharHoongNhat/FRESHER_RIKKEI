package rikkei.test.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rikkei.test.entity.UserCase;
import rikkei.test.service.user.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String idUser) throws UsernameNotFoundException {
		UserCase user = userService.findByIdUser(idUser);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

		grantedAuthorities.add(new SimpleGrantedAuthority(user.getUserRole().getNameRole()));

		System.err.println("grantedAuthorities: " + grantedAuthorities);
		System.err.println("ID: " + user.getIdUser());
		System.err.println("PASS: " + user.getPassword());
		return new org.springframework.security.core.userdetails.User(user.getIdUser(), user.getPassword(),
				grantedAuthorities);
	}
}
