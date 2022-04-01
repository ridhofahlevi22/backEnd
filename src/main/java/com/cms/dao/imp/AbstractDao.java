package com.cms.dao.imp;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import com.cms.utility.StaticUtil;

@Transactional
public abstract class AbstractDao<PK extends Serializable, T> {
	final static Logger logger = Logger.getLogger(AbstractDao.class);
	
	private final Class<T> persistentClass;
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public AbstractDao(){
		this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	protected Session getSession(){
		Session session = entityManager.unwrap(Session.class);
		return session;
	}

	public T getByKey(String key) {
		return (T) getSession().get(persistentClass, key);
	}

	public String persist(T entity) {
		String ret = "";
		
		try {
			getSession().persist(entity);	
		} catch (Exception e) {
			ret = e.getMessage().toString();
			logger.error(ret);
		}
		
		return ret;
		
	}

	public String delete(T entity) throws Exception, HibernateException {
		String ret = "";
		try {
			T em = this.entityManager.merge(entity);
			this.entityManager.remove(em);
			this.entityManager.flush();
			this.entityManager.clear();	
		} catch (HibernateException e) {
			e.getMessage().toString();
			throw new HibernateException(e.getMessage().toString());
		} catch (Exception e) {
			e.getMessage().toString();
			throw new Exception(e.getMessage().toString());
		} 
		
		return ret;
	}
	
	public String deleteAlot(List<Object> list) throws HibernateException, Exception {
		String ret = "";
		if (StaticUtil.isListValid(list)) {
			for (Object obj : list) {
				@SuppressWarnings("unchecked")
				T em = this.entityManager.merge((T) obj);
				this.entityManager.remove(em);
				this.entityManager.flush();
				this.entityManager.clear();		
			}
		}
		
		return ret;
	}
	
	public String getAutoNumber(String header) {
		String ret = "";
		try {
			String d = StaticUtil.getDateStringFormat(new Date(), "dd");
			String m = StaticUtil.getDateStringFormat(new Date(), "MM");
			String y = StaticUtil.getDateStringFormat(new Date(), "yyyy");

			String hh = StaticUtil.getDateStringFormat(new Date(), "HH");
			String mm = StaticUtil.getDateStringFormat(new Date(), "mm");
			String ss = StaticUtil.getDateStringFormat(new Date(), "ss");

			String nano = String.valueOf(System.nanoTime());

			ret = header + ss + mm + hh + y + m + d + nano;
		} catch (Exception e) {

		}

		return ret;
	}
	
	public String saveOrUpdate(T Entity) throws HibernateException, Exception {
		String ret = "";
		Session ss = null;
		
		try {
			ss = getSession();
			ss.saveOrUpdate(Entity);	
		} catch (HibernateException e) {
			ret = e.getMessage().toString();
		} catch (Exception e) {
			ret = e.getMessage().toString();
		}
		
		logger.info(ret);
		
		return ret;
	}
	
	public String saveOrUpdateMultipleObject(List<Object> list) throws HibernateException, Exception {
		String ret = "";
		Session ss = null;
		try {
			if (list != null && list.size() > 0) {
				ss = getSession();
				for (Object obj : list) {
					ss.saveOrUpdate(obj);	
				}
			}
		} catch (HibernateException e) {
			ret = e.getMessage().toString();
		} catch (Exception e) {
			ret = e.getMessage().toString();
		} 
		return ret;
	}
	
	public String saveOrUpdate(List<T> list) throws HibernateException, Exception {
		String ret = "";
		Session ss = null;
		try {
			ss = getSession();
			
			for (T obj : list) {
				ss.saveOrUpdate(obj);	
			}
		} catch (HibernateException e) {
			ret = e.getMessage().toString();
		} catch (Exception e) {
			ret = e.getMessage().toString();
		}
		
		return ret;
	}
	
	public String saveOrUpdateTest(Object Entity) throws HibernateException, Exception {
		String ret = "";
		Session ss = null;
		
		try {
			ss = getSession();
			ss.saveOrUpdate(Entity);	
		} catch (HibernateException e) {
			ret = e.getMessage().toString();
		} catch (Exception e) {
			ret = e.getMessage().toString();
		}
		
		return ret;
	}
	
	public List<T> criteriaGetList(String attr, List<?> listParam) {
		Session ss = getSession();
		List<T> list = new ArrayList<T>();
		
		try {
			CriteriaBuilder cb = ss.getCriteriaBuilder();
			CriteriaQuery<T> cr = cb.createQuery(persistentClass);
			Root<T> root = cr.from(persistentClass);
			
			Expression<String> parentExpression = root.get(attr);
			Predicate parentPredicate = parentExpression.in(listParam);
			
			cr.select(root).where(parentPredicate);
			org.hibernate.query.Query<T> q = ss.createQuery(cr);
			if (q != null) {
				list = q.getResultList();
			}	
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return list;
	}
}
