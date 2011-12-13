package com.mywebgalery.cms.base;

import java.net.URL;

import com.mywebgalery.cms.utils.ConfigMngr;


public class ClientBasePage extends BasePage {

	private String _appName;

	public boolean isAdminApp(){
		return getShopName().equals(ConfigMngr.getAdminAppName());
	}

	protected String getShopName(){
		if(_appName == null){
			try{
				URL url = new URL(getRequest().getHTTPServletRequest().getRequestURL().toString());
				String host = url.getHost();
				if(host.startsWith("www."))
					host = host.substring(4);
				int dot = host.indexOf('.');
				if(dot > -1)
					_appName = host.substring(0,dot);
				else
					_appName = host;
			} catch (Exception e) {
				getLog().error(e.getMessage(), e);
				_appName = "mywebgalery";
			}
		}
		return _appName;
	}

//	public Shop getShop(){
//		if(_shop == null){
//			Session s = getTransactionManager().getSession();
//			s.beginTransaction();
//			try {
//				_shop = ShopDao.getInstance().get(s, getShopName());
//				if(_shop == null)
//					_shop = ShopDao.getInstance().get(s, 0L);
//			} catch (Exception e) {
//				getLog().error(e.getMessage(), e);
//				_shop = new Shop();
//				_shop.setName("unknown");
//				_shop.setTitle("Unknown");
//				_shop.setDescr("My Web Galery. Unknown shop.");
//				_shop.setKeywords(translate("default_keywords"));
//			}
//		}
//		return _shop;
//	}


//	public Client getClient(){
//		if(_client == null){
//			_client = getSessionData().getClient();
//			if(_client == null){
//				Cookie[] cookies = _request.getHTTPServletRequest().getCookies();
//				if(cookies != null)
//					for(Cookie c:cookies){
//						if("shop_client".equals(c.getName())){
//							Session s = getTransactionManager().getSession();
//							s.beginTransaction();
//							try {
//								_client = ClientUtils.getClient(s, c);
//							} catch (Exception e) {
//								getLog().error(e.getMessage(), e);
//								addErrMsg("Session expired.", null);
//							}
//						}
//					}
//			}
//			if(_client == null)
//				_client = new Client();
//			getSessionData().setClient(_client);
//		}
//		return _client;
//	}



}
