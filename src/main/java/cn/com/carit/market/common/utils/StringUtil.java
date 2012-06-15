package cn.com.carit.market.common.utils;



public class StringUtil {

	/**
	 * 将字段单词拆分，以"_"区分单词。如 updateTime，拆分后为update_time
	 * @param field
	 * @return
	 */
	public static String splitFieldWords(String field){
		StringBuilder str=new StringBuilder();
		char [] array=field.toCharArray();
		for (int i = 0; i < array.length; i++) {
			if (Character.isUpperCase(array[i])) {
				str.append("_").append(Character.toLowerCase(array[i]));
			} else {
				str.append(array[i]);
			}
		}
		return str.toString();
	}
	public static void main(String[] args) {
		System.out.println(splitFieldWords("updateTime"));
	}
	
}
