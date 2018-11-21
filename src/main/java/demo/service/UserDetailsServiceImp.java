package demo.service;

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

	@Autowired
	private Dao<?> dao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String nameMethod = "loadUserByUsername";
		System.out.println(nameMethod + "--------------------------------    " + " check : " + username);

		Login login = (Login) dao.findLoginByname(username);

		UserBuilder builder = null;
		if (login != null) {

			Client client = login.getClient();

			builder = org.springframework.security.core.userdetails.User.withUsername(username);
//      builder.idClient(login.getIdClient());
			builder.password(login.getPassword());
			builder.roles(login.getRole());

		} else {
			throw new UsernameNotFoundException("User: " + username + " not found");
		}
		return builder.build();
	}
}
