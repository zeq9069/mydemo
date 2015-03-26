#!usr/bin/python
# coding:utf8
__author__ = 'kyrin kyrincloud@qq.com'

from collections import deque
from math import pi

########################################
#
#
#        python官网学习
#
#      5.数据结构
#
#########################################


# 5.1 more on lists

a = [22, 11, 33, 42, 44, 55, 55, 6.23]

print a.count(55)
a.insert(2, -1)
print a
a.append(22.22)
print a

print a.index(22.22)

a.remove(22.22)
print a

a.reverse()

print a

a.sort()

print a

print a.pop()

print a


print "*" * 40

# 5.1.1. Using Lists as Stacks

stack = [3,4]

stack.append(5)
stack.append(6)

print stack

print stack.pop()
print stack.pop()

print stack


print "*" * 40

# 5.1.2. Using Lists as Queues


queue = deque(["krin_1", "kyrin_2", "kyrin_3"])

queue.append("kyrin_4")
queue.append("kyrin_5")

print queue

print queue.popleft()

queue.popleft()

print queue


print "*" * 40

# 5.1.3. Functional Programming Tools

def f(x):
    return x % 3 == 0 or x % 5 == 0

print filter(f, range(2, 25))


def cube(x):
    return x**3

print map(cube, range(1, 11))

seq = range(8)

def add(x, y):
    return x+y

print map(add, seq, seq)



def sum(seq):
    def add(x, y):
        return x+y
    return reduce(add, seq, 0)

print sum(range(1, 11))
print sum([])



print "*" * 40

# 5.1.4. List Comprehensions

squares = []
for x in range(10):
    squares.append(x**2)

print squares

s = [x**2 for x in range(10)]

print s

sq = map(lambda x: x**2, range(10))

print sq

print [(x, y) for x in [1,2,3] for y in [3,1,4] if x != y]



combs = []
for x in [1,2,3]:
    for y in [3,1,4]:
        if x != y:
            combs.append((x, y))

print combs

vec = [-4, -2, 0, 2, 4]
print [x*2 for x in vec]

print [x for x in vec if x >= 0]

print [abs(x) for x in vec]

freshfruit = ['  banana', '  loganberry ', 'passion fruit  ']
print [weapon.strip() for weapon in freshfruit]

print [(x, x**2) for x in range(6)]

vec = [[1,2,3], [4,5,6], [7,8,9]]
print [num for elem in vec for num in elem]


print [str(round(pi, i)) for i in range(1, 6)]

print "*" * 40

# 5.1.4.1. Nested List Comprehensions

matrix = [
    [1, 2, 3, 4],
    [5, 6, 7, 8],
    [9, 10, 11, 12],
]

print matrix


print [[row[i] for row in matrix] for i in range(4)]


print zip(*matrix)


# 5.2 The del  statement

a = [-1, 1, 66.25, 333, 333, 1234.5]

del a[0]

print a

del a[2:4]

print a

#del a[:]

print a

del a

#print a


# 5.3 Tuples and sequences

t = 1235, 653442, 'hello!'
print t[0]

u = t, (1, 2, 3, 4, 5)

print u

empty = ()

singleton = 'hello'

print len(empty), len(singleton)

print singleton

x, y, z = t

print x, y, z


# 5.4 Sets

basket = ['apple', 'orange', 'apple', 'pear', 'orange', 'banana']

fruit = set(basket)

print fruit

print 'apple' in fruit

print 'www' in fruit

a = set('rrfewrfwer')
b = set('sdfdsfsdf')

print a, b


a = {x for x in 'abracadabra' if x not in 'abc'}

print a


# 5.5. Dictionaries

tel = {'jsck': 123, 'sape': 231}

print tel.keys(), tel.get("jsck"), tel.has_key("sape")

for x in tel.items():
    print "value:", x

for x in tel.iterkeys():
    print "key:", x

del tel['sape']

print tel

print dict([('sape', 4139), ('guido', 4127), ('jack', 4098)])

print  {x: x**2 for x in (2, 4, 6)}


# 5.6 Looping Techniques

for i, v in enumerate(['tic', 'tac', 'toe']):
    print i, v

questions = ['name', 'quest', 'favorite color']
answers = ['lancelot', 'the holy grail', 'blue']
for q, a in zip(questions, answers):
    print 'What is your {0}?  It is {1}.'.format(q, a)


basket = ['apple', 'orange', 'apple', 'pear', 'orange', 'banana']
for f in sorted(set(basket)):
    print f


words = ['cat', 'window', 'defenestrate']
for w in words[:]:  # Loop over a slice copy of the entire list.
    if len(w) > 6:
        words.insert(0, w)


print words


# 5.7. More on Conditions

string1, string2, string3 = '', 'Trondheim', 'Hammer Dance'
non_null = string1 or string2 or string3

print non_null


# 5.8 Comparing Sequences and Other Types

print (1, 2, 3) < (1, 2, 4)
print [1, 2, 3] < [1, 2, 4]
print 'ABC' < 'C' < 'Pascal' < 'Python'
print (1, 2, 3, 4) < (1, 2, 4)
print (1, 2) < (1, 2, -1)
print (1, 2, 3) == (1.0, 2.0, 3.0)
print (1, 2, ('aa', 'ab')) < (1, 2, ('abc', 'a'), 4)