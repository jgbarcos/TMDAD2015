package tokenizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import es.unizar.tmdad.service.BookRaw;
import es.unizar.tmdad.service.BookTokenized;
import es.unizar.tmdad.service.Chapter;
import es.unizar.tmdad.service.Token;
import es.unizar.tmdad.tokenizer.Counter;
import es.unizar.tmdad.tokenizer.Tokenizer;

/**
 * 
 * @author erremesse1
 *	This class tests the operation of the Tokenizer itself, this is, 
 *	the process it should follows after a /tokenize request.
 *	It doesn't test the REST service implemented.
 */
public class TestTokenizer {
	
	@Test
	public void testTokenizer(){
		BookRaw bookRaw = createBookRaw();
		//Terms
		List<String> terms = createTerms();
		
		//BookRaw -> BookTokenized
		System.out.println("BOOK CONTENT: " + bookRaw.getContent().substring(0, 200) + "...");
		System.out.println(terms);
			
		long startTime = System.currentTimeMillis();
		BookTokenized bookTok = new BookTokenized(bookRaw.getId());
			try{
				Tokenizer tokenizer = new Tokenizer(bookRaw.getContent());
				Map<String, String> chapters = tokenizer.tokenize();
				Counter counter = new Counter(bookTok, chapters, terms);
				bookTok = counter.countTerms();
			}catch(IOException ioex){
				//TODO It should return some standard error
				Assert.fail(ioex.getMessage());
			}
		Assert.assertNotNull(bookTok);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("===> It tooks " + elapsedTime + " miliseconds to tokenize the book.");
		checkResults(bookTok);
	}
	
	private BookRaw createBookRaw(){
		//Load book from file and create fake BookRaw
		BookRaw bookRaw = null;
		FileInputStream fstream;
		try {
			fstream = new FileInputStream("src\\test\\resources\\Alice's Adventures in Wonderland");
		
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while(( line = br.readLine()) != null ) {
				sb.append( line );
				sb.append( '\n' );
			}
			String content = sb.toString();
			bookRaw = new BookRaw(123, content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookRaw;
	}
	
	private List<String> createTerms(){
		String termsStr = "Alice,curious,cats";
		List<String> terms = new ArrayList<>();
		terms = Arrays.asList(termsStr.split(","));
		return terms;
	}
	
	private void checkResults(BookTokenized bookTok){
		System.out.println("*** BOOK TOKENIZED ***");
		System.out.println("Number of chapters: " + bookTok.getChapters().size());
		List<Chapter> chapters = bookTok.getChapters();
		for(Chapter chapter:chapters){
			System.out.println(chapter.getTitle());
			List<Token> tokens = chapter.getTokens();
			System.out.println("\tTokens:");
			for(Token token:tokens){
				System.out.println("\t\t" + token.getWord() + "-" + token.getCount());
			}
		}
	}
}
