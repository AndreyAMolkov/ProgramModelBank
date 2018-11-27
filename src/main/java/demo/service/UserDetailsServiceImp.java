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
import demo.model.Login;

@Service("userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {
	private static Logger log =(Logger) LoggerFactory.getLogger("demo.controller.UserDetailsServiceImp");
	@Autowired
	private Dao<?> dao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String nameMethod = "loadUserByUsername";		
		Login login = (Login) dao.findLoginByname(username);
		UserBuilder builder = null;
		Client client=null;
		if (login != null) {

			client = login.getClient();
			
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.password(login.getPassword());
			builder.roles(login.getRole());

		} else {
			String message = "User: " + username + " not found";
			log.warn(nameMethod+" {}={}","message", message);
			throw new UsernameNotFoundException(message);
		}
		log.debug(nameMethod+" {}={}  {}={} ","username", username,"NameFromData", client.getNameFromData());
		return builder.build();
	}
}
