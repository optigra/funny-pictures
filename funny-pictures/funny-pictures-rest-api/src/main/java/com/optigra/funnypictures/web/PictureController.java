package com.optigra.funnypictures.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PictureController {

	@RequestMapping(value = "/")
	public String method1() {
		return "Hello";
	}
	
}
