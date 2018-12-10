package demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Transactional;

import demo.constant.Constants;
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
		Credential credential=null;
		try {
			credential = dao.findCredentialByname(username);
		} catch (CannotCreateTransactionException e) {
			log.warn(nameMethod +  Constants.ONE_PARAMETERS, "message", e);
		}
		UserBuilder builder = null;
		Client client = null;
		if (credential != null) {
			client = credential.getClient();
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.password(credential.getPassword());
			builder.roles(credential.getRole());
		log.debug(nameMethod + Constants.THREE_PARAMETERS, Constants.CLIENT_ID, client.getId(),"login", username, "NameFromData", client.getNameFromData());
		} else {
			String message = "User: " + username + " not found";
			log.warn(nameMethod + Constants.ONE_PARAMETERS, "message", message);
			throw new UsernameNotFoundException(message);
		}
		
		return builder.build();
	}
}
