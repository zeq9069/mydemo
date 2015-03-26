#!/usr/bin/python
# coding:utf8
__author__ = 'kyrin kyrincloud@qq.com'

#####################################
#
#
#    python例子练习
#
#  主要练习各种参数定义，流程控制语句
# 各种基本语法
#
#
####################################


# the first example

print "\n", "if判断联系："

l = [1, 2, 3, "qqq", 1]

if 1 in l:
        print "Yes,you are right!"
else:
        print "No,you are wrong!"


# the second example

print "\n", "for 数组遍历："
for i in [1, 2, 3, "wwww", "over"]:
        print "第", i, "元素"
else:
        print "遍历正常完成！"


# the third example

print "\n", "while 语句练习"

flag = True
i = 0
while flag:
    print "Yes,continue!", i
    i += 1
    if i == 5:
        flag = False

else:
    print "正常退出"


# the four example   三个引号可以保留格式 """或者‘’‘

value = """
  <html>
    <body>
        <p>Hello world!</p>
    </body>
  </html>

"""

print value



while True:
    pass

