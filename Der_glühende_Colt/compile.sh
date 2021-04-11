#!/bin/bash -e

echo ================================
JAVAC=javac
SRC=src/java/colt

date

which $JAVAC
$JAVAC -version

echo compile...

$JAVAC -Xdiags:verbose -encoding cp1252 $SRC/*.java $SRC/models/*.java $SRC/gui/*.java
mv $SRC/gui/*.class bin/colt/gui/
mv $SRC/models/*.class bin/colt/models/
mv $SRC/*.class bin/colt/
echo ================================
