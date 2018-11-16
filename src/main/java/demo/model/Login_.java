package demo.model;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Login.class)
public class Login_ {
	
	public static volatile SingularAttribute<Login, Integer> id;
	public static volatile SingularAttribute<Login, String> name;
	public static volatile SingularAttribute<Login, String> role;
	public static volatile SingularAttribute<Login, String> password;
	public static volatile SingularAttribute<Login, Client>client;
	
}


