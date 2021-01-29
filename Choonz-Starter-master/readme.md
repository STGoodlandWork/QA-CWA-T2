# Practical Project Specification (SDET): Client Web Application Testing & Deployment


This project aimed to create a full-stack web application, with utilisation of a full test suite, alongside supporting tools, methodologies and technologies that encapsulates all fundamental and practical modules covered during training.
## Getting Started

This document will allow the user to run the project's software on 
their local machine for additional development, testing and use.

### Prerequisites

In order to launch the software you will require:
- Maven
- Spring Tool Suite
- JDK Version 11

### Installing

Below are a number of various way to run the project. 

## VIA SPRING TOOL SUITE

1. Open the project via File -> Open Projects from FIle System

2. Select "Directory" and navigate to where the file is stored.

3. Right-click the project folder and choose Run As -> Spring Boot App

## VIA CLONING THE REPOSITORY

1. Choose your preferred directory

2. Within it via Git Bash run 
```
git clone https://github.com/STGoodlandWork/QA-CWA-T2.git

```

##Performance Testing

All Performance Testing files for this project can be found in: https://github.com/syedmahian/Choonz-Performance-Tests
Performance Testing for this project was conducted using Jmeter. 

Tests Conducted:
•	Load Test
•	Spike Test
•	Stress Test
•	Soak Test

The purpose of these tests were to identify how well our web application performs under varying load and duration. 
Two different test cases were developed for this. One that tests all CRUD functionalities and one that follows a user story. 
The CRUD test case was used only for Load Testing. The user story test case was used for load, spike, stress and soak testing.

Suggested maximum thread count based on tests: 5000 

## Running the tests

Integration testing was conducted via Spring. Static Analysis was conducted via SonarQube. 

### Integration Tests 
Integration Tests are used for testing on a larger scale, with the codebase 
working together more closely than unit testing. 
```

@Test
	void readByIDTest() throws Exception {
		RequestBuilder request = get(URI + "read/" + TEST_2.getId());
		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkContent = content().contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkBody = jsonPath("title").value(TEST_2.getTitle());

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent).andExpect(checkBody);
	}

```




## Deployment

Please view the Documentation section within the repository for additional information. 

## Authors

* **Samuel Goodland** - [Sam Goodland](https://github.com/STGoodlandWork/)
* **Myles Brathwaite** - [Myles Brathwaite](https://github.com/MylesBrathQA/)
* **Syed Mahian** - [Syed Mahian](https://github.com/syedmahian/)
* **Benediktas Noreika** - [Ben Noreika](https://github.com/noreb001/)

## Acknowledgments


