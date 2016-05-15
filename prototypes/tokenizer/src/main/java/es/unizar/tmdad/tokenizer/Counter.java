package es.unizar.tmdad.tokenizer;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import es.unizar.tmdad.service.BookTokenized;
import es.unizar.tmdad.service.Chapter;
import es.unizar.tmdad.service.Token;

public class Counter {
	private BookTokenized bookTok;
	private Map<String, String> chapterMap;
	List<String> terms;
	
	public Counter(BookTokenized bookTok, Map<String, String> chapterMap, List<String> terms){
		this.bookTok = bookTok;
		this.chapterMap = chapterMap;
		this.terms = terms;
	}
	
	public BookTokenized countTerms(){
	//Just to test
		int i = 0;
		for (Map.Entry<String, String> entry : chapterMap.entrySet()) {
			String cTitle = entry.getKey();
			String cContent = entry.getValue();
			if(cTitle.equals("Title")){
				bookTok.setTitle(cContent);
			}else{
				Chapter chapter = new Chapter(i, cTitle);
				bookTok.addChapter(chapter);
				countChapterTokens(chapter, cContent, terms);
				i++;
			}
		} //TODO Migrate to Java 8 streams interface

		return bookTok;
	}
	
	private void countChapterTokens(Chapter chapter, String cContent, List<String> terms) {
		for (String term: terms){
			int occurance = StringUtils.countMatches(cContent, term);
			if(occurance != 0){
				chapter.addToken(new Token(term, occurance));
			}
		}
	}
}
