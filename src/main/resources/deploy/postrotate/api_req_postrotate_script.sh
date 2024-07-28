#!/bin/bash

# Variables
LOGFILE="$LOG_PATH/logfile.log.1"
ROTATE_DIR="$LOG_BACKUP_PATH/rotated_logs"
HOSTNAME=$(hostname)
MYSQL_USER="your_mysql_user"
MYSQL_PASSWORD="your_mysql_password"
MYSQL_DATABASE="your_database"
TABLE="rotate_record"
RETENTION_COUNT=4320 # 144 * 30

# Ensure rotate directory exists
mkdir -p ${ROTATE_DIR}

# Get current time for rotated log file
CURRENT_TIME=$(date +'%Y%m%d_%H%M%S')
ROTATED_LOGFILE="${ROTATE_DIR}/rotated_${CURRENT_TIME}.gzip"

# Compress the rotated log file
gzip -c ${LOGFILE} > ${ROTATED_LOGFILE}
rm ${LOGFILE}

# Get the number of lines in the rotated log file
LINE_COUNT=$(zcat ${ROTATED_LOGFILE} | wc -l)

# Get the current datetime for MySQL record
LOG_TIME=$(date +'%Y-%m-%d %H:%M:%S')

# Insert the log rotation record into the MySQL database
mysql -u${MYSQL_USER} -p${MYSQL_PASSWORD} -D${MYSQL_DATABASE} -e "
    INSERT INTO ${TABLE} (서버명, 로그시간, 로그행수)
    VALUES ('${HOSTNAME}', '${LOG_TIME}', ${LINE_COUNT});
"

# Remove old rotated log files if retention count is exceeded
FILE_COUNT=$(ls ${ROTATE_DIR} | wc -l)
if [ ${FILE_COUNT} -gt ${RETENTION_COUNT} ]; then
    ls -t ${ROTATE_DIR} | tail -n +$((${RETENTION_COUNT} + 1)) | xargs -I {} rm -f ${ROTATE_DIR}/{}
fi

# Grant execution permission to api_req_postrotate_script.sh
# chmod +x $LOG_PATH/api_req_postrotate_script.sh

# 로그 스키마 선언하기
# CREATE DATABASE log_monitor;
# USE log_monitor;
# CREATE TABLE api_req_log_rotate_record (
#     id INT AUTO_INCREMENT PRIMARY KEY,
#     서버명 VARCHAR(255) NOT NULL,
#     로그시간 DATETIME NOT NULL,
#     로그행수 INT NOT NULL
# );
