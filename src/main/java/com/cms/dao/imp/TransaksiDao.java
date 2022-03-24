package com.cms.dao.imp;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cms.dao.interfaces.TransaksiDaoInterface;
import com.cms.model.Transaksi;
import com.cms.resultobject.ResultField;
import com.cms.utility.StaticUtil;

@Repository("transaksiDao")
public class TransaksiDao extends AbstractDao<String, Transaksi> implements TransaksiDaoInterface {
	@Override
	public Transaksi findById(String id) {
		Transaksi obj = null;
		Session ss = getSession();
		try {
			Query q = ss.createQuery("from Transaksi o where o.id = uuid(:id)");
			if (q != null) {
				q.setParameter("id", id);
				obj = (Transaksi) q.getSingleResult();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ss.clear(); 
		}
		
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Transaksi> findAll() {
		List<Transaksi> list = null;
		Session ss = getSession();
		try {
			Query q = ss.createQuery("from Transaksi o ");
			if (q != null) {
				list = (List<Transaksi>) q.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ss.clear(); 
		}
		
		return list;
	}
	
	@Override
	public String save(Transaksi obj) throws HibernateException, Exception {
		String ret = saveOrUpdate(obj);
		return ret;
	}
	
	@Override
	public String saveAlot(List<Object> list) throws HibernateException, Exception {
		String ret = "";
		Session ss = null;
		
		if (list != null && list.size() > 0) {
			ss = getSession();
			for (Object obj : list) {
				ss.saveOrUpdate(obj);	
			}
		}
		
		return ret;
	}
	
	@Override
	public String deleteById(String id) throws HibernateException, Exception {
		Transaksi obj = findById(id);
		String ret = delete(obj);
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultField findAll(String field, String search, Integer page, Integer maxResults, String orderBy) {
		ResultField ret = new ResultField();
		List<Transaksi> list = null;
		String hql = "";
		int totalPage = 0;
		int count = 0;
		Session ss = getSession();
		
		try {
			count = count(field, search).intValue();
			totalPage = StaticUtil.getTotalRecordPaging(count, maxResults!=null?maxResults:10);
			
			page = page!=null?page:0;
			maxResults = maxResults!=null?maxResults:10;
			
			int offset = StaticUtil.getPageFromOffset(page, maxResults);
			
			hql = "from Transaksi o ";
			
			if (StaticUtil.isStringValid(field) && StaticUtil.isStringValid(search)) {
				hql += "where lower(" + field.trim() + ") like " + ":param ";
				if (StaticUtil.isStringValid(orderBy)) {
					hql += "Order By " + orderBy;
				}
				Query q = ss.createQuery(hql);
				if (q != null) {
					q.setParameter("param", "%" + search.trim().toLowerCase() + "%");
					q.setFirstResult(offset);
					q.setMaxResults(maxResults);
					list = (List<Transaksi>) q.getResultList();
				}
			} else {
				if (StaticUtil.isStringValid(orderBy)) {
					hql += "Order By " + orderBy;
				}
				Query q = ss.createQuery(hql);
				if (q != null) {
					q.setFirstResult(offset);
					q.setMaxResults(maxResults);
					list = (List<Transaksi>) q.getResultList();
				}
			}
			
			ret.totalPage = totalPage;
			ret.totalRec = count;
			ret.list = list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ss.clear();
		}
		
		return ret;
	}
	
	@Override
	public Long count(String field, String search) {
		Long ret = new Long(0);
		String hql = "";
		Session ss = getSession();
		try {
			hql = "SELECT Count(1) from Transaksi o ";
			
			if (StaticUtil.isStringValid(field) && StaticUtil.isStringValid(search)) {
				hql += "where lower(" + field.trim() + ") like " + ":param";
				Query q = ss.createQuery(hql);
				if (q != null) {
					q.setParameter("param", "%" + search.trim().toLowerCase() + "%");
					ret = (Long) q.getSingleResult();
				}
			} else {
				Query q = ss.createQuery(hql);
				if (q != null) {
					ret = (Long) q.getSingleResult();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ss.clear();
		}
		
		return ret;
	}
}
