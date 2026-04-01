package com.bad;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        PaymentProcessor processor = new PaymentProcessor();

        // Did this work? We have no idea. No result to check.
        processor.processPayment("credit_card", 149.99);

        // This silently fails. No exception thrown, nothing returned.
        processor.processPayment("credit_card", -50.00);

        // Typo in method name. Silently falls into the else branch.
        processor.processPayment("crypto", 200.00);

        // Now the PM says: "add Apple Pay by Friday"
        // You open this file, scroll to the bottom of the else-if chain,
        // paste a new block, and pray you didn't break credit cards.
    }
}
