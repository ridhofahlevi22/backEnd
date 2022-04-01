package com.cms.utility;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class StaticUtil {
	final static Logger logger = Logger.getLogger(StaticUtil.class);
	//public static String userName = "admin";
	
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
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return data;
	}
	
//	public static void sendEmail(String emailto, String mail, String subject) throws MessagingException{
//		final String username	= "mykopnus@kopnus.com";
//		final String password	= "Pass1234";
//
//		Properties props = new Properties();
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.host", "mail.kopnus.com");
//		props.put("mail.smtp.port", "25");
//		Session session	= Session.getDefaultInstance(props, 
//		new Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication(){
//				return new PasswordAuthentication(username, password);
//			}
//			});
//			StringBuffer pesan = new StringBuffer();
//			pesan.append(mail);
//			Message message	= new MimeMessage(session);
//			message.setFrom(new InternetAddress("mykopnus@kopnus.com"));
//			message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailto));
//			message.setSubject(subject);
//			message.setText(pesan.toString());
//			Transport.send(message);
//			System.out.println("Done!!!");
//	}
	
	public static void sendEmail(String emailto, String mail, String subject) throws MessagingException{
		final String username	= "boc.mail@r-link.co.id";
		final String password	= "12345678qweASD";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.host", "mail.r-link.co.id");
		props.put("mail.smtp.port", "465");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication(username, password);
		      }
		   });
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("boc.mail@r-link.co.id", false));

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailto));
			msg.setSubject(subject);
			msg.setContent(mail, "text/html");
			msg.setSentDate(new Date());

			Transport.send(msg);   
			System.out.println("Done!!!");
	}
	public void sendmail() throws AddressException, MessagingException, IOException {
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("tutorialspoint@gmail.com", "<your password>");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("tutorialspoint@gmail.com", false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("tutorialspoint@gmail.com"));
		   msg.setSubject("Tutorials point email");
		   msg.setContent("Tutorials point email", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("Tutorials point email", "text/html");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   MimeBodyPart attachPart = new MimeBodyPart();

		   attachPart.attachFile("/var/tmp/image19.png");
		   multipart.addBodyPart(attachPart);
		   msg.setContent(multipart);
		   Transport.send(msg);   
		}
	
	public static String getLastDigitReversed(int numDigit, String val) {
		String ret = "";
		try {
			for (int i = 1; i <= numDigit; i++) {
				int len = (val.length() - i);
				ret += val.substring(len, (len + 1));
			}
		} catch (Exception e) {

		}
		return ret;
	}

	public static String encoderMD5(String str) {
		return encoder("MD5", str);
	}

	public static String encoderSHA(String str) {
		return encoder("SHA", str);
	}

	public static String encoder(String encoder, String str) {
		String s = "";
		try {
			byte b[] = java.security.MessageDigest.getInstance(encoder).digest(
					(str).getBytes());
			BigInteger bi = new BigInteger(1, b);
			s = bi.toString(16);
			while (s.length() < 32) {
				s = "0" + s;
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		}
		return s;
	}

	public static int checkdigit(String idWithoutCheckdigit) {

		// allowable characters within identifier
		String validChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVYWXZ_";

		// remove leading or trailing whitespace, convert to uppercase
		idWithoutCheckdigit = idWithoutCheckdigit.trim().toUpperCase();

		// this will be a running total
		int sum = 0;

		// loop through digits from right to left
		for (int i = 0; i < idWithoutCheckdigit.length(); i++) {

			// set ch to "current" character to be processed
			char ch = idWithoutCheckdigit.charAt(idWithoutCheckdigit.length()
					- i - 1);

			// throw exception for invalid characters
			if (validChars.indexOf(ch) == -1) {
				try {
					throw new Exception("\"" + ch
							+ "\" is an invalid character");
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}

			// our "digit" is calculated using ASCII value - 48
			int digit = (int) ch - 48;

			// weight will be the current digit's contribution to
			// the running total
			int weight;
			if (i % 2 == 0) {

				// for alternating digits starting with the rightmost, we
				// use our formula this is the same as multiplying x 2 and
				// adding digits together for values 0 to 9. Using the
				// following formula allows us to gracefully calculate a
				// weight for non-numeric "digits" as well (from their
				// ASCII value - 48).
				weight = (2 * digit) - (int) (digit / 5) * 9;

			} else {

				// even-positioned digits just contribute their ascii
				// value minus 48
				weight = digit;

			}

			// keep a running total of weights
			sum += weight;

		}

		// avoid sum less than 10 (if characters below "0" allowed,
		// this could happen)
		sum = Math.abs(sum) + 10;

		// check digit is amount needed to reach next number
		// divisible by ten
		return (10 - (sum % 10)) % 10;

	}

	public static String getNumberCustomFormat(String digitFormat,
			BigDecimal val) {
		String ret = "";
		NumberFormat formatter = new DecimalFormat(digitFormat);
		ret = formatter.format(val);
		return ret;
	}

	public static String getNumberCustomFormat(String digitFormat, int val) {
		String ret = "";
		NumberFormat formatter = new DecimalFormat(digitFormat);
		ret = formatter.format(val);
		return ret;
	}

	public static String getNumberCustomFormat(String digitFormat, long val) {
		String ret = "";
		NumberFormat formatter = new DecimalFormat(digitFormat);
		ret = formatter.format(val);
		return ret;
	}

	public static String getNumberCustomFormat(String digitFormat, double val) {
		String ret = "";
		NumberFormat formatter = new DecimalFormat(digitFormat);
		ret = formatter.format(val);
		return ret;
	}

	public static String getPeriodeNow() {
		String ret = "";
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		ret = dateFormat.format(date);
		return ret;
	}

	public static String getTimeNow() {
		String ret = "";
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		ret = dateFormat.format(date);
		return ret;
	}

	public static Date getDateNow() {
		Date date = null;
		try {
			date = new Date();
		} catch (Exception e) {
			System.out.println("Error At get Date Now");
		}
		return date;
	}

	public static String getDayName(Date date) {
		String ret = "";
		try {
			DateFormat f = new SimpleDateFormat("EEEE");
			ret = f.format(date);
		} catch (Exception e) {
			System.out.println("Error At Get Day Name : " + e.getMessage());
		}
		return ret;
	}

	public static boolean isSaturdayAndSunday() {
		boolean ret = false;
		Date date = StaticUtil.getDateNow();
		String day = StaticUtil.getDayName(date);
		if (day.equalsIgnoreCase("Saturday") || day.equalsIgnoreCase("Sunday")) {
			ret = true;
		}
		System.out.println("Day Now : " + day);
		return ret;
	}

	public static void ShowMsg(String Type, String msg) {
		System.out.println(Type + " : " + msg);
	}

	public static String replacePetikToNoSpace(String strData) {
		String ret = "";

		if (strData == null) {
			ret = "";
		} else {
			ret = strData.replaceAll("\"", "");
		}

		return ret;
	}

	@SuppressWarnings("deprecation")
	public static String getDateStringFormat(String date) {
		String hasil = "";
		Date d = new Date(date);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		hasil = df.format(d);
		return hasil;
	}

	public static String getDateStringFormat(long date, String format) {
		String hasil = "";
		Date d = new Date(date);
		DateFormat df = null;

		if (format.trim().equalsIgnoreCase("")) {
			df = new SimpleDateFormat();
		} else {
			df = new SimpleDateFormat(format);
		}

		hasil = df.format(d);
		return hasil;
	}

	@SuppressWarnings("deprecation")
	public static String getDateStringFormat(String date, String format) {
		String hasil = "";
		Date d = new Date(date);
		DateFormat df = null;

		if (format.trim().equalsIgnoreCase("")) {
			df = new SimpleDateFormat();
		} else {
			df = new SimpleDateFormat(format);
		}

		hasil = df.format(d);
		return hasil;
	}
	
	public static String getDateStringFormat(Date d, String format) {
		String hasil = "";
		DateFormat df = null;

		if (format.trim().equalsIgnoreCase("")) {
			df = new SimpleDateFormat();
		} else {
			df = new SimpleDateFormat(format);
		}

		hasil = df.format(d);
		return hasil;
	}

	public static String getDateStringFormat(Date d, String format,
			Locale locale) {
		String hasil = "";
		DateFormat df = null;

		if (format.trim().equalsIgnoreCase("")) {
			df = new SimpleDateFormat();
		} else {
			df = new SimpleDateFormat(format, locale);
		}

		hasil = df.format(d);
		return hasil;
	}

	public static String getStringFromDate(Date d) {
		String ret = "";

		if (d != null) {
			ret = getDateStringFormat(d, "MMMM d, yyyy HH:mm:ss", Locale.ENGLISH);
					
		}

		return ret;
	}

	public static Date getDateFromString(String string) {
		DateFormat format = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss", Locale.ENGLISH);
				
		Date date = new Date();
		try {
			date = format.parse(string);
			System.out.println(date);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			date = new Date();

		}
		return date;
	}
	
	public static Date getDateFromString(String str, String strFormat, Locale locale) {
		DateFormat format = new SimpleDateFormat(strFormat, locale);
		Date date = new Date();
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		
		return date;
	}
	
	@SuppressWarnings("deprecation")
	public static long getDateLongFormatFromDate(Date date) {
		date.setDate(date.getDate() + 1);
		Date d = new Date(date.toLocaleString());
		return d.getTime();
	}

	public static String balikKarakterTanggalkeMMddyyyy(String tgl) {
		String ret = "";
		String[] data = null;
		try {
			data = tgl.trim().split("[/]");
			ret = data[1] + "/" + data[0] + "/" + data[2];
		} catch (Exception e) {
			System.out
					.println("Error At Reverse Date Char (Util.balikKarakterTanggalkeMMddyyyy) : "
							+ e.getMessage());
		}
		return ret;
	}

	public Date getCurrentDate() {
		Date d = new Date();
		return d;
	}

	public static String getNumberFormat(BigDecimal num) {
		String hasil = "";
		NumberFormat formatter = NumberFormat.getInstance();
		try {
			hasil = formatter.format(num);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return hasil;
	}

	public static String getAutoNumber(String header) {
		String ret = "";
		try {
			String d = getDateStringFormat(new Date(), "dd");
			String m = getDateStringFormat(new Date(), "MM");
			String y = getDateStringFormat(new Date(), "yyyy");

			String hh = getDateStringFormat(new Date(), "HH");
			String mm = getDateStringFormat(new Date(), "mm");
			String ss = getDateStringFormat(new Date(), "ss");

			String nano = String.valueOf(System.nanoTime());

			ret = header + ss + mm + hh + y + m + d + nano;
		} catch (Exception e) {

		}

		return ret;
	}
	
	private static char randomChar(){
		Random rng = new SecureRandom();
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		return ALPHABET.charAt(rng.nextInt(ALPHABET.length()));
	}

	public static StringBuilder randomUUID(int length, int spacing, char spacerChar){
	    StringBuilder sb = new StringBuilder();
	    int spacer = 0;
	    while(length > 0){
	        if(spacer == spacing){
	            sb.append(spacerChar);
	            spacer = 0;
	        }
	        length--;
	        spacer++;
	        
	        char c = randomChar();
	        sb.append(c);
	    }
	    
	    return sb;
	}
	
	public static float getFloatFromBigDecimal(BigDecimal val) {
		float ret = 0;

		try {
			ret = val.floatValue();
		} catch (Exception e) {
			ret = 0;
		}

		return ret;
	}

	public static String getDefaultValue(String data) {
		String ret = "";

		try {
			ret = (data == null) ? "" : data;
		} catch (Exception e) {
			ret = "";
		}

		return ret;
	}

	@SuppressWarnings("removal")
	public static Float getDefaultValue(Float data) {
		Float ret = new Float(0);

		try {
			ret = (data == null) ? 0 : data;
		} catch (Exception e) {
			ret = new Float(0);
		}

		return ret;
	}

	public static Integer getDefaultValue(Integer data) {
		Integer ret = 0;

		try {
			ret = (data == null) ? 0 : data;
		} catch (Exception e) {
			ret = 0;
		}

		return ret;
	}

	public static Boolean getDefaultValue(Boolean data) {
		Boolean ret = false;

		try {
			ret = (data == null) ? false : data;
		} catch (Exception e) {
			ret = false;
		}

		return ret;
	}

	public static BigDecimal getDefaultValue(BigDecimal data) {
		BigDecimal ret = new BigDecimal(0);

		try {
			ret = (data == null) ? new BigDecimal(0) : data;
		} catch (Exception e) {
			ret = new BigDecimal(0);
		}

		return ret;
	}

	public static Object getDefaultValue(Object data, Object defaultValue) {
		Object ret = "";

		try {
			ret = (data == null) ? defaultValue : data;
		} catch (Exception e) {
			ret = "";
		}

		return ret;
	}

	public static boolean isStringValid(String id) {
		boolean ret = false;

		try {
			if (id != null && !id.trim().equalsIgnoreCase("")
					&& !id.trim().equalsIgnoreCase("-")
					&& !id.trim().equalsIgnoreCase("undefined")
					) {
				ret = true;
			}
		} catch (Exception e) {

		}

		return ret;
	}

	public static boolean isStringEmpty(String id) {
		boolean ret = false;

		try {
			if (id != null && (id.trim().equalsIgnoreCase("") || id.trim().equalsIgnoreCase("-"))) {
				ret = true;
			}
		} catch (Exception e) {

		}

		return ret;
	}
	
	public static boolean isStringEmpty(String ...list) {
		boolean ret = true;

		try {
			if (StaticUtil.isStringArrayValid(list)) {
				for (String id : list) {
					if (!StaticUtil.isStringEmpty(id)) {
						ret = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return ret;
	}
	
	public static boolean isStringArrayValid(String[] list) {
		boolean ret = false;

		try {
			if (list != null && list.length > 0) {
				ret = true;
			}
		} catch (Exception e) {

		}

		return ret;
	}

	public static boolean isStringCompared(String strA, String strB) {
		boolean ret = false;

		try {
			if (isStringValid(strA) && isStringValid(strB)) {
				strA = getDefaultValue(strA);
				strB = getDefaultValue(strB);
	
				if (strA.trim().equalsIgnoreCase(strB.trim())) {
					ret = true;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			ret = false;
		}

		return ret;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isListValid(List list) {
		boolean ret = false;

		try {
			if (list != null && list.size() > 0) {
				ret = true;
			}
		} catch (Exception e) {

		}

		return ret;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isListValid(List ...lists) {
		boolean ret = false;
		int c = 0;
		try {
			for(List list : lists) {
				if (list != null && list.size() > 0) {
					c++;
				}
			}
			
			if (lists.length == c) {
				ret = true;
			}
		} catch (Exception e) {
			ret = false;
			logger.error(e.getMessage());
		}

		return ret;
	}
	
	public static Timestamp getCurrentTimeStamp() {
		Timestamp ret = new Timestamp(new Date().getTime());

		return ret;
	}

	public static String getDefaultData(Object data) {
		String ret = "";

		if (data instanceof Date) {
			Date obj = (Date) data;

			ret = String.valueOf(obj.getTime());
		} else if (data instanceof BigDecimal) {
			BigDecimal obj = (BigDecimal) data;
			ret = obj.toString();
		} else if (data instanceof Boolean) {
			Boolean obj = (Boolean) data;
			ret = obj.toString();
		} else {
			String obj = (String) data;
			if (!StaticUtil.isStringValid(obj)) {
				ret = "-";
			} else {
				ret = obj;
			}
		}

		return ret;
	}

	public static String getNumberCustomDigitFormat(String strFormat) {
		String ret = "";
		int digit = 0;

		try {
			digit = 6;
			if (digit == 0) {
				digit = 5;
			}
		} catch (Exception e) {
			digit = 5;
		}

		for (int i = 0; i < digit; i++) {
			ret += strFormat;
		}

		return ret;
	}

	public static void log(Object msg) {
		System.out.println(msg);
	}

	public static void logE(Object msg) {
		System.out.println("Error : " + msg);
	}

	public static int getTotalRecordPaging(int recTotal, int pagingTo) {
		int ret = 0;

		try {
			ret = recTotal / pagingTo;

			if (recTotal % pagingTo > 0) {
				ret++;
			}
		} catch (Exception e) {
			StaticUtil.logE(e.getMessage());
			ret = 0;
		}

		return ret;
	}

	public static int getTotalRecordPaging(long recTotal, long pagingTo) {
		int ret = 0;
		long temp = 0;
		try {
			temp = recTotal / pagingTo;

			if (recTotal % pagingTo > 0) {
				temp++;
			}
		} catch (Exception e) {
			StaticUtil.logE(e.getMessage());
			temp = 0;
		} finally {
			ret = (int) temp;
		}

		return ret;
	}

	public static float round(float value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (float) tmp / factor;
	}

	public static float roundApp(float val) {
		float ret = 0;

		ret = round(val, 2);

		return ret;
	}

	public static void addTree(File file, Collection<File> all) {
		File[] children = file.listFiles();
		if (children != null) {
			for (File child : children) {
				all.add(child);
				// addTree(child, all);
			}
		}
	}

	public static File[] finder(String dirName) {
		File dir = new File(dirName);

		return dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".xls");
			}
		});

	}

	public static Date removeTimeFromDate(Date date) {
		Date ret = null;

		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			ret = cal.getTime();
		} catch (Exception e) {

		}

		return ret;
	}

	public static Date addTimeFromDate(Date date, int h, int m, int s, int ms) {
		Date ret = null;

		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			cal.set(Calendar.HOUR_OF_DAY, h);
			cal.set(Calendar.MINUTE, m);
			cal.set(Calendar.SECOND, s);
			cal.set(Calendar.MILLISECOND, ms);

			ret = cal.getTime();
		} catch (Exception e) {

		}

		return ret;
	}

	public static Date addDate(Date date, int val) {
		Date ret = null;

		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			cal.add(Calendar.DATE, val);

			ret = cal.getTime();
		} catch (Exception e) {

		}

		return ret;
	}
	
	public static Date addDateByYear(Date date, int val) {
		Date ret = null;

		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			cal.add(Calendar.YEAR, val);

			ret = cal.getTime();
		} catch (Exception e) {

		}

		return ret;
	}
	
	public static Date addLastTimeFromDate(Date date) {
		Date ret = null;

		try {
			ret = addTimeFromDate(date, 23, 59, 59, 59);
		} catch (Exception e) {

		}

		return ret;
	}

	public static long getLongDateWithoutTime(Date date) {
		long ret = 0;

		Date newDate = removeTimeFromDate(date);
		ret = newDate.getTime();

		return ret;
	}
	
	public static boolean isDataInList(List<?> list, String data) {
		boolean ret = false;

		try {
			if (StaticUtil.isListValid(list)) {
				for (Object x : list) {
					if (x.equals(data)) {
						ret = true;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return ret;
	}

	public static boolean isDataInList(List<?> list, Object data) {
		boolean ret = false;

		try {
			if (StaticUtil.isListValid(list)) {
				for (Object x : list) {
					if (x.equals(data)) {
						ret = true;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return ret;
	}

	public static Object[] getParameterNameListFromArray(String[] allFind,
			String name) {
		List<String> list = new ArrayList<String>();
		Object[] ret = null;

		try {
			int length = allFind.length;
			for (int i = 0; i < length; i++) {
				list.add(name + (i + 1));
			}

			ret = list.toArray();
		} catch (Exception e) {
			logger.error(e.getMessage());
			ret = null;
		}

		return ret;
	}

	public static boolean getBigDecimalCompareValue(BigDecimal bgdc,
			String compare, double val) {
		boolean ret = false;

		try {
			if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.EQUAL_TO)) {
				if (bgdc.compareTo(new BigDecimal(val)) == 0) {
					ret = true;
				}
			} else if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.MORE_THAN)) {
				if (bgdc.compareTo(new BigDecimal(val)) > 0) {
					ret = true;
				}
			} else if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.MORE_THAN_EQUAL_TO)) {
				if (bgdc.compareTo(new BigDecimal(val)) >= 0) {
					ret = true;
				}
			} else if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.LESS_THAN)) {
				if (bgdc.compareTo(new BigDecimal(val)) < 0) {
					ret = true;
				}
			} else if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.LESS_THAN_EQUAL_TO)) {
				if (bgdc.compareTo(new BigDecimal(val)) <= 0) {
					ret = true;
				}
			}
		} catch (Exception e) {

		}

		return ret;
	}

	public static boolean getBigDecimalCompareValue(BigDecimal bgdc,
			String compare, float val) {
		boolean ret = false;

		try {
			if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.EQUAL_TO)) {
				if (bgdc.compareTo(new BigDecimal(val)) == 0) {
					ret = true;
				}
			} else if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.MORE_THAN)) {
				if (bgdc.compareTo(new BigDecimal(val)) > 0) {
					ret = true;
				}
			} else if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.MORE_THAN_EQUAL_TO)) {
				if (bgdc.compareTo(new BigDecimal(val)) >= 0) {
					ret = true;
				}
			} else if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.LESS_THAN)) {
				if (bgdc.compareTo(new BigDecimal(val)) < 0) {
					ret = true;
				}
			} else if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.LESS_THAN_EQUAL_TO)) {
				if (bgdc.compareTo(new BigDecimal(val)) <= 0) {
					ret = true;
				}
			}
		} catch (Exception e) {

		}

		return ret;
	}

	public static boolean getBigDecimalCompareValue(BigDecimal bgdc,
			String compare, BigDecimal val) {
		boolean ret = false;

		try {
			if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.EQUAL_TO)) {
				if (bgdc.compareTo(val) == 0) {
					ret = true;
				}
			} else if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.MORE_THAN)) {
				if (bgdc.compareTo(val) > 0) {
					ret = true;
				}
			} else if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.MORE_THAN_EQUAL_TO)) {
				if (bgdc.compareTo(val) >= 0) {
					ret = true;
				}
			} else if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.LESS_THAN)) {
				if (bgdc.compareTo(val) < 0) {
					ret = true;
				}
			} else if (compare.trim().equalsIgnoreCase(
					Constantas.MathComparation.LESS_THAN_EQUAL_TO)) {
				if (bgdc.compareTo(val) <= 0) {
					ret = true;
				}
			}
		} catch (Exception e) {

		}

		return ret;
	}

	public static String getLocalHostIpAddress() {
		String ret = "";
		
		try {
			ret = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		}
		
		return ret;
	}
	
	public static String getMacAddress() {
		String ret = "";
		
		InetAddress ip;
		
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
				
			byte[] mac = network.getHardwareAddress();
				
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
			}
			
			ret = sb.toString();
				
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		} catch (SocketException e){
			logger.error(e.getMessage());
		}
		
		return ret;
	}
	
	public static String getLocalHostName() {
		String ret = "";
		
		try {
			ret = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		}
		
		return ret;
	}
	
	public boolean isUserActiveLoginState() {
		boolean ret = false;
		
		try {
			
		} catch (Exception e) {
			
		}
		
		return ret;
	}
	
	public static Integer getPageFromOffset(Integer offset, Integer maxResult) {
		Integer ret = 0;
		
		try {
			ret = (offset -1) * maxResult;
		} catch (Exception e) {
			
		}
		
		return ret;
	}
	
	public static int getOffsetByTotalPage(String action, Integer page, int count) {
		int ret = 0;
		
		try {
			page = page!=null?page:1;
			if (isStringCompared(action, "next")) {
				if ((page + 1) < count) {
					ret = page + 1;
				} else {
					ret = count;
				}
			} else if (isStringCompared(action, "prev")) {
				if ((page - 1) > 0) {
					ret = page -1;
				} else {
					ret = 1;
				}
			} else if (isStringCompared(action, "last")) {
				ret = count;
			} else if (isStringCompared(action, "first")) {
				ret = 1;
			} else {
				ret = page;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return ret;
	}
	
	public static String convertDateTimeToDateTime(String strDate) {
		String ret = "";
		
		try {
			String[] arrDate = strDate.split("[/]");
			ret = arrDate[2] + arrDate[1] + arrDate[0];
		} catch(Exception e) {
			ret = strDate;
		}
		
		return ret;
	}
	
	public static String[] splitString(String str, String delimiter) {
		String[] arr = null;
		
		try {
			arr = str.split("\\" + delimiter);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		
		return arr;
	}
	
	public static void main(String[] args) {
		try {
			Date dt = getDateFromString("2019-01-01", "yyyy-mm-dd", Locale.getDefault());
			System.out.println("date ya = " + dt);
			//			for (int i=1;i<1000;i++) {
//				String an = getAutoNumber("");
//				System.out.println(an);
//			}

//		for (int i=1;i<100;i++) {
//			String an = randomUUID(16, -1, 'a').toString();
//			System.out.println(an);
//		}

			
//			//Client ID And Client Secret
//			String clientId = "93c75837-f846-400a-9432-657107ba9938";
//			String clientSecret = "ec3bee1e-401d-4daf-9e4e-419d167bf2b8";
//			
//			String str = clientId + ":" + clientSecret;
//			
//			String x = Base64.getEncoder().encodeToString(str.getBytes());
//			System.out.println(x);
//			
//			//Timestamp
//			String tss = StaticUtil.getDateStringFormat(new Date(), "yyyy-MM-ddHH:mm:ss.SSSZD");
//			System.out.println("Current Time Stamp: " + tss);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public static String[] splitString(Date trxServer, String delimiter) {
		// TODO Auto-generated method stub
		String[] arr = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		String strDate = dateFormat.format(trxServer);  
		try {
			arr = strDate.split("\\" + delimiter);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		
		return arr;
	}
}
