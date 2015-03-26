#!user/bin/python
# coding:utf8
__author__ = 'kyrin kyrincloud@qq.com'
#import fibo
from fibo import fib, fib2
######################################
#
#     python modules
#
#
#   fibo 模块调用
#
#
#####################################

#fibo.fib(1000)
fib(1000)

# print "\n", fibo.fib2(1000)
print "\n", fib2(1000)

# fib = fibo.fib

fib = fib

fib(500)

# print "\n", fibo.__author__

# 6.1.1. Executing modules as scripts

# python fibo.py <arguments>

if __author__ == 'kyrin kyrincloud@qq.com':
    import fibo
    print "\n"
    fibo.fib(10)


# 6.1.2. The Module Search Path

# 6.1.3. “Compiled” Python files

# 6.2. Standard Modules

import sys
print sys.api_version

print sys.path


# 6.3. The dir() Function

import fibo, sys
print "\n", dir(fibo)
print "\n", dir(sys)

a = [1, 2, 3, 4, 5]
import fibo
fib = fibo.fib

print "\n", dir()


import __builtin__

print "\n", dir(__builtin__)

# 6.4. Packages

import python_base.test_6

print python_base.test_6.__author__

# 6.4.1. Importing * From a Package

__all__ = ["echo", "surround", "reverse"]

print __all__

# 6.4.2. Intra-package References

