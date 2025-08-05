-- テスト実行前に既存のデータをクリア
DELETE FROM tasks;

-- テスト用の初期データを挿入
INSERT INTO tasks (id, title, description, is_completed, due_date) VALUES
(100, 'Existing Task 1', 'テストの説明', false, '2026-10-01 10:00:00+00:00'),
(200, 'Existing Task 2', '', true, '2025-11-01 11:00:00+00:00');