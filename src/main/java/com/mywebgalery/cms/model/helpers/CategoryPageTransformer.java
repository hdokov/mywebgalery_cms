package com.mywebgalery.cms.model.helpers;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.transform.ResultTransformer;

@SuppressWarnings("serial")
public class CategoryPageTransformer implements ResultTransformer {

	//private Map<Long, CategoryPageBean> _map = new HashMap<Long, CategoryPageBean>();
	//private CategoryPageBean _root;

	public CategoryPageTransformer() {
	}

	public CategoryPageTransformer(CategoryPageBean root) {
		//_root = root;
	}

	@SuppressWarnings("unchecked")
	public List<CategoryPageBean> transformList(List collection) {
		return collection;
	}

	public CategoryPageBean transformTuple(Object[] tuple, String[] aliases) {
		CategoryPageBean cpb = new CategoryPageBean();
		if(tuple[2] == null)
			return null;

		if(tuple[4] != null){
			cpb.setParent(((BigInteger)tuple[4]).longValue());
		}
		cpb.setCategoryId(((BigInteger)tuple[2]).longValue());
		cpb.setCategoryName(String.valueOf(tuple[3]));
		if(tuple[0] != null){
			cpb.setPageUrl(String.valueOf(tuple[0]));
			cpb.setPageName(String.valueOf(tuple[1]));
		}
		//addCpb(cpb);
		return cpb;
	}

//	private void addCpb(CategoryPageBean cpb){
//		CategoryPageBean c = _map.get(cpb.getCategoryId());
//		if(c == null){
//			_map.put(c.getCategoryId(), cpb);
//			if(c.getPageUrl() != null){
//				c = new CategoryPageBean();
//				c.setCategoryId(cpb.getCategoryId());
//				c.setCategoryName(cpb.getCategoryName());
//				c.setParent(cpb.getParent());
//				c.getPages().add(cpb);
//			} else {
//				c = cpb;
//			}
//		} else {
//			c.getPages().add(cpb);
//		}
//
//	}

}
