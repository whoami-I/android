package com.example.parse_xml;

import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class WeatherParser {

	static ArrayList<Weather> parse(InputStream in) throws Exception {
		// ��ȡXmlParser��ʵ������
		XmlPullParser parser = Xml.newPullParser();
		ArrayList<Weather> weathers = null;
		Weather weather = null;
		parser.setInput(in, "utf-8");
		int type = parser.getEventType();
		// ���н���
		while (type != XmlPullParser.END_DOCUMENT) {
			// ����������ǩ��ͷ��β����һ���¼�
			switch (type) {
			// ��ǩ��ͷ�¼�
			case XmlPullParser.START_TAG:

				if ("weather".equals(parser.getName())) {
					weathers = new ArrayList<Weather>();
				} else if ("channel".equals(parser.getName())) {
					// ��ȡchannel������ֵ
					String id = parser.getAttributeValue(null, "id");
					weather = new Weather();
					weather.setId(id);
				} else if ("city".equals(parser.getName())) {
					// ��ȡ��ǩ������
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
			// ��һ����ǩ�¼�
			type = parser.next();
		}
		System.out.println("end of document");
		return weathers;

	}
}
