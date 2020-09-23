#!/bin/bash
echo "Shell 传递参数实例！";
echo "执行的文件名：$0";
echo "第一个参数为：$1";
echo "第二个参数为：$2";
echo "第三个参数为：$3";

echo "参数个数为：$#";
echo "传递的参数作为一个字符串显示：$*";

echo " \$* 与 \$@ 不同点：只有在双引号中体现出来。假设在脚本运行时写了三个参数 1、2、3，则 " \* " 等价于 "1 2 3"（传递了一个参数），而 "\@" 等价于 "1" "2" "3"（传递了三个参数）。\n"

echo "-- \$* 演示 ---"
for i in "$*"; do
    echo $i
done

echo "-- \$@ 演示 ---"
for i in "$@"; do
    echo $i
done

if [ -n "$1" ]; then
    echo "包含第一个参数 $1"
else
    echo "没有包含第一参数"
fi

if [ $1 -eq 1 ]; then
	echo "第一个参数等于1"
fi

str1="/Users/eric/shelltest.sh"
if [[ $str1 == $3 ]]; then
	echo "地址相同"
fi

if [[ -e $3 ]]; then
	echo "参数是一个文件"
fi

if [[ -d $4 ]]; then
	echo "参数是一个目录"
fi




