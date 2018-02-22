package acclickdata;

import java.io.Serializable;
import java.util.Locale;

public class EssLocale implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3554178987826839886L;

	private Locale m_locale;
	
	public static final EssLocale US = new EssLocale(Locale.US);
	
	public static final EssLocale ROOT = new EssLocale(Locale.ROOT);
	
	public static final EssLocale ENGLISH = new EssLocale(Locale.ENGLISH);
	
	public static final EssLocale UK = new EssLocale(Locale.UK);
	
	public static final EssLocale FRANCE = new EssLocale(Locale.FRANCE);
	
	public static final EssLocale CANADA = new EssLocale(Locale.CANADA);
	
	public static final EssLocale JAPAN = new EssLocale(Locale.JAPAN);
	
	public static final EssLocale GERMANY = new EssLocale(Locale.GERMANY);
	
	public static final EssLocale CHINESE = new EssLocale(Locale.CHINESE);
	
	public static Locale[] getAvailableLocales(){
		return Locale.getAvailableLocales();
	}
	
	public static EssLocale valueOf(String str){
		return new EssLocale(str);
	}
	
	public EssLocale(Locale loc){
		m_locale = loc;
	}
	
	public EssLocale(String lang, String country){
		m_locale = new Locale(lang, country);
	}
	
	public EssLocale(String loc){
		String[] parsed = loc.split("_");
		
		m_locale = new Locale(parsed[0],
						parsed.length > 1 ? parsed[1] : "",
						parsed.length > 2 ? parsed[2] : ""
				);
	}
	
	@Override
	public String toString(){
		String str = m_locale.toString();
		if("in_ID".equals(str)){
			return "id_ID";
		} else {
			return str;
		}
	}
	
	public Locale getLocale(){
		return m_locale;
	}

	public static EssLocale getDefault() {
		return new EssLocale(Locale.getDefault());
	}
	
	public String getCountry(){
		return m_locale.getCountry();
	}
	
	public String getLanguage(){
		String lang = m_locale.getLanguage();
		if("in".equals(lang)){
			return "id";
		}
		return m_locale.getLanguage();
	}
		
	@Override
	public boolean equals(Object o){
		if(o == null){
			return false;
		}
		if(o instanceof EssLocale){
			Locale other = ((EssLocale)o).getLocale();
			return m_locale.equals(other);	
		}
		if(o instanceof String){
			String[] str = ((String) o).split("_");
			Locale loc = new Locale(str[0],
					str.length > 1 ? str[1] : "",
					str.length > 2 ? str[2] : "");
			return m_locale.equals(loc);
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return m_locale.hashCode();
	}
}

