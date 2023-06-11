#!/bin/bash
# 定义一个变量，用于存放 awk 命令的输出
docker ps| grep '5' | awk '{print $1}' > file.txt
result=$(awk '{print $1}' file.txt)
# 将输出按行分割成数组
IFS=$'\n' read -d '' -r -a lines <<< "$result"
# 循环输出数组中的每一行
for line in "${lines[@]}"
do
	expect -c "send \"echo 'current handler $line'\";
		spawn docker exec -it $line bash;
		send \"ping -c 1 -w 200 www.baidu.com\n\";
		expect {
			\"*time*\" {
				send \"echo 'ping is success $line'\n\";
				send \"exit\n\";
				interact;
			}
			\"*\" {
				send \"echo 'end'\n\";
				send \"exit\n\";
				interact;
			}
		}
		"
done