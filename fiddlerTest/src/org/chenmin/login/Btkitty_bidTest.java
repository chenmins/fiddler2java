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
public class Btkitty_bidTest {

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
				
		String url1 = "http://btkitty.bid/";
		Map<String, String> s1 = new HashMap<String, String>();
		s1.put("Upgrade-Insecure-Requests", "1");
		s1.put("Host", "btkitty.bid");
		s1.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		s1.put("Origin", "http://btkitty.bid");
		s1.put("Connection", "keep-alive");
		s1.put("Cache-Control", "max-age=0");
		s1.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		s1.put("Referer", "http://btkitty.bid/");
		s1.put("Accept-Encoding", "gzip, deflate");
		s1.put("Content-Type", "application/x-www-form-urlencoded");
		s1.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				
								
		Map<String, String> p1 = new HashMap<String, String>();
		p1.put("hidden", "true");
		p1.put("keyword", "小李飞刀");
				
		String r1 = HttpClientTools.postForm(httpclient, url1, p1, s1);	
		
						
				
		//Write File for debug
		//HttpClientTools.write("r1.html",r1);
		System.out.println(r1);
				
		String url2 = "http://btkitty.bid/search/e7qh_9ncvpeL5z3taAAA/1/0/0.html";
		Map<String, String> s2 = new HashMap<String, String>();
		s2.put("Upgrade-Insecure-Requests", "1");
		s2.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		s2.put("Host", "btkitty.bid");
		s2.put("Accept-Encoding", "gzip, deflate, sdch");
		s2.put("Referer", "http://btkitty.bid/");
		s2.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		s2.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		s2.put("Connection", "keep-alive");
		s2.put("Cache-Control", "max-age=0");
				
								
		String r2 = HttpClientTools.get(httpclient, url2, s2);
		
						
				
		//Write File for debug
		HttpClientTools.write("r2.html",r2);
		System.out.println(r2);
				
		String url3 = "http://btkitty.bid/torrent/BcEBDQAwCAMwS2fwwOUsDPxLeJt9J1eU75DC8bAuGZDPqsAN5G19.html";
		Map<String, String> s3 = new HashMap<String, String>();
		s3.put("Upgrade-Insecure-Requests", "1");
		s3.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		s3.put("Host", "btkitty.bid");
		s3.put("Accept-Encoding", "gzip, deflate, sdch");
		s3.put("Referer", "http://btkitty.bid/search/e7qh_9ncvpeL5z3taAAA/1/0/0.html");
		s3.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		s3.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		s3.put("Connection", "keep-alive");
				
								
		String r3 = HttpClientTools.get(httpclient, url3, s3);
		
						
				
		//Write File for debug
		HttpClientTools.write("r3.html",r3);
		System.out.println(r3);
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
