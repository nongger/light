#!/bin/sh
# echo test 标准输入
read name 
echo "$name It is a test"

echo -e "OK! \n" # -e 开启转义
echo "It is a test"

echo -e "OK! \c" # -e 开启转义 \c 不换行
echo "It is a test"

echo `date`

#输入到文件中
echo "It is a test" > myfile.log