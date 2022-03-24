package com.cms.dao.interfaces;

import java.util.List;

import org.hibernate.HibernateException;

import com.cms.model.Transaksi;
import com.cms.resultobject.ResultField;


public interface TransaksiDaoInterface {
	Transaksi findById(String id);
	List<Transaksi> findAll();
	String save(Transaksi obj) throws HibernateException, Exception;
	String deleteById(String id) throws HibernateException, Exception;
	ResultField findAll(String field, String search, Integer page, Integer maxResults, String orderBy);
	Long count(String field, String search);
	String saveAlot(List<Object> list) throws HibernateException, Exception;
}

