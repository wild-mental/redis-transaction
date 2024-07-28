#!/bin/bash

# Variables
LOGFILE="/path/to/your/logfile.log.1"
HOSTNAME=$(hostname)
MYSQL_USER="your_mysql_user"
MYSQL_PASSWORD="your_mysql_password"
MYSQL_DATABASE="your_database"
TABLE="rotate_record"

# Get the number of lines in the rotated log file
LINE_COUNT=$(zcat ${LOGFILE}.gz | wc -l)

# Get the current datetime
LOG_TIME=$(date +'%Y-%m-%d %H:%M:%S')

# Insert the log rotation record into the MySQL database
mysql -u${MYSQL_USER} -p${MYSQL_PASSWORD} -D${MYSQL_DATABASE} -e "
    INSERT INTO ${TABLE} (서버명, 로그시간, 로그행수)
    VALUES ('${HOSTNAME}', '${LOG_TIME}', ${LINE_COUNT});
"

# 로그 스키마 선언하기
# CREATE DATABASE log_monitor;
# USE log_monitor;
# CREATE TABLE spring_log_rotate_record (
#     id INT AUTO_INCREMENT PRIMARY KEY,
#     서버명 VARCHAR(255) NOT NULL,
#     로그시간 DATETIME NOT NULL,
#     로그행수 INT NOT NULL
# );

# spring_postrotate_script.sh 파일에 실행 권한 부여하기
# chmod +x /path/to/spring_postrotate_script.sh
