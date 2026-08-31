-- ============================================================
-- 监犯信息表新增字段SQL脚本
-- 新增字段：出生日期、籍贯、社会背景、职业、照片
-- 生成时间：2025-01-20
-- ============================================================

-- 1. 出生日期
ALTER TABLE prison_prisoner ADD COLUMN birth_date DATE COMMENT '出生日期' AFTER entry_date;

-- 2. 籍贯
ALTER TABLE prison_prisoner ADD COLUMN hometown VARCHAR(100) COMMENT '籍贯' AFTER birth_date;

-- 3. 社会背景
ALTER TABLE prison_prisoner ADD COLUMN social_background VARCHAR(200) COMMENT '社会背景' AFTER hometown;

-- 4. 职业
ALTER TABLE prison_prisoner ADD COLUMN occupation VARCHAR(50) COMMENT '职业' AFTER social_background;

-- 5. 照片
ALTER TABLE prison_prisoner ADD COLUMN photo VARCHAR(500) COMMENT '照片URL' AFTER occupation;

-- ============================================================
-- 如需更新存量数据，可执行以下示例语句（请根据实际情况修改）
-- ============================================================
-- UPDATE prison_prisoner SET birth_date = '1989-01-01' WHERE prisoner_no = 'P20240001';
-- UPDATE prison_prisoner SET hometown = '北京海淀' WHERE prisoner_no = 'P20240001';
-- UPDATE prison_prisoner SET occupation = '无业' WHERE prisoner_no = 'P20240001';
-- UPDATE prison_prisoner SET social_background = '无业' WHERE prisoner_no = 'P20240001';
-- UPDATE prison_prisoner SET photo = '/photos/default.jpg' WHERE prisoner_no = 'P20240001';
