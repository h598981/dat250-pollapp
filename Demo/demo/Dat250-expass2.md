Dat250 - Assigmnent 1

The objective of this task was to create a Simple Rest API for a poll app using Spring Boot. This API allows users to registrer, create polls, add options and cast votes. 


# Implementation

Domain Model
User - Represents a registered user id

Poll - Represents a poll with question, owner, timestamps, options, and invited users. 

VoteOption - Represents a possible answer in a poll

Vote - Represents a users vote

# Service

PollManager - manages memory storage of users, polls, options, and votes. 


# Controllers
UserController - Handles CRUD operations for users

Pollscontroller - Handles poll creation, listing, and retrieval.

VotesController - Allows users to vote and list votes
PollsOptionsController - endpoints to view options for poll

# Data Transfer Objects (DTO)

CreateUserDto, CreatePollDto, createVoteDTO, used to simplify payloads.

# Testing 

The API was tested using HTTP requests. Made a HTTP request file in VS Code

# Issues

1. Gradle not running because i ran it from the wrong folder. Two folders were named demo so it took a awkardly long time to realize.

2. Initially missed DTO classes, made compiling errors

3. Alot of problems with getting pollmanager to work, but it eventually did. 

4. Some of the requests not working. Struggled along time with this so it was just a matter of going through the code. 


