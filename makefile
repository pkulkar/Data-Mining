all: 
	javac -classpath ".:math.jar" prediction.java
	java -classpath ".:math.jar" prediction
	$(RM) prediction.class