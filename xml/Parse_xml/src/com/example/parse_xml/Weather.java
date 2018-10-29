package com.example.parse_xml;

public class Weather {

	private String temp;
	private String wind;
	private String pm250;
	private String city;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getPm250() {
		return pm250;
	}

	public void setPm250(String pm250) {
		this.pm250 = pm250;
	}

	@Override
	public String toString() {
		return "Weather [temp=" + temp + ", wind=" + wind + ", pm250=" + pm250
				+ ", city=" + city + ", id=" + id + "]";
	}
}
