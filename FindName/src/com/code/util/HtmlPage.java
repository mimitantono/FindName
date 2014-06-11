/**
 * Name: HtmlPage
 * @author mimitantono
 * Purpose: 
 * Version information: 7:48:24 pm
 * Date: 10 Jun, 2014
 * Copyright notice: 
 */
package com.code.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.text.BadLocationException;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * This class can be set with either a URL or directly with the HTML content.<br/>
 * To generate plain content, it's using Apache Tika.
 * 
 * @author mimitantono
 * 
 */
public class HtmlPage {

	private static Logger log = Logger.getLogger(HtmlPage.class.getName());
	private String urlString;
	private String paramString;
	private String htmlContent;
	private String plainContent;

	public HtmlPage(String urlString) {
		this.urlString = urlString;
	}

	/**
	 * Create an instance of this class without specifying URL, to be used in
	 * unit tests
	 */
	public HtmlPage() {
	}

	public String getHtmlContent() throws IOException {
		// Lazy initialization of HTML content from submitted URL
		if (htmlContent == null) {
			createRequest();
		}
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	/**
	 * Method to parse HTML content into plain text
	 * 
	 * @throws TikaException
	 * @throws SAXException
	 * @throws IOException
	 * @throws BadLocationException
	 */
	private void parse() throws IOException, SAXException, TikaException,
			BadLocationException {
		// Remove all new lines in HTML content to give more accurate result
		htmlContent = getHtmlContent().replaceAll("\\r?\\n", "");
		
		//Calling parser from Apache Tika
		InputStream input = new ByteArrayInputStream(htmlContent.toString()
				.getBytes());
		ContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();

		ParseContext parseContext = new ParseContext();
		new AutoDetectParser().parse(input, handler, metadata, parseContext);
		plainContent = handler.toString();
	}

	public void createRequest() throws IOException {
		//ignore SSL check for HTTPS connection
		installTrustAllManager();

		log.info("url : " + urlString);
		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();

		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		if (paramString != null) {
			wr.write(paramString);
		}
		wr.flush();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String line;
		htmlContent = "";
		while ((line = rd.readLine()) != null) {
			htmlContent += line;
		}

		wr.close();
		rd.close();
	}

	/**
	 * Create a trust manager that does not validate certificate chains
	 */
	public static void installTrustAllManager() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			log.error(Trace.getStackTrace(e));
		}
	}

	/**
	 * @return the plainContent
	 * @throws BadLocationException
	 * @throws TikaException
	 * @throws SAXException
	 * @throws IOException
	 */
	public String getPlainContent() throws IOException, SAXException,
			TikaException, BadLocationException {
		if (plainContent == null) {
			parse();
		}
		return plainContent;
	}

	/**
	 * @param plainContent
	 *            the plainContent to set
	 */
	public void setPlainContent(String plainContent) {
		this.plainContent = plainContent;
	}
}
