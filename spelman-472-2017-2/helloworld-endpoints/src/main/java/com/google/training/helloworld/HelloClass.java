package com.google.training.helloworld;

//Hello Class
public class HelloClass {
    public String message = "Hello World";

    public HelloClass () {
    }

    public HelloClass (String name) {
        this.message = "Hello " + name + "!";
    }
    
    
    public HelloClass (String userEmail,String userIngredient) {
        this.message = "The email you entered: " + userEmail +" " + " Ingredient:" + userIngredient;
    }
   
    public String getMessage() {
        return message;
    }
    
    
}
