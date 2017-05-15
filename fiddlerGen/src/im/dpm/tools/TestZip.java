package im.dpm.tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class TestZip {

	/**
	 * 方法描述:
	 * 
	 * @author Chenmin
	 * @param args
	 *            描述******
	 * @return void
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		String sazFile = "fanyi.saz";
		//ctp_sin_bkk
//		String sazFile = "e:\\ctp_sin_bkk.saz";
		String genRoot = "../fiddlerTest/src";
		String pack = "org.chenmin.login";
		String exStart="http://update.pan.baidu.com";
		String exEnd=".js,.css,.gzip,.gif,.jpg,.png,.jpeg";
		String sazFilename = sazFile.replace(".saz", "");
		
		
		String ps = pack.replace(".", "/");
		String pathname = genRoot+"/"+ps+"/";
		
		File root  = new File(genRoot);
		if(!root.exists()){
			root.mkdirs();
		}else{
			root = new File (pathname);
			if(!root.exists()){
				root.mkdirs();
			}
		}
		List<FiddlerBean> fbList = readZipFile(sazFile,exStart,exEnd);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("fbList", fbList);
		m.put("pack", pack);
		m.put("sazFilename", sazFilename);
		System.out.println(Templete.tempFile("TestFiddler.vm", m));
		Templete.tempFileToFile("CookiesHttpClient.vm", m, pathname+"CookiesHttpClient.java");
		Templete.tempFileToFile("HttpClientTools.vm", m, pathname+"HttpClientTools.java");
		Templete.tempFileToFile("TestFiddler.vm", m, pathname+sazFilename+"Test.java");
	}

	public static List<FiddlerBean> readZipFile(String file,String exStart,String exEnd) throws Exception {
		ZipFile zf = new ZipFile(file);
		InputStream in = new BufferedInputStream(new FileInputStream(file));
		ZipInputStream zin = new ZipInputStream(in);
		ZipEntry ze;
		List<FiddlerBean> fbList = new ArrayList<FiddlerBean>();
		while ((ze = zin.getNextEntry()) != null) {
			if (ze.isDirectory()) {
			} else {
				// System.out.println("file - " + ze.getName() + " : "
				// + ze.getSize() + " bytes");
				if (ze.getName().endsWith("_c.txt")) {
					FiddlerBean fb = processZipFile(zf, ze,exStart,exEnd);
					if (fb.getUrl() != null)
						fbList.add(fb);
				}
			}
		}
		zin.closeEntry();
		zin.close();
		zf.close();
		return fbList;
	}
	
	private static boolean isExEnd(String url,String expath){
		boolean isex = false;
		for(String ex:expath.split(",")){
			if(url.endsWith(ex)){
				isex = true;
				break;
			}
		}
		return isex;
	}
	
	private static boolean isExStart(String url,String expath){
		boolean isex = false;
		for(String ex:expath.split(",")){
			if(url.startsWith(ex)){
				isex = true;
				break;
			}
		}
		return isex;
	}

	private static FiddlerBean processZipFile(ZipFile zf, ZipEntry ze,String exStart,String exEnd)
			throws IOException {
		FiddlerBean fb = new FiddlerBean();
		InputStream inputStream = zf.getInputStream(ze);
		long size = ze.getSize();
		String text = getTextFromStream(inputStream, size);
		String s[] = split(text, "\n");
		String urlInfo[] = split(s[0], " ");
		String method = urlInfo[0];
		String url = urlInfo[1];
		if (!isExEnd(url,exEnd)&&!isExStart(url,exStart)) {
			System.out.println(method + " -> " + url);
			fb.setUrl(url);
			fb.setMethod(method);
			String param = null;
			if(method.equals("GET")){
				if(url.contains("?")){
					String[] us = url.split("\\?");
					fb.setUrl(us[0]);
					String params = us[1];
					Map<String, String> pm = getParamMap(params);
					if(pm.size()>0)
						fb.setParamMap(pm);
				}
			}else{
				param = s[s.length-1];
			}
			/*if (s.length >= 14) {
				param = s[14];
				// System.out.println(s[14]);
			}*/
			Map<String, String> m = getParam(s);
			fb.setSettings(m);
			String ContentType = "Content-Type";
			if (m.containsKey(ContentType)) {
				String type = m.get(ContentType);
				fb.setContentType(type);
				System.out.println(ContentType + " -> " + type);
				if (param != null) {
					if (type.contains("form")) {
						param = URLDecoder.decode(param, "UTF-8");
						Map<String, String> pm = getParamMap(param);
						if(pm.size()==1){
							if(pm.get(param)==null){
								System.out.println(param);
								fb.setParam(param.replace("\"", "\\\""));
							}else{
								fb.setParamMap(pm);
							}
						}else{
							fb.setParamMap(pm);
						}
					} else {
						System.out.println(param);
						fb.setParam(param.replace("\"", "\\\""));
					}
				}
			}
		}
		inputStream.close();
		return fb;
	}

	private static Map<String, String> getParam(String[] s) {
		Map<String, String> m = new HashMap<String, String>();
		for (String sa : s) {
			String sp = ": ";
			if (sa.contains(sp)) {
				String[] sap = sa.split(sp);
				String value = sap[1];
				value = value.replace("\"", "\\\"");
				m.put(sap[0], value);
			}
		}
		m.remove("Cookie");
		m.remove("Content-Length");
		return m;
	}

	private static Map<String, String> getParamMap(String s) {
//		System.out.println("s:"+s);
		Map<String, String> m = new HashMap<String, String>();
		for (String sa : s.split("&")) {
			String sp = "=";
//			System.out.println(sa);
			String[] sap = sa.split(sp);
			if(sap.length==2){
				m.put(sap[0], sap[1]);
			}else{
				m.put(sap[0],null);
			}
		}
		return m;
	}

	private static String[] split(String s, String sp) {
		return s.split(sp);
	}

	private static String getTextFromStream(InputStream inputStream, long size)
			throws IOException {
		String text = null;
		StringBuffer sb = new StringBuffer();
		if (size > 0) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			br.close();
			text = new String(sb.toString().getBytes(), "UTF-8");
		}
		return text;
	}

}
