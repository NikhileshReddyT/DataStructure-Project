JFLAGS = -g
JC = javac
JVM = java
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = / risingCity.java / RedBlackTree.java / RedBlackNode.java / MinHeap.java / Building.java / ConvertingInputCommand.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class