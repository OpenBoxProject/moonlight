package org.moonlightcontroller.exceptions;

public class ApplicationsLoadException extends Exception {
    public ApplicationsLoadException() {
        super("Failed to update Applications");
    }
}
