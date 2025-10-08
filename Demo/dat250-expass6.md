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