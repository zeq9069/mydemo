#!usr/bin/python
# coding:utf8
__author__ = 'kyrin kyrincloud@qq.com'

###################################
#
#
#    python官网练习
#
#   string 相关操作
#
#
###################################

string_1 = "\t My name is Kyrin ! \n Hello Everyone!"

string_2 = """

 <html>
    <body>
        Hello everyone!
    </body>
 </html>


"""
print string_1, string_2


# 3 times 'un' , followed by 'ium'
string_3 = 3*"un"+"ium"

print string_3

string_4 = "Py" "thon"
print string_4

string_5 = "Python"
print string_5[0], string_5[3]
print string_5[-1], string_5[-2], string_5[-6]
print string_5[0:2], string_5[2:5]
print string_5[:2]+string_5[2:]
print string_5[:4]+string_5[4:]
print string_5[:2], string_5[4:], string_5[-2:]


#  +---+---+---+---+---+---+
#  | P | y | t | h | o | n |
#  +---+---+---+---+---+---+
#  0   1   2   3   4   5   6
# -6  -5  -4  -3  -2  -1

print string_5[4:42], string_5[42:]

print 'J'+string_5[1:]
print len(string_5), len("wwwwwwwwwwwwdddddddddd")

# 3.1.3 unicode strings

print u'Hello World !'
print u'Hello\u0020World !'
print u'Hello\\u0020World !'
print u"abc", str(u"abc")
# print str(u"äöü").encode('utf-8')
print unicode('\xc3\xa4\xc3\xb6\xc3\xbc', 'utf-8')


# 3.1.4 Lists

squares = [1, 2, 3, 4, 5, 6]
print squares
print squares[1], squares[-1], squares[-3:], squares[:]
print squares+[7, 8, 12, 4, 45, 45, 45, 34]
squares.append(216)
print squares


letters = ['w', 'e', 'r', 'r', 't']
print letters
print letters[2:5]
letters[2:5] = []
print letters
print len(letters)

a = ['z', 's', 'e', 'r']
b = [1, 2, 3]
x = [a, b]
print x, x[0], x[0][1]


# first steps towards programming

a, b = 0, 1
while b < 10:
    print b
    a, b = b, a+b

while b < 1000:
    print b,
    a, b = b, a+b


result = 256*256
print '\n', 'The value is ', result