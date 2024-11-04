./build.sh &&
javac -cp bin -d bin $(find test -type f -name *.java) &&
java -Djava.library.path=$(pwd) -cp bin test.Main