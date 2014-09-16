package com.optigra.funnypictures.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optigra.funnypictures.facade.resources.message.MessageResource;
import com.optigra.funnypictures.facade.resources.message.MessageType;

@RestController
public class PictureController extends BaseController {

	@RequestMapping(value = "/")
	public MessageResource method1() {

		MessageResource resource = new MessageResource();
		resource.setMessage("message1");
		resource.setMessageType(MessageType.INFO);
		
		return resource;
	}
	
}
