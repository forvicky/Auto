cmake_minimum_required(VERSION 3.4.1)

##C 的编译选项是 CMAKE_C_FLAGS
## 指定编译参数，可选
#SET(CMAKE_CXX_FLAGS "-Wno-error=format-security -Wno-error=pointer-sign")

#设置生成的so动态库最后输出的路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI})

#编译头文件
#定义全局 my_source_path 变量
file(GLOB my_source_path src/main/cpp/*.cpp src/main/cpp/*.c)

add_library( # Sets the name of the library.
             native-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             ${my_source_path} )

include_directories(src/main/cpp/include/)
include_directories(src/main/cpp)

add_library(lib_opencv SHARED IMPORTED)
set_target_properties(lib_opencv
  PROPERTIES IMPORTED_LOCATION
  ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libopencv_java4.so)


find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# 在ndk中查找jnigraphics库 取别名jnigraphics-lib
# jnigraphics包含图形操作的库
find_library(jnigraphics-lib jnigraphics)

target_link_libraries( # Specifies the target library.
                       native-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       lib_opencv
                       ${jnigraphics-lib}
                       ${log-lib} )