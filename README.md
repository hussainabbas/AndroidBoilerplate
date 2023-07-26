# Android Boiler-Plate using clean architecture

## Feature List

- Added Hilt
- Extensions in helper/Extensions
- Retrofit
- Glide
- Added Auth Interceptor which will allow Authorization 
  header for only api which has Authenticated annotation
- Used Kotlin Coroutines
- Added Different Flavors (Staging, Development, Production)

> Note: If you want to change flavors just go to build.gradle file 
> and add your desired flavor and after that go to config folder 
> and create a new properties file and pass the file name in the flavor function 
> Api base url or any information you want to add you can add in .properties file
> if you dont find any .properties files just create inside the config folder and 
> in your build.gradle just pass the name of that .property file

