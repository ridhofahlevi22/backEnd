package com.cms.service.interfaces;

import org.hibernate.HibernateException;

import com.cms.model.Transaksi;
import com.cms.resultobject.ResultField;

public interface MasterSaveUpdateDeleteServiceInterface {
	String save(Transaksi obj) throws Exception, HibernateException;

	String delete(String id) throws Exception, HibernateException;

	ResultField findAllTransaksiWithPaging(Integer maxResults, String field, String search, Integer page, String orderBy);
}