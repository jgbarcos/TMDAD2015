package es.unizar.tmdad.lab0.service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import es.unizar.tmdad.lab0.model.PieceOfBook;

public class SerarchService {

	public static String getUrlFromId(String bookId) {
		return "http://www.gutenberg.org/cache/epub/" + bookId + "/pg" + bookId + ".txt";
	}
	
	public static String getPathBook(String bookId) {
		return System.getProperty("java.io.tmpdir") + "/" + bookId + ".txt";
	}

	public static ArrayList<PieceOfBook> getBook(String bookId, int piece) {
		downloadBookFromUrl(getUrlFromId(bookId), bookId);
		return getBook(getPathBook(bookId), bookId, piece);

	}

	/**
	 * Download the file from url and save it in the folder "books"
	 * 
	 * @param url
	 * @param bookId
	 */
	public static boolean downloadBookFromUrl(String url, String bookId) {
		URL website = null;
		FileOutputStream fos = null;
		try {
			website = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			fos = new FileOutputStream(getPathBook(bookId));
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	public static ArrayList<PieceOfBook> getBook(String bookPath, String bookId, int piece) {
		try {
			BufferedReader br = null;
			String book = "";
			br = new BufferedReader(new FileReader(getPathBook(bookId)));
			String line = null;
			while ((line = br.readLine()) != null) {
				book+=line;
			}
			
			PieceOfBook  pieceOfBook = new PieceOfBook(bookId, book, 1, 1);
			ArrayList<PieceOfBook> lst = new ArrayList<>();
			lst.add(pieceOfBook);
			return  lst;
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<PieceOfBook> ();
		}

	}
}
