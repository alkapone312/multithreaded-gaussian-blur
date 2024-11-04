javac -d bin -h native $(find src -type f -name *.java)

g++ \
native/pl_pwr_jk_jni_NativeConvolution.cpp \
-I/home/jakub/.local/lib/jdk-17/include \
-I/home/jakub/.local/lib/jdk-17/include/linux \
-shared \
-o libnative.so