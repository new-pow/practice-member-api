-- wanted_db.member 테이블에 기본 데이터 추가
INSERT INTO wanted_db.member (email, password, username, created_at)
VALUES
    ('new-pow@gmail.com', 'default_password1', 'new-pow', NOW()),
    ('user2@example.com', 'password2', 'User2', NOW()+1),
    ('user3@example.com', 'password3', 'User3', NOW()+2),
    ('user4@example.com', 'password4', 'User4', NOW()+3),
    ('user5@example.com', 'password5', 'User5', NOW()+4);

-- wanted_db.post 테이블에 기본 데이터 추가
INSERT INTO wanted_db.post (title, author_id, created_at, updated_at)
VALUES
    ('첫번째 글입니다', 1, NOW(), null),
    ('어떤 글을 써야하나?', 2, NOW()+2, null),
    ('테스트 글입니다.', 3, NOW()+3, null),
    ('🎉 와아', 4, NOW()+4, null),
    ('다섯 번째 글입니다.', 5, NOW()+5, null);

-- wanted_db.post_contents 테이블에 기본 데이터 추가
INSERT INTO wanted_db.post_contents (content, post_id)
VALUES
    ('포스트 내용 1', 1),
    ('포스트 내용 2', 2),
    ('포스트 내용 3', 3),
    ('포스트 내용 4', 4),
    ('포스트 내용 5', 5);

-- wanted_db.member_auth 테이블에 기본 데이터 추가
INSERT INTO wanted_db.member_auth (member_id, access_token, refresh_token, created_at, expired_at)
VALUES
    (1, 'ACCESS_TOKEN_1', 'REFRESH_TOKEN_1', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR)),
    (2, 'ACCESS_TOKEN_2', 'REFRESH_TOKEN_2', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR))
