package com.kings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@RequestMapping("/testJSON")
	public @ResponseBody String testJson() {
		return "{\"such\":\"Team\"}";
	}
	
}
