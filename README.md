# TMDAD2015
Repository for Project in Course: "Technologies and Models for Distributed Applications Development"

This is a group project done for the MSc in Computer Engineering at University of Zaragoza.

## Workflow
- Each of the members will work as collaborators at jgbarcos/TMDAD github.
- Whenever you start working on a certain task, create a new branch from master with a descriptive name.
- Perform all the commits related with that task on his corresponding branch.
- When the task is completed and integrated, merge the new branch with current master.
- If the branch is no longer needed, delete the branch after merging with the master.
- Avoid to perform commits directly into master, try to always work with branches.
- *DON'T* merge a branch with master if the branch doesn't compile, doesn't work or is not even tested.

The following link contains examples about working with branches:

https://git-scm.com/book/en/v2/Git-Branching-Basic-Branching-and-Merging

## Guidelines

Some guidelines to mantain a coherent style between every member:
 - Write commit messages and code always in English (everything: methods, variables, comments...).
 - Start commit messages with a descriptive one-line title: Verb + short description of the change.
 - For further description, leave a blank line after the title and then proceed to describe the commit.
 - Never write garbage commit messages like: ".", "asdf" or anything that gives no description of the change.
 - Don't make a commit with multiple non-related changes, instead perform a commit for each change.
 - Check the files that are being committed, make sure there aren't any unnecesary files and modify .gitignore as needed.
 - *ALWAYS* provide some sort of test. It allows others to check that it works and serves as an example of its usage.

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
