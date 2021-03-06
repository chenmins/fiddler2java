package org.chenmin.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientTools {
	
	public static boolean useProxy = false;
	
	public static RequestConfig getConfig(){
		if(useProxy==false){
			return null;
		}
		HttpHost proxy = new HttpHost("localhost", 8888);
        RequestConfig config = RequestConfig.custom()
            .setProxy(proxy)
            .build();
        return config;
	}
	
	public static String get(HttpClient httpclient, String url,
			Map<String, String> settings) throws ClientProtocolException,
			IOException {
		HttpGet httpGet = new HttpGet(url);
		RequestConfig config = getConfig();
		if(config!=null)
			httpGet.setConfig(config);
		for (Map.Entry<String, String> entry : settings.entrySet()) {
			httpGet.setHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse response1 = (CloseableHttpResponse) httpclient
				.execute(httpGet);
		try {
			HttpEntity entity1 = response1.getEntity();
			String body = EntityUtils.toString(entity1);
			EntityUtils.consume(entity1);
			return body;
		} finally {
			response1.close();
		}
	}
	
	public static String get(HttpClient httpclient, String url,
			Map<String, String> param, Map<String, String> settings) throws ClientProtocolException,
			IOException {
		boolean first = true;
		for (Map.Entry<String, String> entry : param.entrySet()) {
			if(first){
				url+="?";
				first = false;
			}else{
				url+="&";
			}
			url+=entry.getKey();
			url+="=";
			url+=entry.getValue();
		}	
		HttpGet httpGet = new HttpGet(url);
		RequestConfig config = getConfig();
		if(config!=null)
			httpGet.setConfig(config);
		for (Map.Entry<String, String> entry : settings.entrySet()) {
			httpGet.setHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse response1 = (CloseableHttpResponse) httpclient
				.execute(httpGet);
		try {
			HttpEntity entity1 = response1.getEntity();
			String body = EntityUtils.toString(entity1);
			EntityUtils.consume(entity1);
			return body;
		} finally {
			response1.close();
		}
	}

	public static String post(HttpClient httpclient, String url, String param,
			Map<String, String> settings) throws ClientProtocolException,
			IOException {
		HttpPost httpPost = new HttpPost(url);
		RequestConfig config = getConfig();
		if(config!=null)
			httpPost.setConfig(config);
		for (Map.Entry<String, String> entry : settings.entrySet()) {
			httpPost.setHeader(entry.getKey(), entry.getValue());
		}
		httpPost.setEntity(EntityBuilder.create().setText(param).build());
		CloseableHttpResponse response1 = (CloseableHttpResponse) httpclient
				.execute(httpPost);
		try {
			HttpEntity entity1 = response1.getEntity();
			String body = EntityUtils.toString(entity1);
			EntityUtils.consume(entity1);
			return body;
		} finally {
			response1.close();
		}
	}

	public static String postForm(HttpClient httpclient, String url,
			Map<String, String> param, Map<String, String> settings)
			throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		RequestConfig config = getConfig();
		if(config!=null)
			httpPost.setConfig(config);
		for (Map.Entry<String, String> entry : settings.entrySet()) {
			httpPost.setHeader(entry.getKey(), entry.getValue());
		}
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : param.entrySet()) {
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response1 = (CloseableHttpResponse) httpclient
				.execute(httpPost);
		try {
			HttpEntity entity1 = response1.getEntity();
			String body = EntityUtils.toString(entity1);
			EntityUtils.consume(entity1);
			return body;
		} finally {
			response1.close();
		}
	}
	
	public static String postJson(HttpClient httpclient, String url,
			String param, Map<String, String> settings)
			throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		RequestConfig config = getConfig();
		if(config!=null)
			httpPost.setConfig(config);
		for (Map.Entry<String, String> entry : settings.entrySet()) {
			httpPost.setHeader(entry.getKey(), entry.getValue());
		}
		
		httpPost.setEntity(new StringEntity(param));
		CloseableHttpResponse response1 = (CloseableHttpResponse) httpclient
				.execute(httpPost);
		try {
			HttpEntity entity1 = response1.getEntity();
			String body = EntityUtils.toString(entity1);
			EntityUtils.consume(entity1);
			return body;
		} finally {
			response1.close();
		}
	}
	
	public static void write(String file,String text){
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(text);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public static String read(String _file) {
		File file = new File(_file);
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
//			int line = 1;
			// 一次读一行，读入null时文件结束
			while ((tempString = reader.readLine()) != null) {
				// 把当前行号显示出来
//				System.out.println("line " + line + ": " + tempString);
				sb.append(tempString);
//				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}
	

}
