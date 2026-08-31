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
-- 新增字段：风险等级
ALTER TABLE prison_prisoner ADD COLUMN risk_level VARCHAR(20) COMMENT '风险等级(高风险/中风险/低风险/未评定)' AFTER status;

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

-- ===================== 新增表：考核记录表 =====================
CREATE TABLE IF NOT EXISTS prison_assessment (
                                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '考核记录ID',
                                                 prisoner_no VARCHAR(50) NOT NULL COMMENT '监犯编号',
    name VARCHAR(50) COMMENT '姓名',
    cell VARCHAR(50) COMMENT '监区',
    assess_date DATE COMMENT '考核日期',
    type VARCHAR(20) COMMENT '考核类型(奖励/日常考核/处罚/月度汇总)',
    item VARCHAR(500) COMMENT '考核事项',
    score INT DEFAULT 0 COMMENT '分值(正为加分,负为扣分)',
    level VARCHAR(50) COMMENT '奖惩等级(记功/表扬/重大立功/警告/记过)',
    handler VARCHAR(50) COMMENT '经办人',
    remark TEXT COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_prisoner_no (prisoner_no),
    INDEX idx_type (type),
    INDEX idx_assess_date (assess_date)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='监犯考核记录表';

-- ===================== 新增表：教育学习记录表 =====================
CREATE TABLE IF NOT EXISTS prison_education (
                                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '教育记录ID',
                                                prisoner_no VARCHAR(50) NOT NULL COMMENT '监犯编号',
    name VARCHAR(50) COMMENT '姓名',
    cell VARCHAR(50) COMMENT '监区',
    course_name VARCHAR(200) COMMENT '课程名称',
    edu_date DATE COMMENT '学习日期',
    duration INT DEFAULT 0 COMMENT '学时(小时)',
    teacher VARCHAR(50) COMMENT '授课人',
    score INT DEFAULT 0 COMMENT '考核成绩(0-100)',
    result VARCHAR(20) COMMENT '结果(合格/不合格)',
    remark TEXT COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_prisoner_no (prisoner_no),
    INDEX idx_edu_date (edu_date),
    INDEX idx_result (result)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='监犯教育学习记录表';

-- ===================== 新增表：风险评估表 =====================
CREATE TABLE IF NOT EXISTS prison_risk (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '风险评估ID',
                                           prisoner_no VARCHAR(50) NOT NULL COMMENT '监犯编号',
    name VARCHAR(50) COMMENT '姓名',
    cell VARCHAR(50) COMMENT '监区',
    crime_type VARCHAR(100) COMMENT '罪名',
    assess_date DATE COMMENT '评估日期',
    violence INT DEFAULT 0 COMMENT '暴力倾向得分(0-100)',
    escape INT DEFAULT 0 COMMENT '脱逃风险得分(0-100)',
    suicide INT DEFAULT 0 COMMENT '自杀自残得分(0-100)',
    health INT DEFAULT 0 COMMENT '健康风险得分(0-100)',
    total_score INT DEFAULT 0 COMMENT '综合得分',
    risk_level VARCHAR(20) COMMENT '风险等级(高风险/中风险/低风险)',
    assessor VARCHAR(50) COMMENT '评估人',
    remark TEXT COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_prisoner_no (prisoner_no),
    INDEX idx_risk_level (risk_level)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='监犯风险评估表';

-- ===================== 新增表：风险变更日志表 =====================
CREATE TABLE IF NOT EXISTS prison_risk_log (
                                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID',
                                               prisoner_no VARCHAR(50) COMMENT '监犯编号',
    name VARCHAR(50) COMMENT '姓名',
    old_level VARCHAR(20) COMMENT '原风险等级',
    new_level VARCHAR(20) COMMENT '新风险等级',
    operator VARCHAR(50) COMMENT '操作人',
    reason VARCHAR(500) COMMENT '变更原因',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '变更时间',
    INDEX idx_prisoner_no (prisoner_no)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='风险等级变更日志表';

-- ===================== 新增表：刑期档案表 =====================
CREATE TABLE IF NOT EXISTS prison_sentence (
                                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '刑期档案ID',
                                               prisoner_no VARCHAR(50) NOT NULL COMMENT '入监编号(RJ+年份+流水号)',
    name VARCHAR(50) COMMENT '姓名',
    cell VARCHAR(50) COMMENT '监区',
    crime_type VARCHAR(100) COMMENT '罪名',
    entry_date DATE COMMENT '入监日期',
    original_sentence VARCHAR(50) COMMENT '原判刑期(如:10年)',
    sentence_start DATE COMMENT '刑期起算日',
    reduced_months INT DEFAULT 0 COMMENT '累计减刑(月)',
    release_date DATE COMMENT '预计释放日期',
    parole VARCHAR(20) DEFAULT '未假释' COMMENT '假释状态(未假释/申请中/已假释)',
    status VARCHAR(20) DEFAULT '在押' COMMENT '状态(在押/已释放/保外就医)',
    remark TEXT COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_prisoner_no (prisoner_no),
    INDEX idx_release_date (release_date),
    INDEX idx_status (status)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='入监与刑期档案表';

-- ===================== 新增表：刑期变动记录表 =====================
CREATE TABLE IF NOT EXISTS prison_sentence_change (
                                                      id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '变动记录ID',
                                                      prisoner_no VARCHAR(50) COMMENT '入监编号',
    name VARCHAR(50) COMMENT '姓名',
    change_type VARCHAR(50) COMMENT '变动类型(减刑/假释/暂予监外执行/更正)',
    old_value VARCHAR(100) COMMENT '变动前值',
    new_value VARCHAR(100) COMMENT '变动后值',
    change_date DATE COMMENT '变动日期',
    operator VARCHAR(50) COMMENT '操作人',
    reason VARCHAR(500) COMMENT '变动原因',
    remark TEXT COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_prisoner_no (prisoner_no),
    INDEX idx_change_type (change_type)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='刑期变动记录表';

-- ===================== 新增表：离监申请表 =====================
CREATE TABLE IF NOT EXISTS prison_release (
                                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '申请ID',
                                              apply_no VARCHAR(50) NOT NULL COMMENT '申请编号',
    prisoner_no VARCHAR(50) COMMENT '监犯编号',
    name VARCHAR(50) COMMENT '姓名',
    cell VARCHAR(50) COMMENT '监区',
    type VARCHAR(50) COMMENT '离监类型(刑满释放/假释/暂予监外执行/特许离监/奖励离监探亲)',
    reason TEXT COMMENT '申请理由',
    status VARCHAR(20) DEFAULT 'PENDING' COMMENT '状态(PENDING/APPROVED/REJECTED)',
    approver VARCHAR(50) COMMENT '审批人',
    approve_time DATETIME COMMENT '审批时间',
    reject_reason TEXT COMMENT '驳回原因',
    version INT DEFAULT 0 COMMENT '版本号(乐观锁)',
    apply_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_prisoner_no (prisoner_no),
    INDEX idx_apply_no (apply_no),
    INDEX idx_status (status)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='离监申请表';

-- ===================== 新增表：预警记录表 =====================
CREATE TABLE IF NOT EXISTS prison_warning (
                                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '预警ID',
                                              warning_no VARCHAR(50) NOT NULL COMMENT '预警编号',
    prisoner_no VARCHAR(50) COMMENT '监犯编号',
    name VARCHAR(50) COMMENT '姓名',
    cell VARCHAR(50) COMMENT '监区',
    type VARCHAR(50) COMMENT '预警类型(短刑期预警/长期无考核/健康异常等)',
    level VARCHAR(20) COMMENT '级别(紧急/重要/一般)',
    warning_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '预警时间',
    content VARCHAR(500) COMMENT '预警内容',
    status VARCHAR(20) DEFAULT '未处理' COMMENT '状态(未处理/处理中/已处理)',
    source VARCHAR(100) COMMENT '命中规则(ShortSentenceRule等)',
    handler VARCHAR(50) COMMENT '处理人',
    handle_time DATETIME COMMENT '处理时间',
    handle_result TEXT COMMENT '处理结果',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读(0未读/1已读)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_warning_no (warning_no),
    INDEX idx_prisoner_no (prisoner_no),
    INDEX idx_status (status),
    INDEX idx_is_read (is_read)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预警记录表';

-- ===================== 新增表：谈话记录表 =====================
CREATE TABLE IF NOT EXISTS prison_talk (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '谈话记录ID',
                                           prisoner_no VARCHAR(50) NOT NULL COMMENT '监犯编号',
    name VARCHAR(50) COMMENT '姓名',
    cell VARCHAR(50) COMMENT '监区',
    talk_date DATE COMMENT '谈话日期',
    type VARCHAR(50) COMMENT '谈话类型(个别谈话/集体教育/心理辅导/入监谈话/出监谈话)',
    location VARCHAR(100) COMMENT '谈话地点',
    content TEXT COMMENT '谈话内容',
    talker VARCHAR(50) COMMENT '谈话人',
    follow_up VARCHAR(500) COMMENT '后续跟进措施',
    remark TEXT COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_prisoner_no (prisoner_no),
    INDEX idx_talk_date (talk_date),
    INDEX idx_type (type)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='监犯谈话记录表';

-- ===================== 新增表：探视登记表 =====================
CREATE TABLE IF NOT EXISTS prison_visit (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '探视记录ID',
                                            prisoner_no VARCHAR(50) NOT NULL COMMENT '监犯编号',
    name VARCHAR(50) COMMENT '监犯姓名',
    cell VARCHAR(50) COMMENT '监区',
    visitor_name VARCHAR(50) COMMENT '探视人姓名',
    visitor_id_card VARCHAR(18) COMMENT '探视人身份证号',
    relation VARCHAR(20) COMMENT '与罪犯关系(配偶/父母/子女等)',
    visit_time DATETIME COMMENT '探视时间',
    remark TEXT COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_prisoner_no (prisoner_no),
    INDEX idx_visit_time (visit_time),
    INDEX idx_relation (relation)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='监犯探视登记表';

-- ===================== 新增表：系统角色表 =====================
CREATE TABLE IF NOT EXISTS sys_role (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
                                        role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_key VARCHAR(50) NOT NULL COMMENT '角色标识',
    description VARCHAR(500) COMMENT '角色描述',
    user_count INT DEFAULT 0 COMMENT '关联账号数(冗余计数)',
    status TINYINT DEFAULT 1 COMMENT '状态(0禁用/1启用)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_role_key (role_key),
    INDEX idx_status (status)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- 角色表初始数据
INSERT INTO sys_role (role_name, role_key, description, user_count, status) VALUES
                                                                                ('超级管理员', 'admin', '拥有系统全部权限', 1, 1),
                                                                                ('监狱民警', 'police', '日常管理、劳动分配、谈话教育等操作权限', 2, 1),
                                                                                ('风险评估员', 'assessor', '专门负责风险评估和预警处理', 0, 1),
                                                                                ('普通用户', 'user', '基础查看权限', 1, 0);
