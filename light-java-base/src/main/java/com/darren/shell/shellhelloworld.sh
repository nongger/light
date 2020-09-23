#!/bin/bash
echo "Hello World !"
your_name="Eric"
echo $your_name
your_name="Darren"
echo ${your_name}

str="Hello, I know you are \"$your_name\"! \n"
echo -e $str

#获取字符串长度
echo ${#str}

#提取子字符串
echo ${str:1:5}


#查找子字符串
{
string="runoob is a great site"
echo `expr index "$string" io`
}


myUrl="https://www.google.com"
readonly myUrl

# myUrl="https://www.runoob.com"

array_name=(
value0
value1
value2
value3
)
echo ${array_name[0]}
# 取得数组元素的个数
length=${#array_name[@]}
# 或者
length=${#array_name[*]}
# 取得数组单个元素的长度
lengthn=${#array_name[n]}


:<<EOF
多行注释写法
注释内容...
注释内容...
注释内容...
EOF

