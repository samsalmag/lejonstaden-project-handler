package edu.chalmers.axen2021.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to mark a class as a controller for .fxml files.
 * @author Sam Salek
 */
@Retention(RetentionPolicy.RUNTIME)  // Is retained and kept during runtime. Is used to be functional on runtime.
@Target(ElementType.TYPE)            // Only works on classes.
public @interface FXMLController {}
