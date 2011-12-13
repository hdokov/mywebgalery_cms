package com.mywebgalery.cms.model.audit;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

import com.mywebgalery.cms.utils.StringUtils;

public class AuditHelper {

	public static ThreadLocal<AuditHelper> LOCAL_INSTANCE = new ThreadLocal<AuditHelper>();

	public static String SEPARATOR = "|";

	public static String serializeRequest(Request req){
		StringBuffer b = new StringBuffer();
		b.append("request{url=");
		b.append(req.getServerName());
		b.append(req.getPath());
		b.append("}headers{");
		for(String header:req.getHeaderNames()){
			b.append(header);
			b.append("=");
			b.append(req.getHeader(header));
			b.append(SEPARATOR);
		}
		b.append("}params{");
		for(String param:req.getParameterNames()){
			b.append(param);
			b.append("=");
			b.append(req.getParameter(param));
			b.append(SEPARATOR);
		}
		b.append("}");
		return b.toString();
	}

	@SuppressWarnings("unchecked")
	public static String serializeRequest(HttpServletRequest req){
		StringBuffer b = new StringBuffer();
		b.append("request{url=");
		b.append(req.getRequestURL());
		b.append("|ip=");
		b.append(req.getRemoteAddr());
		b.append("}headers{");
		Enumeration<String> h = req.getHeaderNames();
		while(h.hasMoreElements()){
			String header = h.nextElement();
			b.append(header);
			b.append("=");
			b.append(req.getHeader(header));
			b.append(SEPARATOR);
		}
		b.append("}params{");
		Enumeration<String> p = req.getParameterNames();
		while(p.hasMoreElements()){
			String param = p.nextElement();
			b.append(param);
			b.append("=");
			b.append(req.getParameter(param));
			b.append(SEPARATOR);
		}
		b.append("}");
		return b.toString();
	}

	@SuppressWarnings("unchecked")
	public static String serializeForEmailRequest(HttpServletRequest req){
		StringBuffer b = new StringBuffer("<html>");
		b.append("<b>request</b><br/> url=");
		b.append(req.getRequestURL());
		b.append("<br/> ip=");
		b.append(req.getRemoteAddr());
		b.append("<br/><br/><b>headers</b><br/>");
		Enumeration<String> h = req.getHeaderNames();
		while(h.hasMoreElements()){
			String header = h.nextElement();
			b.append(header);
			b.append("=");
			b.append(req.getHeader(header));
			b.append("<br/>");
		}
		b.append("<br/><b>params</b><br/>");
		Enumeration<String> p = req.getParameterNames();
		while(h.hasMoreElements()){
			String param = p.nextElement();
			b.append(param);
			b.append("=");
			b.append(req.getParameter(param));
			b.append("<br/>");
		}
		return b.toString();
	}

	public static Map<String, Object> serializeRequestForHoptoad(Request req){
		Map<String, Object> b = new HashMap<String, Object>();
		for(String param:req.getParameterNames()){
			b.put(param,req.getParameter(param));
		}
		return b;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> serializeRequestForHoptoad(HttpServletRequest req){
		Map<String, Object> b = new HashMap<String, Object>();
		Enumeration<String> p = req.getParameterNames();
		while(p.hasMoreElements()){
			String param = p.nextElement();
			b.put(param,req.getParameter(param));
		}
		return b;
	}

	public static Map<String, Object> serializeHeadersForHoptoad(Request req){
		Map<String, Object> b = new HashMap<String, Object>();
		b.put("url",req.getServerName()+req.getPath());
		for(String header:req.getHeaderNames()){
			b.put(header, req.getHeader(header));
		}
		return b;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> serializeHeadersForHoptoad(HttpServletRequest req){
		Map<String, Object> b = new HashMap<String, Object>();
		b.put("url",req.getRequestURL());
		b.put("ip", req.getRemoteAddr());
		Enumeration<String> h = req.getHeaderNames();
		while(h.hasMoreElements()){
			String header = h.nextElement();
			b.put(header, req.getHeader(header));
		}
		return b;
	}


	public static Map<String, Object> serializeSessionForHoptoad(Session s){
		Map<String, Object> b = new HashMap<String, Object>();
//		for(String attr:s.getAttributeNames()){
//			b.put(attr, s.getAttribute(attr));
//		}
		return b;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> serializeSessionForHoptoad(HttpSession s){
		Map<String, Object> b = new HashMap<String, Object>();
		Enumeration<String> h = s.getAttributeNames();
		while(h.hasMoreElements()){
			String attr = h.nextElement();
			b.put(attr, s.getAttribute(attr));
		}
		return b;
	}


	public static Map<String, Map<String, String>> deserializeRequest(String req){
		Map<String, Map<String, String>> r = new HashMap<String, Map<String,String>>(3);
		Map<String, String> map = new HashMap<String, String>(2);
		req = req.substring(req.indexOf('{'));
		String s = req.substring(1,req.indexOf('}'));
		String[] ss = s.split("\\|");
		for(String prop:ss){
			if(!StringUtils.isEmpty(prop)){
				String[] p = prop.split("=");
				if(p.length>1){
					map.put(p[0], p[1]);
				}
			}
		}
		r.put("request", map);
		map = new HashMap<String, String>();
		req = req.substring(req.indexOf('{', 2));
		s = req.substring(1,req.indexOf('}'));
		ss = s.split("\\|");
		for(String prop:ss){
			if(!StringUtils.isEmpty(prop)){
				int index = prop.indexOf('=');
				if(index > 0)
					map.put(prop.substring(0,index), prop.substring(index+1));

			}
		}
		r.put("headers", map);
		map = new HashMap<String, String>();
		req = req.substring(req.indexOf('{', 2));
		s = req.substring(1,req.indexOf('}'));
		ss = s.split("\\|");
		for(String prop:ss){
			if(!StringUtils.isEmpty(prop)){
				int index = prop.indexOf('=');
				if(index > 0)
					map.put(prop.substring(0,index), prop.substring(index+1));
			}
		}
		r.put("params", map);
		return r;
	}

	private String username;
	private String request;
	private String pageName;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}


}
