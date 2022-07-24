package br.com.product.services.exceptions;

public class ProductBadRequestException extends RuntimeException{

	private static final long serialVersionUID = 7041938638279394873L;

	public ProductBadRequestException(String msg) {
		super(msg);
	}

}
