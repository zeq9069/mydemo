#!usr/bin/python
# coding:utf8
__author__ = 'kyrin kyrin@qq.om'

###########################
#
#     python modules
#
#
#############################


def fib(n):
    a, b=0, 1
    while b < n:
        print b,
        a, b = b, a+b


def fib2(n):
    result = []
    a, b=0, 1
    while b < n:
        result.append(b)
        a, b = b, a+b
    return result




