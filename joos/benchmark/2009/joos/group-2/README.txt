JOOS deliverable: benchmark programs
------------------------------------

1. Program Overview

Arn't you sick of colors?? Arn't you sick of GUIs???
If your answer is yes to any of these questions or 
you simply want to have some laughs!
Use our AsciiFunArt software!

AsciiFunArt takes a name of a very colorful image which is so colorful that
hurts your eyes and converts it to a beautiful plain ascii characters 
which you can store in a text file or have it display in your terminal!!
AWESOME!!! 
Imagine you can set your .bashrc profile to display yourself as ascii art!

Supports: Jpeg, Gif, Bitmap, etc...

2. Implementation

- AsciiArtFun

The main executable file is AsciiArtFun which takes input from stdin or from 
file and passes preccessing to InputProccessor object.

- InputProcessor
The input processor is responsible for getting user input from stdin line by 
line. Each line that the user types or redirects from file must be in the
following format:

width;height;path

Where width and height are integers and path is the path to the image file.
After ImageProcessor gets the line it extracts the 3 parameters.

- abstract ImageTransform
The idea of this abstract class is to serve as a sort of interface
Any implementation will store the width and height and 
will have an apply(int) method which converts an integer value to some string

- BasicBrightnessTransform extends ImageTransform
This class extends frim ImageTransform and overrides the abstract class methods.
The method apply takes in a brightness between 0 and 100. Any other numbers 
above or below will be treated as the boundry 100 or 0 respectivly. 

- MacroBlock
A single rectangular area of the image. The image is split into several 
MacroBlock objects. The overall brightness of each macroblock is calculated
and a correspondings ASCII character is computed using an ImageTransform.

- NormalizaedImage
Proxy for an actual bufferedimage object. This proxy allows for resizing of the
image with "fake" pixels such that the original aspect ratio is kept when 
converting to a fixed ascii frame size (n x n characters).

- ImageConverter
Takes care of the main image converter. Creates all macroblock objects,
applies brightness transform, and writes the result to an ASCIIImage object.

- ASCIIImage            
Class representation of a 2 dimensional character array. Used to store
character data that constitutes an ASCII image throught get(int,int) set(int,
int,String) methods

3. Class diagram

 <<Entry Point>>
+-----------------+
|  AsciiFunArt    |
+-----------------+
       <.>  <<Instantiates>>
        |
        v
+------------------+ 
|  InputProcess    |
+------------------+ 
  <.>  
 1 | <<Instanciates>>
   |                             1 +--------------------+  
   |     +-----------------------> |  NormalizedImage   |
   |     |                         +--------------------+
   |     | 1
 * v    <.>         1           1
+----------------+                 +----------------------+
| ImageConverter | <>----------->  |      ASCIIImage      |
+----------------+                 +----------------------+
        <.> 1
         |
         |
         V  *            
+-----------------+ 1      <<Uses>>     1 +---------------------+
|  MacroBlock     | <>------------------> |   ImageTransform    |
+-----------------+                       +---------------------+
                                                 ^
                                                 | extends
                                 +--------------------------+
                                 | BasicBrightnessTransform |
                                 +--------------------------+

4.Conclusion

LIMITATIONS: The width and the height selected must be close to the 
image's aspect ratio for optimal results.
 

