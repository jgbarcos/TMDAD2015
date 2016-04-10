# TMDAD2015
Repository for Project in Course: "Technologies and Models for Distributed Applications Development"

This is a group project done for the MSc in Computer Engineering at University of Zaragoza.

## Workflow

- Each of the members forks from jgbarcos github.
- Then we proceed to work individually pushing changes to our github.
- Whenever you want to make a change available to all, perform a pullrequest to jgbarcos.
- Whenever you want to get all the available changes, perform a pull from jgbarcos.

The following diagram sumarizes the workflow:
```
    ------------github javi-------------
    |                |                 |         ^ Pullrequest
    |                |                 |         v Pull
github rosa     github victoria    github alex
```

## Guidelines

Some guidelines to mantain a coherent style between every member:
 - Write commit messages and code always in English (everything: methods, variables, comments...).
 - Start commit messages with a descriptive one-line title: Verb + short description of the change.
 - For further description, leave a blank line after the title and then proceed to describe the commit.
 - Never write garbage commit messages like: ".", "asdf" or anything that gives no description of the change.
 - Don't make a commit with multiple non-related changes, instead perform a commit for each change.
 - Check the files that are being committed, make sure there aren't any unnecesary files and modify .gitignore as needed.

## Example of a commit:

> Fixed Tokenizer chapter parsing
>
> Some of the books have different chapter delimiter styles:
> - CHAPTER 1
> - chapter 1
> - FIRST CHAPTER
> - PROLOGUE
>
> Tokenizer's parser was fixed to recognize those styles and remove trailing whitespaces.
