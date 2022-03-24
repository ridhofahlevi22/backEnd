package com.cms.control;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cms.model.Transaksi;
import com.cms.resultobject.ResultField;
import com.cms.service.interfaces.MasterSaveUpdateDeleteServiceInterface;
import com.cms.utility.JSONUtils;
import com.cms.utility.StaticUtil;
import com.google.gson.reflect.TypeToken;

@Controller
public class TesterController {
	private static final Logger logger = LoggerFactory.getLogger(TesterController.class);
	
	@Autowired
	private MasterSaveUpdateDeleteServiceInterface masterSaveService;
	
	@RequestMapping(value="/save.edit", consumes=MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.POST)
	public @ResponseBody String doSave(@RequestBody String json, HttpServletRequest req){
		String ret = "99|Simpan Gagal";
		try {
			logger.info("req: "+ json);
			Transaksi obj = (Transaksi) JSONUtils.parseFromJSon(json, Transaksi.class, "dd-MM-yyyy");
			
			if (obj != null) {
				String retval = masterSaveService.save(obj);
				if (StaticUtil.isStringEmpty(retval)) {
					ret = "00|Simpan Berhasil";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/save.multi", consumes=MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.POST)
	public @ResponseBody String doSaveMulti(@RequestBody String json, HttpServletRequest req){
		String ret = "99|Simpan Gagal";
		String retval = "99|Simpan Gagal";
		try {
			logger.info("req: "+ json);
//			Transaksi obj = (Transaksi) JSONUtils.parseFromJSon(json, Transaksi.class, "dd-MM-yyyy");
			Type listTypeTransaksi = new TypeToken<List<Transaksi>>(){}.getType();
			List<Transaksi> listTransaksi = (List<Transaksi>) JSONUtils.parseFromJSon(json, listTypeTransaksi);
			
			if (listTransaksi != null) {
				for (Transaksi tTransaksi : listTransaksi) {
					logger.info("hp : " + tTransaksi.getHp());
					
					try {
						retval = masterSaveService.save(tTransaksi);
					} catch (Exception e1) {
						logger.error(" : " + e1.getMessage());
					}
				}
				
				if (StaticUtil.isStringEmpty(retval)) {
					ret = "00|Simpan Berhasil";
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	@RequestMapping(value="/delete", params= {"id"}, method=RequestMethod.DELETE)
	public @ResponseBody String doDelete(@RequestParam String id){
		String ret = "99|Simpan Gagal";
		try {
			logger.info("req: "+ id);
			String retval = masterSaveService.delete(id);
			if (StaticUtil.isStringEmpty(retval)) {
				ret = "00|Hapus Berhasil";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	@RequestMapping(value = "/data/transaksi/{currpage}/{field}/{searchid}/{rowperpage}", params = { "orderby" }, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public @ResponseBody String getDataPagingOrderBy(@PathVariable int currpage, @PathVariable String field, @PathVariable String searchid, 
													 @PathVariable int rowperpage, @RequestParam String orderby) {
		
	String ret = "";
	
	try {
		logger.info("req: "+ currpage + "|"+ field + "|"+ searchid + "|"+ rowperpage + "|"+ orderby + "|");
		
		ResultField rf = masterSaveService.findAllTransaksiWithPaging(currpage, field, searchid, rowperpage, orderby);
		ret = JSONUtils.parseToJSon(rf);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return ret;
	}
}
