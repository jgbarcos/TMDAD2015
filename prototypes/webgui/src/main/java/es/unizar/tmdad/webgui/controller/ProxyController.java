package es.unizar.tmdad.webgui.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import es.unizar.tmdad.webgui.services.RestProxy;


@RestController
public class ProxyController {
	
	String url = "http://localhost:8081";
	
	@RequestMapping(value="/themes", method={RequestMethod.GET}, params={"like"})
	public String themeslike(@RequestParam("like") String like){
		return RestProxy.createGet(url+"/themes")
				.addParam("like", like)
				.forward();
	}

	@RequestMapping(value="/analyze", method={RequestMethod.GET})
	public String search(@RequestParam("book") String book, @RequestParam("themes[]") List<String> themes) {
		return RestProxy.createGet(url+"/analyze")
				.addParam("book", book)
				.addParam("themes[]", themes)
				.forward();
	}
}