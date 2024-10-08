package com.moyanshushe.exception;

/*
 * Author: Napbad
 * Version: 1.0
 * address exists
 */
public class AddressExistsException extends BaseException {

    public AddressExistsException() {
        super("Address exists");
    }

    public AddressExistsException(String msg) {
        super(msg);
    }

}
