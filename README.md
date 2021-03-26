Week 3 Assignment - Timer
=====================
This is assignment 3 of the 'mobile application development' course at Munich University of Applied Sciences.

## Basics for this assignment
Follow lesson 4 'Activity & Fragment Lifecycle' in the [udacity course](https://www.udacity.com/course/developing-android-apps-with-kotlin--ud9012)
or read [04.1 and 04.2](https://codelabs.developers.google.com/android-kotlin-fundamentals/) in Google codelabs.


## What to do with this repository

The basic steps are:

1. Clone the repository
2. You can create your own development branch or work with the master branch. 
3. Read the assignment steps on [Moodle Assignment 'Week 3 - Lesson 4 of online course'](https://moodle.hm.edu/mod/assign/view.php?id=421784) or down below.
4. Start coding and have fun.

## Assignment
Create an app with a timer. Your app has the following functionality:
* There is a timer in the main activity. The main activity shows the updating timer value. 
* The user can set a start value for the timer (see the dialog fragment in the starter code or use your design).
* Start the timer with a button
* Pause the timer with a button
* Stop the timer with a button and reset the timer back to its start value

The app should also have the following features:
* The timer pauses if you send the app to the background.
* The timer continues where it was paused if you bring the app back to the foreground.
* Flipping your phone does not reset the timer.  
* Log the counting timer value in LogCat (Tipp: Use Timber as suggested in the udacity course or the codelabs material)

The timer can either indefinitely count up or indefinitely count down. Think about stopping the timer when it makes sense.

!!! Do not forget to commit and push your changes AND submit the link to your repository in Moodle!!!

Example how the app could look like:
<p align="center">
  <img width="337" height="600" src="https://github.com/mobileappdevhm20/w3/blob/master/doc/timer_example.png">
</p>

## Git basics
The basics are for console users. There are also buttons for these commands in Android-Studio.
See [this](https://stackoverflow.com/questions/52565212/how-to-easy-commit-android-studio) for graphical use.

```
git clone <repository-url> - Clone a repository you want to work on
```
```
git checkout -b <new_branchname>  - check out all the content you are working on to a new branch with specified name
```
```
git checkout <branchname>  - check out to another already existing branch
```
```
git stash - store all changes made to the stash so the working directory is 'clean' and you can e.g. switch branches
```
```
git stash apply - apply all changes stored to the stash back to the working directory
```
```
git add <files>  - To add files you want to commit later locally
```
```
git commit -m "<message>" - Commit your added changes to the staging phase locally
```
```
git push - Push your changes to the remote repository
```

For interested command-line users read [this](https://git-scm.com/doc)

### How to make useful git messages (git commit -m "<message>)
* Do not make it longer than 50 characters
* Describe what you have changed
* Start with a verb e.g. Added, Fixed, Removed, etc.
* Stick to either present or past tense over all your commits.
Further [reading](https://dev.to/jacobherrington/how-to-write-useful-commit-messages-my-commit-message-template-20n9).

## More questions?
* Make posts in the [moodle](https://moodle.hm.edu) forums of mobile application development.
* Email the student assistant (tutor) of this course: <jeremias.wiedmann@hm.edu>
* Email the professor of this course: <gudrun.socher@hm.edu>




