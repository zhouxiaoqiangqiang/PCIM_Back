CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码(MD5加密)',
    real_name VARCHAR(50) COMMENT '真实姓名',
    role VARCHAR(20) DEFAULT 'user' COMMENT '角色(admin/user)',
    phone VARCHAR(20) COMMENT '手机号',
    status TINYINT DEFAULT 1 COMMENT '状态(0禁用/1启用)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_username (username),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

INSERT INTO sys_user (username, password, real_name, role, phone, status) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 'admin', '13800138000', 1),
('zhangwei', 'e10adc3949ba59abbe56e057f20f883e', '张伟', 'user', '13900139000', 1),
('lili', 'e10adc3949ba59abbe56e057f20f883e', '李丽', 'user', '13700137000', 0),
('wangqiang', 'e10adc3949ba59abbe56e057f20f883e', '王强', 'user', '13600136000', 1);

CREATE TABLE IF NOT EXISTS prison_prisoner (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '监犯ID',
    prisoner_no VARCHAR(50) NOT NULL UNIQUE COMMENT '监犯编号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender VARCHAR(10) COMMENT '性别',
    age INT COMMENT '年龄',
    id_card VARCHAR(18) COMMENT '身份证号',
    cell VARCHAR(50) COMMENT '监区',
    crime_type VARCHAR(100) COMMENT '罪名',
    sentence VARCHAR(50) COMMENT '刑期',
    entry_date DATE COMMENT '入所日期',
    status VARCHAR(20) DEFAULT '在押' COMMENT '状态(在押/已释放/保外就医)',
    remark TEXT COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_prisoner_no (prisoner_no),
    INDEX idx_cell (cell),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='监犯信息表';

-- 新增字段：出生日期、籍贯、社会背景、职业、照片
ALTER TABLE prison_prisoner ADD COLUMN birth_date DATE COMMENT '出生日期' AFTER entry_date;
ALTER TABLE prison_prisoner ADD COLUMN hometown VARCHAR(100) COMMENT '籍贯' AFTER birth_date;
ALTER TABLE prison_prisoner ADD COLUMN social_background VARCHAR(200) COMMENT '社会背景' AFTER hometown;
ALTER TABLE prison_prisoner ADD COLUMN occupation VARCHAR(50) COMMENT '职业' AFTER social_background;
ALTER TABLE prison_prisoner ADD COLUMN photo VARCHAR(500) COMMENT '照片URL' AFTER occupation;

INSERT INTO prison_prisoner (prisoner_no, name, gender, age, id_card, cell, crime_type, sentence, entry_date, birth_date, hometown, social_background, occupation, photo, status, remark) VALUES
('P20240001', '张大山', '男', 35, '110101198901011234', '一区', '抢劫罪', '10年', '2024-01-15', '1989-01-01', '北京海淀', '无业', '无', '', '在押', '表现良好'),
('P20240002', '李小花', '女', 28, '110101199602022345', '二区', '盗窃罪', '3年', '2024-02-20', '1996-02-02', '上海浦东', '学生', '学生', '', '在押', ''),
('P20240003', '王建国', '男', 45, '110101197903033456', '一区', '故意伤害罪', '7年', '2024-03-10', '1979-03-03', '广州天河', '农民', '农民', '', '保外就医', '患有心脏病'),
('P20240004', '赵晓燕', '女', 32, '110101199204044567', '三区', '诈骗罪', '5年', '2024-04-05', '1992-04-04', '深圳南山', '公司职员', '公司职员', '', '在押', ''),
('P20240005', '刘强', '男', 26, '110101199805055678', '四区', '贩毒罪', '15年', '2024-05-12', '1998-05-05', '成都锦江', '无业', '无业', '', '在押', '重点监控'),
('P20240006', '陈静', '女', 30, '110101199406066789', '二区', '贪污罪', '8年', '2024-06-18', '1994-06-06', '杭州西湖', '公务员', '公务员', '', '在押', ''),
('P20240007', '周明', '男', 52, '110101197207077890', '一区', '受贿罪', '12年', '2024-07-22', '1972-07-07', '南京鼓楼', '企业高管', '企业高管', '', '已释放', '刑满释放'),
('P20240008', '吴芳', '女', 29, '110101199508088901', '三区', '卖淫罪', '2年', '2024-08-08', '1995-08-08', '武汉武昌', '无业', '无业', '', '在押', '');

CREATE TABLE IF NOT EXISTS prison_labor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '劳动分配ID',
    prisoner_id BIGINT COMMENT '监犯ID',
    prisoner_no VARCHAR(50) COMMENT '监犯编号',
    name VARCHAR(50) COMMENT '姓名',
    cell VARCHAR(50) COMMENT '监区',
    work_type VARCHAR(50) COMMENT '工种',
    work_place VARCHAR(100) COMMENT '工作地点',
    work_hours INT COMMENT '每日工时',
    work_score INT DEFAULT 3 COMMENT '劳动评分(1-5)',
    status VARCHAR(20) DEFAULT '在岗' COMMENT '状态(在岗/休息/请假)',
    assign_date DATE COMMENT '分配日期',
    remark TEXT COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_prisoner_id (prisoner_id),
    INDEX idx_work_type (work_type),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='监犯劳动分配表';

INSERT INTO prison_labor (prisoner_id, prisoner_no, name, cell, work_type, work_place, work_hours, work_score, status, assign_date, remark) VALUES
(1, 'P20240001', '张大山', '一区', '生产加工', 'A车间', 8, 4, '在岗', '2024-01-15', ''),
(2, 'P20240002', '李小花', '二区', '后勤服务', '食堂', 6, 5, '在岗', '2024-02-20', '表现良好'),
(4, 'P20240004', '赵晓燕', '三区', '生产加工', 'B车间', 8, 3, '休息', '2024-04-05', ''),
(5, 'P20240005', '刘强', '四区', '基建维修', '工地', 8, 2, '在岗', '2024-05-12', '需加强管理'),
(6, 'P20240006', '陈静', '二区', '文体活动', '活动室', 4, 5, '请假', '2024-06-18', '身体不适'),
(8, 'P20240008', '吴芳', '三区', '后勤服务', '卫生清洁', 6, 4, '在岗', '2024-08-08', '');

CREATE TABLE IF NOT EXISTS prison_attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '出勤记录ID',
    prisoner_id BIGINT COMMENT '监犯ID',
    prisoner_no VARCHAR(50) COMMENT '监犯编号',
    name VARCHAR(50) COMMENT '姓名',
    cell VARCHAR(50) COMMENT '监区',
    roll_call_time DATETIME COMMENT '点名时间',
    status VARCHAR(20) DEFAULT '正常' COMMENT '状态(正常/迟到/早退/缺勤)',
    check_in_time DATETIME COMMENT '签到时间',
    check_out_time DATETIME COMMENT '签退时间',
    remark TEXT COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_prisoner_id (prisoner_id),
    INDEX idx_cell (cell),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='监犯出勤点名表';

INSERT INTO prison_attendance (prisoner_id, prisoner_no, name, cell, roll_call_time, status, check_in_time, check_out_time, remark) VALUES
(1, 'P20240001', '张大山', '一区', '2024-01-15 06:30:00', '正常', '2024-01-15 06:25:00', '2024-01-15 18:00:00', ''),
(2, 'P20240002', '李小花', '二区', '2024-01-15 06:30:00', '正常', '2024-01-15 06:28:00', '2024-01-15 18:00:00', ''),
(3, 'P20240003', '王建国', '一区', '2024-01-15 06:30:00', '缺勤', NULL, NULL, '保外就医'),
(4, 'P20240004', '赵晓燕', '三区', '2024-01-15 06:30:00', '迟到', '2024-01-15 06:45:00', '2024-01-15 18:00:00', '身体不适'),
(5, 'P20240005', '刘强', '四区', '2024-01-15 06:30:00', '正常', '2024-01-15 06:20:00', '2024-01-15 18:00:00', ''),
(6, 'P20240006', '陈静', '二区', '2024-01-15 06:30:00', '早退', '2024-01-15 06:26:00', '2024-01-15 16:30:00', '请假'),
(7, 'P20240007', '周明', '一区', '2024-01-15 06:30:00', '缺勤', NULL, NULL, '已释放'),
(8, 'P20240008', '吴芳', '三区', '2024-01-15 06:30:00', '正常', '2024-01-15 06:22:00', '2024-01-15 18:00:00', '');
