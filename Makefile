JCC = javac
JVM = java
JFLAGS = -Xmx1G

.PHONY: build clean run

build: compile

run:
	$(JVM) $(JFLAGS) PathToSat

compile:
	$(JCC) *.java

clean:
	rm -rf *.class
