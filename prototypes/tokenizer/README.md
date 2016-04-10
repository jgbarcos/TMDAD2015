# tokenizer

This is the tokenizer prototype.

Tokenizer analyzes a complete book received from AnaylzerCoordinator, which has obtained it from the Gutenberg Project (https://www.gutenberg.org/) and sends results back to it. 

## tokenizer rest api
Exposes a /tokenize service which receives a book in the request body and the terms to analyze through "terms" parameter.

Returns the information after tokenizing and analyzing the book: book (id, title), chapters and tokens.

## tokenizer operations
//TODO