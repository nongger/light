#!/bin/bash
# shell的流程控制
a=10
b=20
if [ $a == $b ]
then
   echo "a 等于 b"
elif [ $a -gt $b ]
then
   echo "a 大于 b"
elif [ $a -lt $b ]
then
   echo "a 小于 b"
else
   echo "没有符合的条件"
fi

if [ $(ps -ef | grep -c "ssh") -gt 1 ]; then echo "true"; fi


for loop in 1 2 3 4 5
do
    echo "The value is: $loop"
done


int=1
while(( $int<=5 ))
do
    echo $int
    let "int++" # Bash let 命令，它用于执行一个或多个表达式，变量计算中不需要加上 $ 来表示变量
done


a=0

until [ ! $a -lt 10 ]
do
   echo $a
   a=`expr $a + 1`
done


echo '输入 1 到 4 之间的数字:'
echo '你输入的数字为:'
read aNum
case $aNum in
    1)  echo '你选择了 1'
    ;;
    2)  echo '你选择了 2'
    ;;
    3)  echo '你选择了 3'
    ;;
    4)  echo '你选择了 4'
    ;;
    *)  echo '你没有输入 1 到 4 之间的数字'
    ;;
esac

while :
do
    echo -n "输入 1 到 5 之间的数字:"
    read aNum
    case $aNum in
        1|2|3|4|5) echo "你输入的数字为 $aNum!"
        ;;
        *) echo "你输入的数字不是 1 到 5 之间的! 游戏结束"
            break
        ;;
    esac
done

