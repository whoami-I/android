package com.example.parse_xml;

import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class WeatherParser {

	static ArrayList<Weather> parse(InputStream in) throws Exception {
		// 获取XmlParser的实例对象
		XmlPullParser parser = Xml.newPullParser();
		ArrayList<Weather> weathers = null;
		Weather weather = null;
		parser.setInput(in, "utf-8");
		int type = parser.getEventType();
		// 进行解析
		while (type != XmlPullParser.END_DOCUMENT) {
			// 解析遇到标签的头或尾会有一个事件
			switch (type) {
			// 标签的头事件
			case XmlPullParser.START_TAG:

				if ("weather".equals(parser.getName())) {
					weathers = new ArrayList<Weather>();
				} else if ("channel".equals(parser.getName())) {
					// 获取channel的属性值
					String id = parser.getAttributeValue(null, "id");
					weather = new Weather();
					weather.setId(id);
				} else if ("city".equals(parser.getName())) {
					// 获取标签的内容
					String city = parser.nextText();
					weather.setCity(city);
				} else if ("temp".equals(parser.getName())) {
					String temp = parser.nextText();
					weather.setTemp(temp);
				} else if ("wind".equals(parser.getName())) {
					String wind = parser.nextText();
					weather.setWind(wind);
				} else if ("pm250".equals(parser.getName())) {
					String pm250 = parser.nextText();
					weather.setPm250(pm250);
				}

				break;
			case XmlPullParser.END_TAG:
				if ("weather".equals(parser.getName())) {
					break;
				} else if ("channel".equals(parser.getName())) {
					weathers.add(weather);
				}
				break;
			}
			// 下一个标签事件
			type = parser.next();
		}
		System.out.println("end of document");
		return weathers;

	}
}
