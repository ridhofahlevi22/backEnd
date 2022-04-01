package com.cms.service.imp;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cms.dao.interfaces.TransaksiDaoInterface;
import com.cms.model.Transaksi;
import com.cms.resultobject.ResultField;
import com.cms.service.interfaces.MasterSaveUpdateDeleteServiceInterface;

@Service("masterSaveUpdateDeleteService")
@Transactional(rollbackFor= {Exception.class, HibernateException.class})
public class MasterSaveUpdateDeleteService implements MasterSaveUpdateDeleteServiceInterface {
	final static Logger logger = Logger.getLogger(MasterSaveUpdateDeleteService.class);
	
	@Autowired
	private TransaksiDaoInterface transaksiDao;
	
	public String save(Transaksi obj) throws Exception, HibernateException {
		String ret = "";
		
		try {
			transaksiDao.save(obj);
		} catch (HibernateException e) {
			throw new HibernateException(e.getLocalizedMessage());
		} catch (Exception e1) {
			throw new Exception();
		}
		
		return ret;
	}
	
	public String delete(String id) throws Exception, HibernateException {
		String ret = "";
		
		try {
			ret = transaksiDao.deleteById(id);
		} catch (HibernateException e) {
			throw new HibernateException(e.getLocalizedMessage());
		} catch (Exception e1) {
			throw new Exception();
		}
		
		return ret;
	}
	
	public ResultField findAllTransaksiWithPaging(Integer page, String field, String search, Integer maxResults, String orderBy) {
		
		return transaksiDao.findAll(field, search, page, maxResults, orderBy);
		
	}
	
}
