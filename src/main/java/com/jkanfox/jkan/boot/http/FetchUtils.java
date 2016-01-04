package com.jkanfox.jkan.boot.http;

import java.io.IOException;
import java.io.Serializable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.common.base.Preconditions;

/**
 * URL自动抓取工具类
 * 
 * @author larry.qi
 */
public final class FetchUtils {
	
	// 设置User_agent
	static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36";
	
	// 抓取超时时间
	static final int TIMEOUT = 5000;
	
	/**
	 * 连接并返回JSON格式的数据
	 * @param url
	 * @return
	 */
	public static WebModel connect(String url) {
		long start = System.currentTimeMillis();
		WebModel wm = new FetchUtils().new WebModel(url);

		try {
			Preconditions.checkNotNull(url, "URL不能为null.");
			
			Document document = build(url);
			wm.setTitle(document.title());
			Element metaKey =  document.select("meta[name=keywords]").first();
			Element metaDesc = document.select("meta[name=description]").first();
			
			if (metaKey != null) {
				wm.setKeywords(metaKey.attr("content"));
			}

			if (metaDesc != null) {
				wm.setDescription(metaDesc.attr("content"));
			}
		} catch (IOException e) {
			wm.setSuccess(false);
		}
		
		wm.setFetchTime(System.currentTimeMillis() - start);
		return wm;
	}
	
	/**
	 * 基于JSOUP的方式获取网页内容
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private static Document build(String url) throws IOException {
		try {
			return Jsoup.connect(url)
					.userAgent(USER_AGENT)
					.timeout(TIMEOUT)
					.followRedirects(true)
					.ignoreContentType(true)
					.ignoreHttpErrors(true)
					.get();
		} catch (IOException e) {
			return Jsoup.parse(HttpUtils.get(url));
		}
	}
	
	public class WebModel implements Serializable {
		private static final long serialVersionUID = 1L;
		private String url; // URL
		private String title; // 标题
		private String keywords; // 关键词
		private String description; // 描述信息
		private long fetchTime; // 完成时长[ms]
		private boolean success; // 是否抓取成功

		public WebModel() {
			super();
			success = Boolean.TRUE;
		}

		public WebModel(String url) {
			this();
			this.url = url;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getKeywords() {
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public long getFetchTime() {
			return fetchTime;
		}

		public void setFetchTime(long fetchTime) {
			this.fetchTime = fetchTime;
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

	}

}
