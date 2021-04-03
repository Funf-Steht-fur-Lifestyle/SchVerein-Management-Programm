#!/bin/bash -e

echo ================================
date
JAVAC=javac
which $JAVAC
$JAVAC -version

echo compile...

$JAVAC -Xdiags:verbose -encoding cp1252 src/*.java src/gui/*.java
mv src/gui/*.class bin/colt/gui/
mv src/*.class bin/colt/

echo ================================
