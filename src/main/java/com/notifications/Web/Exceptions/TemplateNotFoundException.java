package com.notifications.Web.Exceptions;

public class TemplateNotFoundException extends RuntimeException{
    public TemplateNotFoundException() {
    }

    public TemplateNotFoundException(String s) {
        super(s);
    }

    public TemplateNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TemplateNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
