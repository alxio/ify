Jar-in-command-line howto
===
What you need:
1. recipe.java - your recipe code  
2. ify.jar - jar exported from ify project  
3. android.jar - jar with whole android stuff (ADT/sdk/platforms/android-18/android.jar)  
4. dx - application (/ADT/sdk/build-tools/android-4.3/dx)  
   dx requires lib/dx.jar  
   make lib dir and copy /home/scony/ADT/sdk/build-tools/android-4.3/lib/dx.jar  

First, lets make recipe.class:
===
javac -cp ify.jar:android.jar recipe.java

Second, build temporary jar:
===
jar cf tmp.jar recipe.class

Third, make dex classes:
===
./dx --dex --output=classes.dex tmp.jar

Finally build recipe.jar:
===
jar cf recipe.jar recipe.class classes.dex