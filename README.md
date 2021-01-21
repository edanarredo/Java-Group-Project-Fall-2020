# ADS
IMPORTANT: This repository is an archived collection of projects done throughout the semester of Fall 2020. 

The first project, `project1`, contains code for an Amazon review data analysis program - where datasets containing product reviews and metadata (obtained from https://jmcauley.ucsd.edu/data/amazon/) are processed in order to determine credibility of certain amazon reviews.

The second project, `project2`, contains unit tests implemented to test some of the functions of the Amazon review program. These tests were ran on an optimal version of project1 provided by the teacher.

The third project, `project3`, implements a GUI for `project1` using Java Swing and JavaFX.

NOTE: The class was listed as an introductory Java class, so this was a very challenging project to pursue for a lot of us - the main objective of the projects was to help us understand the process of interpreting and understanding a larger and imperfect codebase and attempt to make it work in a group.

THE TEXT BELOW IS A TUTORIAL I WROTE FOR MY GROUP TO ACCOMPANY MY EFFORTS TO TEACH THEM HOW TO USE GIT FOR OUR PROJECTS WITHOUT GOING TOO FAR.

#  General 

Here's an outline of how we're going to add progress to this `development` branch. We're going to add changes here because normally people save the master branch for "deployment-ready" code - so we'll treat it as our final product :)

My goal is that this README helps you both out with understanding our project structure - if something doesn't work let me know and I'll make changes to this text. 

There are also a lot of other `git` commands that can do very elaborate things and smoothen out this process - we'll use those on the next projects but for now let's get the basic idea of collaborating on git :)

Setup
-----
### Cloning the repository
First let's `git clone` this repository into the 2020Fall class repository:

```powershell
$ cd .../2020Fall
$ git clone https://git.txstate.edu/CS3354/ADS.git
```
### Checking out a 'development' branch
Now we're going to `cd` into the new directory and `checkout` a `development` branch so that we don't make changes to the `master` branch - our final product:

```powershell 
$ cd ADS
$ git fetch
$ git checkout development
```

First we `fetch` the available branches from our remote repository. When we `checkout`, what we're doing is slecting the remote branch `development`.

### Updating the local repository with new changes
When we make commits and add changes to the `development` branch, we'll need a way for other people to see and access these changes. We'll do that with the following command:

```powershell
$ git pull origin development
```
  
`pull origin <target_repo>` will update AND **OVERRIDE** your local cloned repository with the new changes made on the `development` branch - our target. This might cause some conflict if we're pushing new code addittions at the same time, but we can manage what happens. We'll get better at this, I promise!

*Sidenote: There's a `fetch` command that won't overwrite what you have in your local repo on your computer - and if I figure out an easy way to explain that I'll let y'all know. Feel free to add onto this README if you have an easy way of sharing with everyone how to use it in this context.*

*Context of branches: I read in an article that usually, separate branches are places where developers in a team will work on features, debugging, etc.. Say for example we're developing a social media website/app for dogs, if we planned on implementing an `explore page`, what we could do is create a new branch called `explore_page`, and add work towards that feature in that branch. Whenever the work for that feature is ready to be deployed, that's when the `explore_page` branch is merged with the master one.*

### Adding and committing changes
As you do work and finish implementing something, `add` and `commit` those changes - push them as well so that everyone can access those changes

```powershell
  $ git add .
  $ git commit -m 'Implement bananaFarm()'
  $ git push origin development
  
  //some time later
  $ git add .
  $ git commit -m "fix harvest operation in bananaFarm()"
  $ git push origin development
```
I believe that normally in a professional setting we would not `push` every single `commit` we make to the branch in the remote repository on GitHub, but for now that's what we'll do since we're learning. Also, since we're not working on a greenfield project this will do.

Eventually, when we finish the assignment, I'll merge the `development` branch into the master branch and that will be what we make copies of and upload into our CS3354/NetID repository to submit.

Assignment Details and Progress
-----
Main goal regardless of the correctness of any code implementation we make:
- *Show Object-Oriented Princples, only add what's missing, and make sure this compiles*

Here's some basic information about the code we have so far
- `Rating.java, AbstractRatingSummary.java, and RatingStatsApp.java` are already implemented and can compile. **All we need to do for those and every other file is to add relevant packages**
- `RatingSummary.java` is where a lot of the statistical computations will take place. It also contains basic member functions like constructors and get/set methods - **12 methods total**.
- `DataAnalysis.java` is a utility class, and we just need to implement **2 method**s in here. Prof said that the lecture and notes from 9/24 (comparators, sorting collections) are *helpful* for solving this part.
- `DataSet.java` just needs **1 method** implemented, a computeStats() method
- `DataSetHandler.java` handles the databse, and prints the results of everything onto the console (System.out.println), and onto a text file (File I/O). **3 total methods**

This is a rough estimate, but it seems like we have 5 different types of tasks that need to be implemented

- Complete accessor/mutator/constructor methods for `RatingSummary.java`
- Parsing and reading the databse file (.csv)
- Calculating Statistics with the parsed database file (.csv)
- Output Calculated Statistics and Data (terminal & textfile)

Like we mentioned in our meeting, the provided code is pretty complicated, so I don't think we should assign strict tasks. I think what we should do is call out what we want (and will) work on in Microsoft Teams at the time you plan on working on something. If a task looks too difficult for any one of us to accomplish solo (like parsing the database or calculating statistics), we should consider having a discussion (in voice/video/text-chat) and solve the problems together. I think pair/trio programming might be an awesome learning experience for each of us.


## Other 

In the future if y'all want to continue working on projects together for this class,  we can start learning and applying other github features like 'Pull Requests' and 'Issues' just so that we can get even more familiar with Github/git and have an easier time organizing things. 

Thing might get messy - let's do our best to work things out.

Feel free to add anything onto this README if you have other resources/articles/ideas to share.
  







  

