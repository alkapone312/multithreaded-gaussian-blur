#include "pl_pwr_jk_jni_NativeConvolution.h"
#include <stdio.h>

JNIEXPORT jobjectArray JNICALL Java_pl_pwr_jk_jni_NativeConvolution_nativelyCalculateConvolution
    (JNIEnv *env, jobject object, jobjectArray inputMatrix, jobjectArray filterMatrix) {

    int matrixHeight = env->GetArrayLength(inputMatrix);
    jobjectArray element = (jobjectArray)env->GetObjectArrayElement(inputMatrix, 0);
    jobject objectElement;
    int matrixWidth = env->GetArrayLength((jobjectArray)env->GetObjectArrayElement(inputMatrix, 0));
    env->DeleteLocalRef(element);

    int filterHeight = env->GetArrayLength(filterMatrix);
    element = (jobjectArray)env->GetObjectArrayElement(filterMatrix, 0);
    int filterWidth = env->GetArrayLength(element);
    env->DeleteLocalRef(element);

    jclass doubleArrCls = env->FindClass("[Ljava/lang/Double;");
    jclass doubleClass = env->FindClass("Ljava/lang/Double;");
    jobjectArray result = env->NewObjectArray(matrixHeight, doubleArrCls, nullptr);

    for (int y = 0; y < matrixHeight; y++) {
        jobjectArray row = env->NewObjectArray(matrixWidth, doubleClass, nullptr);
        for (int x = 0; x < matrixWidth; x++) {
            double sum = 0.0;
            for (int fy = 0; fy < filterHeight; fy++) {
                for (int fx = 0; fx < filterWidth; fx++) {
                    double matrixValue = 0.0;
                    int matY = y + fy - filterHeight / 2;
                    int matX = x + fx - filterWidth / 2;
                    if (matY >= 0 && matY < matrixHeight && matX >= 0 && matX < matrixWidth) {
                        element = (jobjectArray)env->GetObjectArrayElement(inputMatrix, matY);
                        objectElement = (jobject)env->GetObjectArrayElement(element,matX);
                        matrixValue = env->CallDoubleMethod(
                            objectElement,
                            env->GetMethodID(
                                doubleClass,
                                "doubleValue",
                                "()D"
                            )
                        );
                        env->DeleteLocalRef(element);
                        env->DeleteLocalRef(objectElement);
                    }
                    element = (jobjectArray)env->GetObjectArrayElement(filterMatrix, fy);
                    objectElement = (jobject)env->GetObjectArrayElement(element,fx);
                    double filterValue = env->CallDoubleMethod(
                        objectElement,
                        env->GetMethodID(
                            doubleClass,
                            "doubleValue",
                            "()D"
                        )
                    );
                    env->DeleteLocalRef(element);
                    env->DeleteLocalRef(objectElement);
                    sum += matrixValue * filterValue;
                }
            }
            env->SetObjectArrayElement(
                row,
                x,
                env->NewObject(
                    doubleClass,
                    env->GetMethodID(doubleClass, "<init>", "(D)V"),
                    sum
                )
            );
        }
        env->SetObjectArrayElement(result, y, row);
    }

    return result;
}