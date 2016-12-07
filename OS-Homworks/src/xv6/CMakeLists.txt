cmake_minimum_required(VERSION 3.3)
project(xv6_public)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES main.cpp)
add_executable(xv6_public ${SOURCE_FILES})