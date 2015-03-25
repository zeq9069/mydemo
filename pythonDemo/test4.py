#!usr/bin/python
# coding:utf8
__author__ = 'kyrin kyrincloud@qq.com'

######################################
#
#    python 官网练习
#
#
#     流程控制语句
#
#
#
#######################################

# 4.More Control Flow Tools

x = int(raw_input("please enter a integer:"))

print x

if x < 0:
    x = 0
    print "negative change to zero"
elif x == 0:
    print "Zero"
elif x == 1:
    print "Single"
else:
    print 'More'

print "*" * 40


# 4.2 for statements

words = ['cat', 'windows', 'kyrin']

for s in words:
    print s, len(s)


for w in words[:]:
    if len(w) > 6:
        words.insert(0, w)

print words

# 4.3 The rang() function

print range(10)

print range(5, 10)
print range(1, 10, 2)
print range(-10, -100, -30)

a = ["ww", "eeee", "rrrr"]
for i in range(len(a)):
    print i, a[i]


print "*" * 40

# 4.4. break and continue Statements, and else Clauses on Loops


for n in range(2, 10):
        for x in range(2, n):
            if n % x == 0:
                print n, 'equals', x, '*', n/x
                break
        else:
            # loop fell through without finding a factor
            print n, 'is a prime number'


for num in range(2, 10):
        if num % 2 == 0:
            print "Found an even number", num
            continue
            print "Found a number", num

# 4.5. pass Statements

#while True:
#    pass


class MyClass:
    pass

print "*" * 40

# 4.6. Defining Functions


def fib(n):
# write Fibonacci series up to n
     """
        Print a Fibonacci series up to n.
     """
     a, b = 0, 1
     while a < n:
         print a,
         a, b = b, a+b
 # Now call the function we just defined

fib(2000)

f = fib

print "\n", f(2000)

f(2000)


print "\n", "*" * 40

# 4.7. More on Defining Functions

def ask_ok(prompt, retries=4, complaint='Yes or no, please!'):
    while True:
        ok = raw_input(prompt)
        if ok in ('y', 'ye', 'yes'):
            return True
        if ok in ('n', 'no', 'nop', 'nope'):
            return False
        retries = retries - 1
        if retries <20:
            raise IOError('refusenik user')
        print complaint

print "\n"

ask_ok('Do you really want to quit?')


i = 5

def f(arg=i):
    print arg

i = 6
f()

def f(a, L=[]):
    L.append(a)
    return L

print f(1)
print f(2)
print f(3)


def f(a, L=None):
    if L is None:
        L = []
    L.append(a)
    return L

print "*" * 40

# 4.7.2. Keyword Arguments

def parrot(voltage, state='a stiff', action='voom', type='Norwegian Blue'):
    print "-- This parrot wouldn't", action,
    print "if you put", voltage, "volts through it."
    print "-- Lovely plumage, the", type
    print "-- It's", state, "!"

parrot("kyrin")
parrot(1000)
parrot(voltage=1000)
parrot(voltage=1000,action='kyrin')
parrot(action='kyrin', voltage=10000)
parrot('kyrin_1', 'kyrin_2', 'kyrin_3')
parrot('krin_1', 'kyrin_22')


# parrot()                     # required argument missing
# parrot(voltage=5.0, 'dead')  # non-keyword argument after a keyword argument
# parrot(110, voltage=220)     # duplicate value for the same argument
# parrot(actor='John Cleese')  # unknown keyword argument

print "*" * 40

def cheeseshop(kind, *arguments, **keywords):
    print "-- Do you have any", kind, "?"
    print "-- I'm sorry , we're all out of", kind
    for arg in arguments:
        print arg
    print "-" * 40
    keys = sorted(keywords.keys())
    for kw in keys:
        print kw, ":", keywords[kw]


cheeseshop("Limburger", "It's very runny, sir.",
           "It's really very, VERY runny, sir.",
           shopkeeper='Michael Palin',
           client="John Cleese",
           sketch="Cheese Shop Sketch")


print "*" * 40

# 4.3.7 Argument Lists

def write_multiple_items(file, separator, *args):
    file.write(separator.join(args))


print "*" * 40

# 4.7.4. Unpacking Argument Lists

print range(3, 6)
agrs = [3, 6]
print range(*agrs)


def parrot(voltage, state='a stiff', action='voom'):
    print "-- This parrot wouldn't", action,
    print "if you put", voltage, "volts through it.",
    print "E's", state, "!"

d = {"voltage": "four million", "state": "bleedin' demised", "action": "VOOM"}
parrot(**d)


print "*" * 40

# 4.7.5. Lambda Expressions

def make_incrementor(n):
    return lambda x: x+n

f = make_incrementor(42)

print f(0)

print f(1)


pairs = [(1, 'one'), (2, 'two'), (3, 'three'), (4, 'four')]
pairs.sort(key=lambda pair: pair[1])
print pairs




print "*" * 40

# 4.7.6. Documentation Strings



