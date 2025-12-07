# Scope

A centralized news platform for Cornell news sources.

## Description

This app was made in hopes of solving the problem of Cornell students being unaware of local news sources or simply not caring enough to read and engage with them. Through Scope, people can have a centralized hub of articles to read or listen to. There is also a save functionality for a user to gather articles that they want to read later. 

### Executing program

* If the program does not run at first, edit the NewsViewModel.kt file
* Replace 'articleRepository.login("janedoe", "1234")' with the following two lines of code: 
```
articleRepository.registerAccount(username = "_", password = "_", email = "_")
articleRepository.login("_", "_")
```
Username/Password/Email can be anything, and once you run the emulator once, you can comment out the first line (the user has already been created). 
We could not create a fully functional authentication system, so we hard-coded a user for functionality purposes. 

## Authors

Ryan Cheung  
Shadman Syed
