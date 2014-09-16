package com.optigra.funnypictures.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optigra.funnypictures.facade.resources.Picture;

@RestController
public class PictureController {

	@RequestMapping(value = "/")
	public Picture method1() {
		
		String name = "name";
		
		Picture picture = new Picture();
		picture.setName(name);
		
		return picture;
	}
	
}
