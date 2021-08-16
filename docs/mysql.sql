CREATE TABLE `user` (
  `openid` varchar(255) PRIMARY KEY COMMENT '用户openid',
  `nickname` varchar(255) COMMENT '用户昵称',
  `sex` int COMMENT '性别',
  `language` varchar(255) COMMENT '语言',
  `city` varchar(255) COMMENT '城市',
  `province` varchar(255) COMMENT '省份',
  `country` varchar(255) COMMENT '国家',
  `headimgurl` varchar(255) COMMENT '头像地址',
  `subscribe` int COMMENT '是否关注',
  `subscribe_time` datetime COMMENT '关注时间',
  `unionid` varchar(255) COMMENT '微信unionid',
  `remark` varchar(255) COMMENT '备注',
  `groupid` int COMMENT '用户所在分组ID',
  `tagid_list` varchar(255) COMMENT '被打上的标签ID列表',
  `subscribe_scene` varchar(255) COMMENT '用户关注的渠道来源',
  `qr_scene` varchar(255) COMMENT '二维码扫码场景',
  `qr_scene_str` varchar(255) COMMENT '二维码扫码场景描述',
  `create_time` datetime COMMENT '创建时间',
  `update_time` datetime COMMENT '更新时间'
);

CREATE TABLE `qrcode_batch` (
  `qrcode_batch_id` varchar(255) PRIMARY KEY NOT NULL COMMENT '批次号',
  `batch_name` varchar(255) COMMENT '批次名称',
  `creator` varchar(255) COMMENT '创建人',
  `create_time` datetime COMMENT '创建时间',
  `updator` varchar(255) COMMENT '更新人',
  `update_time` datetime COMMENT '更新时间',
  `batch_quantity` int COMMENT '批次数量',
  `amount` decimal COMMENT '金额',
  `bak` varchar(255) COMMENT '备注'
);

CREATE TABLE `coupon` (
  `coupon_id` varchar(255) PRIMARY KEY NOT NULL COMMENT '优惠券ID',
  `coupon_type_id` varchar(255) COMMENT '优惠券类别',
  `start_time` date COMMENT '优惠券开始时间',
  `end_time` date COMMENT '优惠券结束时间',
  `amount` decimal COMMENT '优惠券金额',
  `coupon_status` int COMMENT '优惠券状态（0:已生成待分配，1:已分配待发放，2:已发放待领取，3:已领取代核销，4:已核销，9:已作废）',
  `creator` varchar(255) COMMENT '创建人',
  `create_time` datetime COMMENT '创建时间',
  `updater` varchar(255) COMMENT '更新人',
  `update_time` datetime COMMENT '更新时间',
  `below_openid` varchar(255) COMMENT '所属人',
  `coupon_name` varchar(255) COMMENT '券名称'
);

CREATE TABLE `qrcode_batch_distribute` (
  `distribute_id` varchar(255) PRIMARY KEY NOT NULL COMMENT '主键id',
  `qrcode_batch_id` varchar(255) COMMENT '优惠券批次id',
  `coupon_id` varchar(255) COMMENT '优惠券id',
  `staff_openid` varchar(255) COMMENT '员工openid'
);

CREATE TABLE `coupon_given` (
  `given_id` varchar(255) PRIMARY KEY NOT NULL COMMENT 'id',
  `coupon_id` varchar(255) COMMENT '优惠券id',
  `given_openid` varchar(255) COMMENT '转赠人',
  `receive_openid` varchar(255) COMMENT '接收人',
  `given_time` datetime COMMENT '转赠时间'
);

CREATE TABLE `user_role` (
  `openid` varchar(255) PRIMARY KEY COMMENT '微信openid',
  `name` varchar(255) COMMENT '真实姓名',
  `role` varchar(255) COMMENT '角色（admin，staff，customer）',
  `status` int COMMENT '状态'
);

ALTER TABLE `coupon` ADD FOREIGN KEY (`below_openid`) REFERENCES `user` (`openid`);

ALTER TABLE `user_role` ADD FOREIGN KEY (`openid`) REFERENCES `user` (`openid`);

ALTER TABLE `coupon` ADD FOREIGN KEY (`coupon_id`) REFERENCES `qrcode_batch_distribute` (`coupon_id`);

ALTER TABLE `qrcode_batch_distribute` ADD FOREIGN KEY (`qrcode_batch_id`) REFERENCES `qrcode_batch` (`qrcode_batch_id`);

ALTER TABLE `coupon_given` ADD FOREIGN KEY (`given_id`) REFERENCES `coupon` (`coupon_id`);

ALTER TABLE `user` COMMENT = "用户表";

ALTER TABLE `qrcode_batch` COMMENT = "优惠券批次表";

ALTER TABLE `coupon` COMMENT = "优惠券表";

ALTER TABLE `qrcode_batch_distribute` COMMENT = "优惠券分配表";

ALTER TABLE `coupon_given` COMMENT = "优惠券转赠记录表";

ALTER TABLE `user_role` COMMENT = "用户角色表";

