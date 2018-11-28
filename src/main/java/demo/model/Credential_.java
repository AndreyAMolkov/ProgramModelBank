package demo.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Credential.class)
public class Credential_ {

	public static volatile SingularAttribute<Credential, Integer> id;
	public static volatile SingularAttribute<Credential, String> name;
	public static volatile SingularAttribute<Credential, String> role;
	public static volatile SingularAttribute<Credential, String> password;
	public static volatile SingularAttribute<Credential, Client> client;
	
	private Credential_() {
	}

}
