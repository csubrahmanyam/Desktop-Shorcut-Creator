javac src/dsc/Main.java -d out/
cd out
jar -cfvm ../bin/dsc dsc.mf dsc/*.class
cd ..
java -jar bin/dsc

