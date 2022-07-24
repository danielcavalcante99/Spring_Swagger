package br.com.product.services.exceptions;

public class ProductNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -7511185391069453L;
	
	public ProductNotFoundException(String msg) {
		super(msg);
	}

}
