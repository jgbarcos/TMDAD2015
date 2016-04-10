# Analysis Coordinator

This is the analysis coordinator prototype.

Analyis coordinator gets the book's name and terms to be analyzed from the GUI and coordinates the different elements to get the final result. 

First of all, sends the information of the book to the Gateway so it can get the book from www.gutenberg.org.

Once it has the book and the terms, the coordinator forwards them to the Tokenizer.

The coordinator gets the result from the Tokenizer and returns it to the GUI.

## Analysis operations
