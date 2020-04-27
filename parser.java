package parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		List<Article> articleList = new ArrayList<>();
		JSONObject jsonObj = new JSONObject();
		FileWriter writer = new FileWriter("habr.txt");
		FileWriter file = new FileWriter("habr.json");
		Document doc = Jsoup.connect("https://habr.com/ru/").get();


		Elements h2Elements = doc.getElementsByAttributeValue("class", "post__title");

		h2Elements.forEach(h2Element -> {
			Element aElement = h2Element.child(0);
			String url = aElement.attr("href");
			String title = aElement.text();
			articleList.add(new Article(url, title));
			jsonObj.put(title, url);
			try {
				writer.write(title + " : " + url + System.getProperty("line.separator"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		file.write(jsonObj.toJSONString());
		file.flush();
		writer.close();
		articleList.forEach(System.out::println);
	}
}

class Article{
	private String url;
	private String name;

	public Article(String url, String name) {
		this.url = url;
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return	name +
				" : " + url;
	}
}