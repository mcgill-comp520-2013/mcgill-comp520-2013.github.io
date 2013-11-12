I added my own externs in javalib2.joos please make sure this is placed 
in the proper directory for my code to compile thank you

I had to modify the extern random in javalib.joos found in the public html joos extern folder

I changed the method from nextInt() to nextInt( int n). I am checking in the updated javalib.joos 
in our directory please make sure to use it when compiling our code thank you

To Compile, after making sure the .joos files where located in the correct place, type:

joosc Maze.java Node.java

to run just type: java Maze < in1. the code expects input of type integer 
between 2 and 6 representing the dimension of the maze you want created

The argument is the dimension n = 2....6 which comes from the file in1. Please do not go beyond 6 as it will 
take a very long time to compute

The output is all the nxn unicursal mazes it can find