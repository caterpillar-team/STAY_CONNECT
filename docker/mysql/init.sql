CREATE DATABASE IF NOT EXISTS stayconnect;
DROP USER IF EXISTS 'princess'@'%';
GRANT ALL PRIVILEGES ON *.* TO 'princess'@'%';
FLUSH PRIVILEGES;
ALTER USER 'princess'@'%' IDENTIFIED BY 'princess';