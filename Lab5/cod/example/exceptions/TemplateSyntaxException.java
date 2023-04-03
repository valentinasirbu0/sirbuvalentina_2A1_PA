package org.example.exceptions;

import java.net.URISyntaxException;

public class TemplateSyntaxException extends URISyntaxException {
    public TemplateSyntaxException(String input, String reason, int index) {
        super(input, reason, index);
    }
}
