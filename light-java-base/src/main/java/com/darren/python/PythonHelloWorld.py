#!/usr/bin/python
# -*- coding: UTF-8 -*-
import sys

print '参数个数为:', len(sys.argv), '个参数。'
print '参数列表:', str(sys.argv)

needHandleDic = sys.argv[1]
outPutDic = sys.argv[2]
print "待处理词表文件："+needHandleDic,"处理后的词表文件："+outPutDic

# 打开一个文件r+
fo = open("/Users/eric/"+outPutDic, "w")

# http日志格式需满足每一行的标准格式为：method\turl\tbody\theader(\t为tab键)
fo.write("GET\t/helloWorld/hi?a=b\t"+fo.name+"\t{\"ke\":\"ok\",\"Host\":\"etc.test.darren.com\"}\n")

# str2 = fo.read(10)
# print "读取的字符串是 : ", str2
# 关闭打开的文件
fo.close()