package com.KinFourGUtils.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 作者：KingGGG on 16/2/18 14:12
 * 描述：
 */
public class PinyinUtils {
    /**
     * 获取汉字字符串拼音
     *
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        String output = "";

        try {
            for (char cur_char : input) {
                if (java.lang.Character.toString(cur_char).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(cur_char, format);
                    output += temp[0];
                } else
                    output += java.lang.Character.toString(cur_char);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     *  获取汉字拼音首字母
     *
     */
    public static String getFirstSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char cur_char : arr) {
            if (cur_char > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(cur_char, defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(cur_char);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }
}
