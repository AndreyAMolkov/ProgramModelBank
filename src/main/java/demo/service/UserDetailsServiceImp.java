package demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.dao.Dao;
import demo.model.Client;
import demo.model.Credential;

@Service("userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {
	private static Logger log = LoggerFactory.getLogger("demo.controller.UserDetailsServiceImp");
	@Autowired
	private Dao dao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) {
		String nameMethod = "loadUserByUsername";
		Credential credential = dao.findCredentialByname(username);
		UserBuilder builder = null;
		Client client = null;
		if (credential != null) {
			client = credential.getClient();
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.password(credential.getPassword());
			builder.roles(credential.getRole());
		} else {
			String message = "User: " + username + " not found";
			log.warn(nameMethod + " {}={}", "message", message);
			throw new UsernameNotFoundException(message);
		}
		log.debug(nameMethod + " {}={}  {}={} ", "username", username, "NameFromData", client.getNameFromData());
		return builder.build();
	}
}
