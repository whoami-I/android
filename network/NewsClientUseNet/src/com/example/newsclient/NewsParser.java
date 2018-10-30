package com.example.newsclient;

import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class NewsParser {

	static ArrayList<News> parse(InputStream in) {
		ArrayList<News> newsList = null;
		News news = null;
		try {
			XmlPullParser newsParser = Xml.newPullParser();
			newsParser.setInput(in, "utf-8");
			int event = newsParser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_TAG:
					if (newsParser.getName().equals("channel")) {
						newsList = new ArrayList<News>();
					} else if (newsParser.getName().equals("item")) {
						news = new News();
					} else if (newsParser.getName().equals("title")) {
						String title = newsParser.nextText();
						news.setTitle(title);
					} else if (newsParser.getName().equals("description")) {
						String description = newsParser.nextText();
						news.setContent(description);
					} else if (newsParser.getName().equals("image")) {
						String imagePath = newsParser.nextText();
						news.setImagePath(imagePath);
					}
					break;

				case XmlPullParser.END_TAG:
					if (newsParser.getName().equals("item")) {
						newsList.add(news);
					} else if (newsParser.getName().equals("channel")) {
						break;
					}
					break;
				}
				event = newsParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsList;

	}
}
