package com.optigra.funnypictures.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PictureController {

	@RequestMapping(value = "/")
	@ResponseBody
	public String method1() {
		return "Hello";
	}
	
}
