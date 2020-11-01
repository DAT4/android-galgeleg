# Galgeleg App

[Using the REST API](https://github.com/DAT4/android-galgeleg-rest-api)

## Patterns
In the development of the app one key requirement has been to follow software patterns, for me to lean about that.

### MVC
I have chosen to use MVC pattern (Model View Controller), which came naturally to me when I began developing the app, because I worked with this pattern before, and it helped me a lot creating the structure of the game. The MVC pattern is heavily used in the Game Part of the app.

![UML diagram of how the MVC pattern is implemented in the app](UML/mvc.png)

### Observer Pattern
Since I am getting data from the internet, I thought that it will be nice if there was a way to cache the received data on the phone, and share it between Activities, and I was considering using something like a Singleton, or static class. I ended up creating a Kotlin-Object as a **Subject/Observable** and making the Activities who wants to know about the data, **Subscripers/Observers** in their `onCreate()` method, then the observer pattern takes care of updating the views on each active Activity each time the data changes.

![UML diagram of how the Observer pattern is implemented in the app](UML/class.png)

### Other patterns
Android development is using a lot of different patterns, like for example the way an Adapter Pattern is used to connect a list of objects to a recycler view, and then do some magic in the background so that the app developer does not have to do it.

