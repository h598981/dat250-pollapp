# Dat 250 assigment 3

## Goal
To build a Svelte app that connects to the Spring boot Rest API

## Implementation
I used Svelte created via "npm init vite@latest" with the plain Javascript template. 

The app has three components
- CreateUserComponent - to create new users
- CreatePollComponent - to create new polls with options
- VoteComponent - to load polls, select options, and vote

## Problems
- Trouble running npm so had to fix the path in Environment variables
- Forgot to run the backend, causing "failed to fetch"

## Results
The SPA can: 
- Create users
- Create polls
- Load options and submit votes
- Display votes from the backend


# Dat 250 assignment 6

## Goal
Extend the PollApp with messaging using RabbitMQ so that each poll has its own topic for publishing and receiving vote events

## Technology
Rabbit - Easy local setup (Docker), 
Exchange model: 
- Publish: poll.created, vote.cast (simple Json)
- Subscribe: app listens to poll.pollId and can update the database from vote.cast

Gradle dependencies added: 
implementation 'com.rabbitmq:amqp-client:5.21.0'
testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'

## Implementation
- Created the class RabbitMQService for connecting to rabbitMQ, publish JSON messages and subscribes with callbacks. 


## problems and solutions
JUnit not found. Managed to have the test class in src/main/java, moved it to src/test/java
RabbitMQ client missing after added to dependency. Had to refresh Gradle


Didnt manage to have the PollApp automatically update the database when the messages are received.
