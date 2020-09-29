# anonTech Interview
    
    anonTech Interview is a microservice application built to satisfy the requirements set forth in the interview document.
    
## How to Run

Check out the repository:

```
git clone https://github.com/ccrosby7/anon-tech-interview.git
```

Change into the repository directory:

```
cd anon-tech-interview
```

Run the following command to run locally in development:

```
 ./gradlew 
```
 To run inside a docker container:
or
```
docker build . -t anon-tech-interview
docker run anon-tech-interview
```

##Usage

```
GET /cable-diameter
curl --request GET --url http://localhost:8080/cable-diameter
```

## Questions
```
1. How should we run your solution?
```
 Either by running the gradle batch included, or via the dockerfile. This was all built on windows so what may work
  on my machine may not work well elsewhere, despite docker's best efforts. 
 
```
2. How long did you spend on the take home? What would you add to your solution if you
had more time and expected it to be used in a production setting?
```
About 3 hours off and on thinking, another 2 actually writing. I try to keep to a `measure twice, cut once` mentality.
I'd regenerate the JHipster side of the application to include a cache to store values as this will definitely run into 
memory issues with large time to lives. Along with working security, heartbeat, and a more robust docker container.
```
3. If you used any libraries not in the language’s standard library, why did you use them?
```
I used Jhipster for the long term extensibility as I took the high level view of designing this as if it were a
 POC instead of an interview. I also brought in Apache's commons-collections4 for the Map with a time to live, 
 allowing me to do the work in memory without a cache or DB. And awaitily for a non--blocking await on my test
 so the internal cache can fill. 
```
4. If you have any feedback, feel free to share your thoughts!
```
I like this test, it gives a lot of breadth as to keeping it super simple with a node.js style application or
 going a tad overboard with a full sized microapp.
