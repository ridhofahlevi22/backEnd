package com.cms.utility;

import java.util.ArrayList;
import java.util.List;

public class Constantas {
	public static class MCSCode {
		public final static String MASTER_PAKET = "MPK001";
		public final static String MASTER_JENIS_GROUP = "MJG001";
		public final static String TRANSFER_PO = "TPO001";
		public final static String CEK_PSW = "CSO001";
		public final static String CEK_STATUS_POSBILLER = "CSPO001";
	}
	
	public static class StaticPage {
		public final static String SAVE_SUCCSESS = "registrationsuccess";
		public final static String SAVE_FAILED = "registrationsuccess";
		public final static String SAVE_MSG = "msg";
	}
	
	public static class UserRole {
		public final static String ADMIN = "ADMIN";
	}
	
	public static class EntityType {
		public final static String DC = "DC";
		public final static String OUTLET = "OUTLET";
	}
	
	public static class LocationType {
		public final static String HO = "HO";
		public final static String OUTLET = "OUTLET";
		public final static String CP = "CP";
	}
	
	public static class PaymentMethod {
		public final static String CASH = "CASH";
		public final static String INSURANCE = "INSURANCE";
		public final static String PAYMENT_DEBIT = "PAYMENT_DEBIT";
		public final static String PAYMENT_CREDIT = "PAYMENT_CREDIT";
		public final static String VOUCHER = "VOUCHER";
	}
	
	public static class TaxType {
		public final static String EXCLUDE = "EXCLUDE";
		public final static String INCLUDE = "INCLUDE";
	}
	
	public static class MathComparation {
		public final static String EQUAL_TO = "=";
		public final static String MORE_THAN = ">";
		public final static String LESS_THAN = "<";
		public final static String LESS_THAN_EQUAL_TO = "<=";
		public final static String MORE_THAN_EQUAL_TO = ">=";
	}
	
	public static class LogisticType {
		public static final String RACK_MOVEMENT_IN = "RMV_IN";
		public static final String RACK_MOVEMENT_OUT = "RMV_OUT";
	}
	
	public static class LockStockStatus {
		public static final String RELEASED = "RELEASED";
		public static final String LOCK = "LOCK";
		public static final String ALL = "ALL";
	}
	
	public static class StatusPending {		
		public final static String PENDING = "PENDING";
		public final static String OPEN = "OPEN";
		public final static String CLOSED = "CLOSED";
	}
	
	public static class TransHeaderAutoNumber {
		public final static String MS_USERS = "USR";
		public final static String MS_USER_ROLES = "USRROLES";
		public final static String MS_COUNTRIES = "MSCOUNTRY";
		public final static String MS_PROVINCE = "MSPROV";
		public final static String MS_CITY = "MSCITY";
		public final static String MS_PRINCIPAL = "MSPRINCIPAL";
		public final static String MS_PRINCIPAL_APPROVAL = "MSPRINCIPALAPROVAL";
		public final static String MS_SUBPROD1 = "MSSUBPROD1";
		public final static String MS_SUBPROD2 = "MSSUBPROD2";
		public final static String MS_SUBPROD3 = "MSSUBPROD3";
		public final static String MS_ITEM = "MSITEM";
		public final static String KARTUGARANSI = "KARTUGARANSI";
		public final static String CLAIM = "CLAIM";
	}
	
	public static List<String> getIdTypeListForMsUser() {
		List<String> idTypeList = new ArrayList<String>();
		idTypeList.add("KTP");
		idTypeList.add("SIM");
		idTypeList.add("Other");
		
		return idTypeList;
	}
}
