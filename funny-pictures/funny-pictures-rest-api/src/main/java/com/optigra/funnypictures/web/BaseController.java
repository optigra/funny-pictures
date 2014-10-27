package com.optigra.funnypictures.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.optigra.funnypictures.facade.resources.message.MessageResource;
import com.optigra.funnypictures.facade.resources.message.MessageType;

/**
 * Abstract Class for all common logic.
 * @author ivanursul
 *
 */
public abstract class BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
	
	/**
	 * Method for handling base exceptions.
	 * @param e input exception.
	 * @return message resource.
	 */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { Exception.class })
    @ResponseBody
    public MessageResource handleException(final Exception e) {
    	LOG.error("Handling general exception", e);
    	
        MessageResource message = new MessageResource();
        message.setMessageType(MessageType.WARN);
        message.setMessage(e.getMessage());

        return message;
    }
}
