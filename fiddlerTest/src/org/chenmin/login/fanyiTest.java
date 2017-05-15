package org.chenmin.login;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.cookie.Cookie;

/**
 * 
 * @author Chenmin
 *
 */
public class fanyiTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		CookiesHttpClient chc = new CookiesHttpClient();
		CookieStore cookieStore = chc.getCookieStore();
		HttpClient httpclient = chc.get();
		//config proxy in HttpClientTools.java
		//HttpClientTools.useProxy = true;
//gen --- > begin
				
		String url1 = "http://fanyi.baidu.com/sug";
		Map<String, String> s1 = new HashMap<String, String>();
		s1.put("Accept", "application/json, text/javascript, */*; q=0.01");
		s1.put("X-Requested-With", "XMLHttpRequest");
		s1.put("Referer", "http://fanyi.baidu.com/");
		s1.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393");
		s1.put("Connection", "Keep-Alive");
		s1.put("Host", "fanyi.baidu.com");
		s1.put("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5");
		s1.put("Accept-Encoding", "gzip, deflate");
		s1.put("Pragma", "no-cache");
		s1.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				
							
		String p1 = "kw=China";
		String r1 = HttpClientTools.post(httpclient, url1, p1, s1);	
						
				
		//Write File for debug
		//HttpClientTools.write("r1.html",r1);
		System.out.println(r1);
//gen --- > end
		printlnCookie(cookieStore);
		chc.close();
	}
	
	private static void printlnCookie(CookieStore cookieStore) {
		for(Cookie cookie:cookieStore.getCookies()){
			System.out.println(cookie);
		}
	}

}
