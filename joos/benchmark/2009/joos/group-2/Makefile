JC = joosc
LIBS = ../imports.joos
SOURCES = AsciiFunArt.java ASCIIImage.java BasicBrightnessTransform.java\
          ImageConverter.java ImageTransform.java InputProcessor.java\
          MacroBlock.java NormalizedImage.java

default: clean build
	
build: 
	$(JC) $(SOURCES) $(LIBS)

clean:
	rm -f *.class *.j
