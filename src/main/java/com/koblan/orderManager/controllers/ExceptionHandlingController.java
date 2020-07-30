package com.koblan.orderManager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.koblan.orderManager.exceptions.NoSuchItemException;
import com.koblan.orderManager.exceptions.NoSuchOrderException;
import com.koblan.orderManager.models.Message;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(NoSuchOrderException.class)
    ResponseEntity<Message> showNoSuchOrderException(){
        return new ResponseEntity<Message>(new Message("Such order not found"), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(NoSuchItemException.class)
    ResponseEntity<Message> showNoSuchItemException(){
        return new ResponseEntity<Message>(new Message("Such item not found"), HttpStatus.NOT_FOUND);
    }

}
