package com.kyrin.OltuDemo.exceptions;

import org.apache.oltu.oauth2.common.exception.OAuthRuntimeException;

/**
 * redrect_url error
 * @author kyrin
 *
 */
public class OAuthRedirectUrlException extends OAuthRuntimeException{

	private static final long serialVersionUID = -2957489317951302344L;
	
	public OAuthRedirectUrlException(){
		super();
	}
	
	public OAuthRedirectUrlException(String message){
		super(message);
	}
}
