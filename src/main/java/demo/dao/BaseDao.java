package demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//@Transactional
public abstract class BaseDao<T> implements Dao<T> {
	private static Logger log =LoggerFactory.getLogger("BaseDao");
	@PersistenceContext
	private EntityManager em;

	public EntityManager getEntityManager() {
		return em;
	}

	public BaseDao() {

	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public T getById(Long id, Class<?> T) {
		String nameMethod = "getById";
		em.find(T, id);
		return (T) em.find(T, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(Class<?> T) {
		String nameMethod = "getAll";
		log.debug(nameMethod,T);
		return (List<T>) em.createQuery(String.format("SELECT e FROM %s e ORDER BY id", T.getSimpleName()), T)
				.getResultList();

	}

	@Transactional(propagation = Propagation.REQUIRED)
	@SuppressWarnings("hiding")
	public <T> T merge(final T object) {
		String nameMethod = "merge";
		return (T) em.merge(object);
	}

	@Transactional
	public void add(Object obj) {
		String nameMethod = "add";
		em.persist(obj);
	}

	@Transactional
	public void remove(Long id, Class<?> T) {
		String nameMethod = "remove";
		T t = getById(id, T);
		em.remove(t);
	}

}
