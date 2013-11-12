all: clean
	joosc *.java */*.java joosutil/*.joos
java:
	javac -d . Locations/*.java Items/*.java Commands/*.java *.java 

clean:	
	rm -rf *.class *.j *~  */*.j */*.class */*~

runFile: clean all
	./run2

run: clean all
	./run

runJava: clean java
	./run

runJavaFile: clean java
	./run2