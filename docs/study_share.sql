/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : study_share

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 02/05/2025 12:43:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分组名称',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典字段名称',
  `value` int(255) DEFAULT NULL COMMENT '字典字段值',
  `sort` int(255) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES (1, 'language_type', '语文', 1, 1);
INSERT INTO `dict` VALUES (2, 'language_type', '数学', 2, 2);
INSERT INTO `dict` VALUES (3, 'language_type', '英语', 3, 3);
INSERT INTO `dict` VALUES (9, 'language_type', '物理', 4, 4);

-- ----------------------------
-- Table structure for s_danmu
-- ----------------------------
DROP TABLE IF EXISTS `s_danmu`;
CREATE TABLE `s_danmu`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `video_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '影视id',
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '弹幕内容',
  `uid` int(255) DEFAULT NULL COMMENT '用户id\r\n',
  `time` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '弹幕发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_danmu
-- ----------------------------
INSERT INTO `s_danmu` VALUES (1, '1', '第一条', 1, '2025-03-04');
INSERT INTO `s_danmu` VALUES (2, '1', 'haha ', 1, '2025-03-04');
INSERT INTO `s_danmu` VALUES (3, '1', 'lala', 1, '2025-03-04');
INSERT INTO `s_danmu` VALUES (4, '1', '再来', 1, '2025-03-04');
INSERT INTO `s_danmu` VALUES (5, '1', '哈哈', 1, '2025-03-04');
INSERT INTO `s_danmu` VALUES (6, '1', 'lalala', 1, '2025-04-29');

-- ----------------------------
-- Table structure for s_reply
-- ----------------------------
DROP TABLE IF EXISTS `s_reply`;
CREATE TABLE `s_reply`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `talk_id` int(10) NOT NULL COMMENT '评论表的id',
  `user_id` int(10) NOT NULL COMMENT '回复人的id',
  `reply_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回复的内容',
  `reply_time` datetime(0) DEFAULT NULL COMMENT '回复的时间',
  `rname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '被回复人的名称',
  `reply_href` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '回复地址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `video_talke_id`(`talk_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_reply
-- ----------------------------
INSERT INTO `s_reply` VALUES (1, 1, 1, '他的讲的也挺好啊，真的太棒了', '2025-04-29 11:29:09', 'bigWhite', NULL);
INSERT INTO `s_reply` VALUES (2, 2, 1, '看看我的', '2025-05-02 09:22:56', 'bigWhite', 'localhost:9090/video/getVideo?id=1');
INSERT INTO `s_reply` VALUES (3, 1, 2, '哈啊哈', '2025-05-02 09:43:40', 'bigWhite', NULL);
INSERT INTO `s_reply` VALUES (4, 2, 2, '看到了，讲得很好', '2025-05-02 09:51:42', 'bigWhite', NULL);

-- ----------------------------
-- Table structure for s_talk
-- ----------------------------
DROP TABLE IF EXISTS `s_talk`;
CREATE TABLE `s_talk`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `video_id` int(255) NOT NULL COMMENT '被评论的视频id',
  `user_id` int(10) NOT NULL COMMENT '评论者id',
  `talk_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `publish_time` datetime(0) DEFAULT NULL COMMENT '评论发布时间',
  `href_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '链接地址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `video_id`(`video_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_talk
-- ----------------------------
INSERT INTO `s_talk` VALUES (1, 1, 1, '这个视频讲的还不错，看看我的视频把', '2025-04-29 11:26:52', 'http://localhost:9090/1');
INSERT INTO `s_talk` VALUES (2, 1, 1, '学到了', '2025-05-02 09:10:15', NULL);
INSERT INTO `s_talk` VALUES (3, 1, 2, '来啦~', '2025-05-02 09:42:44', NULL);

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户密码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户邮箱',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户头像',
  `role` int(255) DEFAULT NULL COMMENT '1管理员 0普通用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES (1, 'bigWhite', '4297f44b13955235245b2497399d7a93', '991313901@qq.com', '/pics/4ef322acb5ff44edb48f077dd3a8642a.jpg', 0);
INSERT INTO `s_user` VALUES (2, 'muyang', '4297f44b13955235245b2497399d7a93', NULL, '/pics/6771804c14984a1d99dcc3b27c118aea.jpg', 1);

-- ----------------------------
-- Table structure for s_video
-- ----------------------------
DROP TABLE IF EXISTS `s_video`;
CREATE TABLE `s_video`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '视频标题',
  `type` int(10) DEFAULT NULL COMMENT '科目类型',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '视频封面',
  `base_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '视频地址',
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `status` int(255) DEFAULT NULL COMMENT '食品状态 0 未审核 1未通过 2通过',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_video
-- ----------------------------
INSERT INTO `s_video` VALUES (1, '语文教学', 1, '/pics/6771804c14984a1d99dcc3b27c118aea.jpg', '/videos/83edeeed37ac4167a104452c17f158cb.mp4', 1, 2);
INSERT INTO `s_video` VALUES (2, '数学竞赛讲解视频，有兴趣来看看', 2, '/pics/b9d2016aff5b4ac1b51e4f3a6c5d2ae4.jpg', '/videos/5fe6eddadf394ef89c34af95e2b99035.mp4', 1, 2);

SET FOREIGN_KEY_CHECKS = 1;
