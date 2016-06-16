package com.KinFourGUtils.utils;

import com.KinFourGUtils.bean.ContactsMemberBean;

import java.util.Comparator;

public class PinyinComparator implements Comparator<ContactsMemberBean> {

	@Override
	public int compare(ContactsMemberBean lhs, ContactsMemberBean rhs) {
		return sort(lhs, rhs);
	}

	private int sort(ContactsMemberBean lhs, ContactsMemberBean rhs) {
		int lhs_ascii = lhs.getFirst_PinYin().toUpperCase().charAt(0);
		int rhs_ascii = rhs.getFirst_PinYin().toUpperCase().charAt(0);
		if (lhs_ascii < 65 || lhs_ascii > 90)
			return 1;
		else if (rhs_ascii < 65 || rhs_ascii > 90)
			return -1;
		else
			return lhs.getFull_PinYin().compareTo(rhs.getFull_PinYin());
	}

}
