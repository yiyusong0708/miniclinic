-- 1. 醫生資料 (改用 PostgreSQL 的 ON CONFLICT 語法)
INSERT INTO doctor (doctor_id, name, department, specialty, password_hash) VALUES
    ('D001', '陳志明醫師', '家醫科', '一般內科、慢性病管理','$2a$10$XhyEgd4qh5TXJa7NkMg3gOqsJxATykAyJERH7ZqTD7eEPVlcmgewm'),
    ('D002', '林佩君醫師', '內科',   '心臟血管、高血壓', '$2a$10$/x/fVm66HZJWeeYZRUbPp..gS9Czgs3a27RjYQPs75obpRoUWU9ZC'),
    ('D003', '王建華醫師', '復健科', '運動傷害、脊椎復健', '$2a$10$4fZBPZq1NJmqW5MUgOUsqukV6OiTJutAKR/WbiFiQ6PRTjFbNsMFy'),
    ('D004', '李美玲醫師', '小兒科', '兒童感冒、疫苗接種',  '$2a$10$ZlsUgEo2MOm0RYxwcP55qukrjipEXYNKyyRfdIKkOEv7RpuXEPhxK'),
    ('D005', '張雅筑醫師', '身心科', '焦慮、失眠、情緒調適', '$2a$10$XsgY9Cmk7PqJ2pve2k4xwuTnV/hakC6LOGJqicQyjH.wDiM7PQhWa')
ON CONFLICT (doctor_id) DO NOTHING;

-- 2. 病患資料
INSERT INTO patient (chart_no, name, gender, phone, birth_date) VALUES 
('TEST00001', '林小明', '男', '0912345678', '1995-08-12'),
('TEST00002', '陳美玲', '女', '0923456789', '1988-03-25'),
('TEST00003', '張三豐', '男', '0934567890', '2002-11-30')
ON CONFLICT (chart_no) DO NOTHING;

-- 3. 掛號資料 (PostgreSQL 抓今日日期要改用 CURRENT_DATE)
INSERT INTO appointment (appt_id, chart_no, doctor_id, appt_date, time_slot, status) VALUES 
(1, 'TEST00001', 'D001', CURRENT_DATE, '上午診', 'BOOKED'),
(2, 'TEST00002', 'D001', CURRENT_DATE, '下午診', 'BOOKED'),
(3, 'TEST00003', 'D002', CURRENT_DATE, '夜間診', 'BOOKED')
ON CONFLICT (appt_id) DO NOTHING;