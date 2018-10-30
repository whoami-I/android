package com.example.newsclient;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class DataParser {
	public static ArrayList<News> parse(String data) {
		ArrayList<News> list = new ArrayList<News>();

		Pattern pattern = Pattern
				.compile("<a target=\"_blank\" class=\"pic\" href=\"([^\"]*)\"><img class=\"picto\" src=\"([^\"]*)\"></a><em class=\"f14 l24\"><a target=\"_blank\" class=\"linkto\" href=\"[^\"]*\">([^</a>]*)</a></em><p class=\"l22\">([^</p>]*)</p>");
		Matcher matcher = pattern.matcher(data);

		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			News news = new News();
			news.setImagePath(matcher.group(2).trim());
			news.setTitle(matcher.group(3).trim());
			news.setContent(matcher.group(4).trim());

			sb.append("详情页地址：" + matcher.group(1).trim() + "\n");
			sb.append("图片地址：" + matcher.group(2).trim() + "\n");
			sb.append("标题：" + matcher.group(3).trim() + "\n");
			sb.append("概要：" + matcher.group(4).trim() + "\n\n");

			list.add(news);
		}

		Log.e("----------------->", sb.toString());

		return list;
	}

}
