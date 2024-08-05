-- Active: 1721803062715@@8.137.96.68@3306@item_sell_db_test
drop database if exists pengyou;

create database pengyou;

use pengyou;

CREATE TABLE user (
                      id INT UNSIGNED AUTO_INCREMENT,
                      username VARCHAR(50) NOT NULL,
                      password VARCHAR(64) NOT NULL,
                      email VARCHAR(50),
                      phone VARCHAR(20),
                      login_time TIMESTAMP,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      delete_at TIMESTAMP,
                      status SMALLINT DEFAULT 1,
                      heart_beat_time TIMESTAMP,
                      client_ip VARCHAR(50),
                      is_logout TINYINT DEFAULT 0,
                      log_out_time TIMESTAMP,
                      device_info VARCHAR(255),
                      created_person INT UNSIGNED,
                      updated_person INT UNSIGNED,
                      PRIMARY KEY (id),
                      UNIQUE INDEX idx_username (username),
                      INDEX idx_email (email),
                      INDEX idx_phone (phone)
);

CREATE TABLE user_profile (
                              user_id INT UNSIGNED NOT NULL,
                              display_name VARCHAR(50),
                              avatar_id VARCHAR(255),
                              bio VARCHAR(255),
                              gender TINYINT,
                              birthday DATE,
                              location VARCHAR(100),
                              occupation VARCHAR(100),
                              education VARCHAR(100),
                              school VARCHAR(100),
                              major VARCHAR(100),
                              company VARCHAR(100),
                              position VARCHAR(100),
                              website VARCHAR(255),
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              delete_at TIMESTAMP,
                              created_person INT UNSIGNED,
                              updated_person INT UNSIGNED,
                              PRIMARY KEY (user_id),
                              FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);


CREATE TABLE tag (
                     id INT UNSIGNED AUTO_INCREMENT,
                     name VARCHAR(63) NOT NULL,
                     description VARCHAR(255),
                     PRIMARY KEY (id),
                     UNIQUE INDEX idx_name (name)
);

CREATE TABLE user_tag_mapping (
                                  id INT UNSIGNED AUTO_INCREMENT,
                                  user_id INT UNSIGNED NOT NULL,
                                  tag_id INT UNSIGNED NOT NULL,
                                  PRIMARY KEY (id),
                                  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
                                  FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
);


CREATE TABLE user_friend (
                             id INT UNSIGNED AUTO_INCREMENT,
                             user_id INT UNSIGNED NOT NULL,
                             friend_id INT UNSIGNED NOT NULL,
                             status TINYINT DEFAULT 0,
                             request_date TIMESTAMP,
                             accepted_date TIMESTAMP,
                             require_person INT UNSIGNED,
                             relationship SMALLINT DEFAULT 1,
                             delete_at TIMESTAMP,
                             PRIMARY KEY (id),
                             FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
                             FOREIGN KEY (friend_id) REFERENCES user(id) ON DELETE CASCADE,
                             FOREIGN KEY (require_person) REFERENCES user(id) ON DELETE SET NULL
);


CREATE TABLE social_account (
                                id INT UNSIGNED AUTO_INCREMENT,
                                user_id INT UNSIGNED NOT NULL,
                                platform VARCHAR(63) NOT NULL,
                                link VARCHAR(255),
                                PRIMARY KEY (id),
                                FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
                                UNIQUE INDEX idx_user_platform (user_id, platform)
);


CREATE TABLE post (
                      id INT UNSIGNED AUTO_INCREMENT,
                      author INT UNSIGNED NOT NULL,
                      title VARCHAR(255),
                      content TEXT,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      status TINYINT DEFAULT 1,
                      created_person INT UNSIGNED,
                      updated_person INT UNSIGNED,
                      label INT UNSIGNED,
                      is_delete TINYINT DEFAULT 0,
                      PRIMARY KEY (id),
                      FOREIGN KEY (author) REFERENCES user(id) ON DELETE CASCADE,
                      FOREIGN KEY (created_person) REFERENCES user(id) ON DELETE SET NULL,
                      FOREIGN KEY (updated_person) REFERENCES user(id) ON DELETE SET NULL,
                      FOREIGN KEY (label) REFERENCES post_label(id) ON DELETE SET NULL
);


CREATE TABLE post_section (
                              id INT UNSIGNED AUTO_INCREMENT,
                              section VARCHAR(100),
                              description VARCHAR(255),
                              PRIMARY KEY (id),
                              UNIQUE INDEX idx_section (section)
);


CREATE TABLE post_label (
                            id INT UNSIGNED AUTO_INCREMENT,
                            label VARCHAR(100),
                            description VARCHAR(255),
                            PRIMARY KEY (id),
                            UNIQUE INDEX idx_label (label)
);


CREATE TABLE post_history (
                              id INT UNSIGNED AUTO_INCREMENT,
                              author INT UNSIGNED NOT NULL,
                              title VARCHAR(255),
                              content TEXT,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_person INT UNSIGNED,
                              label INT UNSIGNED,
                              is_delete TINYINT DEFAULT 0,
                              PRIMARY KEY (id),
                              FOREIGN KEY (author) REFERENCES user(id) ON DELETE CASCADE,
                              FOREIGN KEY (updated_person) REFERENCES user(id) ON DELETE SET NULL,
                              FOREIGN KEY (label) REFERENCES post_label(id) ON DELETE SET NULL
);


CREATE TABLE post_label_mapping (
                                    id INT UNSIGNED AUTO_INCREMENT,
                                    post_id INT UNSIGNED NOT NULL,
                                    label_id INT UNSIGNED NOT NULL,
                                    PRIMARY KEY (id),
                                    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
                                    FOREIGN KEY (label_id) REFERENCES post_label(id) ON DELETE CASCADE
);


CREATE TABLE post_section_mapping (
                                      id INT UNSIGNED AUTO_INCREMENT,
                                      section_id INT UNSIGNED NOT NULL,
                                      post_id INT UNSIGNED NOT NULL,
                                      PRIMARY KEY (id),
                                      FOREIGN KEY (section_id) REFERENCES post_section(id) ON DELETE CASCADE,
                                      FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
);


CREATE TABLE post_history_label_mapping (
                                            id INT UNSIGNED AUTO_INCREMENT,
                                            post_history_id INT UNSIGNED NOT NULL,
                                            label_id INT UNSIGNED NOT NULL,
                                            PRIMARY KEY (id),
                                            FOREIGN KEY (post_history_id) REFERENCES post_history(id) ON DELETE CASCADE,
                                            FOREIGN KEY (label_id) REFERENCES post_label(id) ON DELETE CASCADE
);


CREATE TABLE post_history_section_mapping (
                                              id INT UNSIGNED AUTO_INCREMENT,
                                              post_history_id INT UNSIGNED NOT NULL,
                                              section_id INT UNSIGNED NOT NULL,
                                              PRIMARY KEY (id),
                                              FOREIGN KEY (post_history_id) REFERENCES post_history(id) ON DELETE CASCADE,
                                              FOREIGN KEY (section_id) REFERENCES post_section(id) ON DELETE CASCADE
);


CREATE TABLE post_like (
                           id INT UNSIGNED AUTO_INCREMENT,
                           post_id INT UNSIGNED NOT NULL,
                           user_id INT UNSIGNED NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           PRIMARY KEY (id),
                           FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
                           FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);


CREATE TABLE post_dislike (
                              id INT UNSIGNED AUTO_INCREMENT,
                              post_id INT UNSIGNED NOT NULL,
                              user_id INT UNSIGNED NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              PRIMARY KEY (id),
                              FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
                              FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);


CREATE TABLE comment (
                         id INT UNSIGNED AUTO_INCREMENT,
                         post_id INT UNSIGNED NOT NULL,
                         user_id INT UNSIGNED NOT NULL,
                         content VARCHAR(255),
                         created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         created_person INT UNSIGNED,
                         updated_person INT UNSIGNED,
                         PRIMARY KEY (id),
                         FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
                         FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
                         FOREIGN KEY (created_person) REFERENCES user(id) ON DELETE SET NULL,
                         FOREIGN KEY (updated_person) REFERENCES user(id) ON DELETE SET NULL
);


CREATE TABLE comment_like (
                              id INT UNSIGNED AUTO_INCREMENT,
                              comment_id INT UNSIGNED NOT NULL,
                              user_id INT UNSIGNED NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              PRIMARY KEY (id),
                              FOREIGN KEY (comment_id) REFERENCES comment(id) ON DELETE CASCADE,
                              FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);


CREATE TABLE comment_history (
                                 id INT UNSIGNED AUTO_INCREMENT,
                                 post_id INT UNSIGNED NOT NULL,
                                 user_id INT UNSIGNED NOT NULL,
                                 content VARCHAR(255),
                                 updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_person INT UNSIGNED,
                                 is_delete TINYINT DEFAULT 0,
                                 PRIMARY KEY (id),
                                 FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
                                 FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
                                 FOREIGN KEY (updated_person) REFERENCES user(id) ON DELETE SET NULL
);


CREATE TABLE report (
                        id INT UNSIGNED AUTO_INCREMENT,
                        reported_id INT UNSIGNED NOT NULL,
                        reporter_id INT UNSIGNED NOT NULL,
                        reason VARCHAR(255),
                        report_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        status TINYINT DEFAULT 0,
                        is_delete TINYINT DEFAULT 0,
                        type SMALLINT,
                        PRIMARY KEY (id),
                        FOREIGN KEY (reported_id) REFERENCES user(id) ON DELETE CASCADE,
                        FOREIGN KEY (reporter_id) REFERENCES user(id) ON DELETE CASCADE
);


CREATE TABLE admin (
                       id INT UNSIGNED AUTO_INCREMENT,
                       username VARCHAR(100) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(100),
                       phone VARCHAR(100),
                       created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       created_person INT UNSIGNED,
                       updated_person INT UNSIGNED,
                       is_delete TINYINT DEFAULT 0,
                       role TINYINT DEFAULT 0,
                       PRIMARY KEY (id),
                       UNIQUE INDEX idx_username (username)
);


CREATE TABLE sensitive_word (
                                id INT UNSIGNED AUTO_INCREMENT,
                                word VARCHAR(100) NOT NULL,
                                PRIMARY KEY (id),
                                UNIQUE INDEX idx_word (word)
);


CREATE TABLE message_send (
                              id INT UNSIGNED AUTO_INCREMENT,
                              sender_id INT UNSIGNED NOT NULL,
                              recipient_id INT UNSIGNED NOT NULL,
                              content VARCHAR(511) NOT NULL,
                              sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              is_read TINYINT DEFAULT 0,
                              is_delete TINYINT DEFAULT 0,
                              type TINYINT DEFAULT 0,
                              PRIMARY KEY (id),
                              FOREIGN KEY (sender_id) REFERENCES user(id) ON DELETE CASCADE,
                              FOREIGN KEY (recipient_id) REFERENCES user(id) ON DELETE CASCADE
);


CREATE TABLE message_receive (
                                 id INT UNSIGNED AUTO_INCREMENT,
                                 message_send_id INT UNSIGNED NOT NULL,
                                 recipient_id INT UNSIGNED NOT NULL,
                                 read_at TIMESTAMP,
                                 is_delete TINYINT DEFAULT 0,
                                 type TINYINT DEFAULT 0,
                                 PRIMARY KEY (id),
                                 FOREIGN KEY (message_send_id) REFERENCES message_send(id) ON DELETE CASCADE,
                                 FOREIGN KEY (recipient_id) REFERENCES user(id) ON DELETE CASCADE
);


