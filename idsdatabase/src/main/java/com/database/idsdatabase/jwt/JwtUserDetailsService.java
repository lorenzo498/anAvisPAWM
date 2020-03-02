package com.database.idsdatabase.jwt;


import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.database.idsdatabase.repository.UserDao;
import com.database.idsdatabase.repository.UserRoleRepository;
import com.database.idsdatabase.entity.DAOUser;
import com.database.idsdatabase.entity.UserRole;
import com.database.idsdatabase.dto.UserDTO;


@Service(value = "userService") 
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DAOUser user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

		org.springframework.security.core.userdetails.User u = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
		return u;
	}

	public DAOUser findUserByUsername(String username){
		return userDao.findByUsername(username);
	}
	
	private Set<GrantedAuthority> getAuthority(DAOUser user) { 
        Set<GrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}

	public List findAll() {
		List list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public DAOUser save(UserDTO user) {
		DAOUser newUser = new DAOUser();
		newUser.setUsername(user.getUsername());
		newUser.setName(user.getName());
		newUser.setlastname(user.getLastname());
		newUser.setemail(user.getemail());
		newUser.setstato(user.getStato());
		newUser.setemergenza(user.getEmergenza());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		DAOUser saved = userDao.save(newUser);

		UserRole newUserRole = new UserRole();
		newUserRole.setRole_id(1);
		newUserRole.setUser_id(saved.getId());
		userRoleRepository.save(newUserRole);
		return saved;
	}


}