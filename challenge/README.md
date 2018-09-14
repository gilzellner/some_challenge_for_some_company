#  DevOps Challenge

Welcome to  DevOps challenge! Please read the following instructions carefully:

## Introduction
As a DevOps engineer in , we are more than often need to come up with tools to better serve different teams
within our company.
One of the tools we often interact with is Jenkins, our continuous integration / delivery tool of choice. 

## Setup
Prepare your environment by executing the following script:
```
./setup.sh`
```

## Goal
Your task will be to create a small [Flask](http://flask.pocoo.org/) application that provides statistics of our jenkins 
builds. The task will be divided into:
* [Task 1 - Get_build_statistics](#task-1)
* [Task 2 - Get test statistics](#task-2)
* [Task 3 - Setup SSL encryption](#task-3)

### task-1
**Get build statistics**

In this part, when a user adds the `/build` URI, the app should get the build statistics of our 'promote-master' job and 
print the build that took the longest to run. An example output:

>**Out of 10 builds, build number 7 took the longest to run: 293 seconds**

### task-2
**Get test statistics**

In this part, when a user adds the `/test` URI, the app should get the test statistics of our 'promote-master' job and
prints our the longest running test out of all builds. An example output:

>**Out of 10 builds, build number 5 had the longest running test**
>
>**Test name: com.example.project.CalculatorTests.test_10**
>
>**Test duration: 42 seconds**

### task-3
**Build a dashboard**

In this part, we would like to presents the results of our tests with each build.
Add a `/dashboard` URI to the app such that it will print a table / grid of our test results per build. For example:

```
      /builds | build N | N-1  | N-2  | ...
tests/
-------------------------------------------
test1         | PASS    | FAIL | FAIL | ...
test2         | FAIL    | FAIL | FAIL | ...
test3         | PASS    | PASS | PASS | ...
.
.
.
```

### task-4
**Setup SSL encryption**

It's time to wrap it all together. Your flask app should be protected by a web server, serving requests over SSL.
Build a Dockerfile such that it will hold our new Flask application and serve users using an Nginx as a reverse proxy..


## Notes
* Once Jenkins starts, a dummy job will be triggered to create some test results for you to play with.
* We recommend using [JenkinsAPI ](http://pythonhosted.org/jenkinsapi/)python module to do the heavy lifting of getting data from Jenkins.
* For SSL encryption, you can use an openssl, self-signed certificate.


##### If you have questions, don't hesitate to ask - we want you to succeed!
## Good Luck!
