package com.cms.utility;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import com.cms.resultobject.JResult;
import com.cms.resultobject.ResultField;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONUtils {
	public static String parseToJSon(Object obj) {
		String ret = "";
		
		try {
			GsonBuilder gson = new GsonBuilder();
			Gson gsonx = gson.create();
			ret = gsonx.toJson(obj);	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public static String parseToJSonWithExpose(Object obj) {
		String ret = "";
		
		try {
			GsonBuilder gson = new GsonBuilder();
			Gson gsonx = gson.excludeFieldsWithoutExposeAnnotation(). create();
			ret = gsonx.toJson(obj);	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public static Object parseFromJSonWithSkipDate(String content, Class<?> classOf) {
		Object obj = new Object();
		
		try {
			GsonBuilder builder = new GsonBuilder();
			//builder.excludeFieldsWithoutExposeAnnotation();
			builder.setExclusionStrategies(new ExclusionStrategy() {  
			    @Override
			    public boolean shouldSkipField(FieldAttributes f) {
			        return false;
			    }

			    @Override
			    public boolean shouldSkipClass(Class<?> incomingClass) {
			        return incomingClass == Date.class || incomingClass == boolean.class;
			    }
			});
			Gson gson = builder.create();
	    	obj = gson.fromJson(content,  classOf);
		} catch (Exception e) {
			StaticUtil.ShowMsg("Error ", e.getMessage());
			obj = null;
		}
		
		return obj;
	}
	
	public static Object parseFromJSon(String content, Class<?> classOf) {
		Object obj = new Object();
		
		try {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
	    	obj = gson.fromJson(content,  classOf);
		} catch (Exception e) {
			StaticUtil.ShowMsg("Error ", e.getMessage());
			obj = null;
		}
		
		return obj;
	}
	
	public static List<?> parseFromJSon(String content, Type listType) {
		List<?> ret = new ArrayList<>();
		
		try {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
	    	ret = gson.fromJson(content, listType);
		} catch (Exception e) {
			StaticUtil.ShowMsg("Error ", e.getMessage());
		}
		
		return ret;
	}
	
	
	
	public static Object parseFromJSon(String content, Class<?> classOf, String dateFormat) {
		Object obj = new Object();
		
		try {
			GsonBuilder builder = new GsonBuilder();
			builder.setDateFormat(dateFormat);
			Gson gson = builder.create();
	    	obj = gson.fromJson(content,  classOf);
		} catch (Exception e) {
			StaticUtil.ShowMsg("Error ", e.getMessage());
			obj = null;
		}
		
		return obj;
	}
	
	public static JResult parseJSONFromJR(String strjson) {
		JResult ret = new JResult();
		
		try {
			Gson gson = new GsonBuilder().create();
	    	ret = gson.fromJson(strjson, JResult.class);
		} catch (Exception e) {
			ShowMsg("Error " + e.getClass().getMethods().toString(), e.getMessage());
		}
    	
		return ret;
	}
	
	//=============================================================================================================
	
	public static String jsonParser(String jsonValue,String tag) {
		String data =null;
		JsonFactory jfac = new JsonFactory();
		try {
			JsonParser json = jfac.createJsonParser(jsonValue);
			while(json.nextToken() != JsonToken.END_OBJECT)
			{
				String fieldName = json.getCurrentName();
				if(tag.equals(fieldName))
				{
					json.nextToken();
					data = json.getText();
					break;
				}
			}
			json.close();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}
	
	public static String getDefaultValue(String data) {
		String ret = "";
		
		try {
			ret = (data == null) ? "" : data;
		} catch(Exception e) {
			ret = "";
		}
		
		return ret;
	}
	
	public static float getDefaultValue(Float data) {
		float ret = 0;
		
		try {
			ret = (data == null) ? 0 : data;
		} catch(Exception e) {
			ret = 0;
		}
		
		return ret;
	}
	
	public static Integer getDefaultValue(Integer data) {
		Integer ret = 0;
		
		try {
			ret = (data == null) ? 0 : data;
		} catch(Exception e) {
			ret = 0;
		}
		
		return ret;
	}
	
	public static Boolean getDefaultValue(Boolean data) {
		Boolean ret = false;
		
		try {
			ret = (data == null) ? false : data;
		} catch(Exception e) {
			ret = false;
		}
		
		return ret;
	}
	
	public static BigDecimal getDefaultValue(BigDecimal data) {
		BigDecimal ret = new BigDecimal(0);
		
		try {
			ret = (data == null) ? new BigDecimal(0) : data;
		} catch(Exception e) {
			ret = new BigDecimal(0);
		}
		
		return ret;
	}
	
	public static Object getDefaultValue(Object data, Object defaultValue) {
		Object ret = "";
		
		try {
			ret = (data == null) ? defaultValue : data;
		} catch(Exception e) {
			ret = "";
		}
		
		return ret;
	}
	
	public static void ShowMsg(String Type, String msg) {
		System.out.println(Type + " : " + msg);
	}
	
	
	//=============================================================================================================
	
	public static ResultField parseJSONObjectToRF(String strjson) {
		ResultField ret = new ResultField();
		
		try {
	    	JResult j = parseJSONFromJR(strjson);
	    	if (j != null) {
	    		
	    		ret.result = j.getResult();
	    	}
		} catch (Exception e) {
			ShowMsg("Error ON JSON oiii : ", e.getMessage());
			ret = null;
		}
    	
		return ret;
	}
	
	public static Object toJSONObject(String json, Class<?> c) {
		Object obj = null;
		
		try {
			Gson gson = new GsonBuilder().create();
			obj = gson.fromJson(json, c);
		} catch(Exception e) {
			ShowMsg("Error ", e.getMessage());
			e.printStackTrace();
		}
		
		
		return obj;
	}
	
	public static String generateJSONObject(Object obj) {
		String ret = "";
		
		try {
			Gson gson = new GsonBuilder().create();
	    	ret = gson.toJson(obj);
		} catch(Exception e) {
			ShowMsg("Error ", e.getMessage());
		}
		
		
		return ret;
	}
	
	public static String geerateJSONtObject(String modul, String intf, String type,  String user, String pwd, String security, Object content, int result, String msg) {
		JResult obj = new JResult();
		String ret = "";
		
		try {
			obj.setModul(modul);
			obj.setIntf(intf);
			obj.setType(type);
			obj.setUser(user);
			obj.setPwd(pwd);
			obj.setSecurity(security);
			obj.setContent(content);
			obj.setResult(result);
			obj.setMsg(msg);
			
			Gson gson = new GsonBuilder().create();
	    	ret = gson.toJson(obj).replaceAll("\"", "");;	
		} catch(Exception e) {
			ShowMsg("Error ", e.getMessage());
		}
		
		
		return ret;
	}
	
	public static String geerateJSONtObject(ResultField rf) {
		JResult obj = new JResult();
		String ret = "";
		
		try {
			obj.setResult(rf.result);
			
			Gson gson = new GsonBuilder().create();
	    	ret = gson.toJson(obj);
		} catch(Exception e) {
			ShowMsg("Error ", e.getMessage());
		}
		
		return ret;
	}
	
	public static void main(String[] args) {
	}
}
