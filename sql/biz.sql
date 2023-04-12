drop table if exists `user`;
create table `user`
(
    id          bigint(20) not null auto_increment comment 'id',
    nick_name   varchar(255) default null comment '用户名称',
    avatar_url  varchar(255) default null comment '用户头像',
    integral    float(20)    default '0' comment '用户积分',
    mobile      varchar(50)  default null comment '用户手机号',
    ma_open_id  varchar(255) default null comment '微信小程序开放id',
    is_del      char(1)      default '0' comment '删除标志（0代表存在 2代表删除）',
    create_by   varchar(64)  default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64)  default '' comment '更新者',
    update_time datetime comment '更新时间',
    primary key (id)
) engine=innodb auto_increment=200 comment = '用户表';

drop table if exists integral_good;
create table integral_good
(
    id          bigint(20) not null auto_increment comment 'id',
    name        varchar(255) default null comment '商品名称',
    integral    float(20)    default null comment '兑换需要的积分',
    stock       bigint(20) default null comment '商品库存',
    banners     varchar(255) default null comment '封面',
    description varchar(255) default null comment '商品描述',
    content     text(1234) default null comment '商品内容',
    is_del      char(1)      default '0' comment '删除标志（0代表存在 2代表删除）',
    create_by   varchar(64)  default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64)  default '' comment '更新者',
    update_time datetime comment '更新时间',
    primary key (id)
) engine=innodb auto_increment=200 comment = '积分商品表';

drop table if exists integral_rel;
create table integral_rel
(
    id          bigint(20) not null auto_increment comment 'id',
    user_id     bigint(20) default '0' comment '用户id',
    info        text(1234) default null comment '记录积分变动的详情 ? 购买商品 ? 答题',
    `action`    int         default '0' comment '1 为 减少， 2 为增加',
    num         float(20)   default '0' comment '一次积分变动记录的数量',
    is_del      char(1)     default '0' comment '删除标志（0代表存在 2代表删除）',
    create_by   varchar(64) default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64) default '' comment '更新者',
    update_time datetime comment '更新时间',
    primary key (id)
) engine=innodb auto_increment=200 comment = '积分变动表';

drop table if exists article;
create table article
(
    id            bigint(20) not null auto_increment comment 'id',
    title         varchar(255) default '0' comment '用户id',
    `description` text(1234) default null comment '记录积分变动的详情 ? 购买商品 ? 答题',
    picUrl        varchar(255) default null comment '1 为 减少， 2 为增加',
    `url`         varchar(255) default null comment '一次积分变动记录的数量',
    is_del        char(1)      default '0' comment '删除标志（0代表存在 2代表删除）',
    create_at     datetime,
    `content`     text(1234) default null,
    `author`      text(1234) default null,
    `from`        varchar(255) default null,
    update_at     datetime,
    primary key (id)
)engine=innodb auto_increment=200 comment = '文章表';

drop table if exists examine_question;

create table examine_question
(
    id        bigint(20) not null auto_increment comment 'id',
    `type`    varchar(255) default '0' comment '题目类型',
    `content` text(1234) default null comment '内容',
    pic_url   varchar(255) default null comment '1 为 减少， 2 为增加',
    is_del    char(1)      default '0' comment '删除标志（0代表存在 2代表删除）',
    create_at datetime,
    update_at datetime,
    primary key (id)
)engine=innodb auto_increment=200 comment = '题库表';

drop table if exists `ticket`;

create table `ticket`
(
    id        bigint(20) not null auto_increment comment 'id',
    `name`    varchar(255) default '0' comment '兑换券名称',
    cover     varchar(255) default null comment 'cover',
    `good_id` bigint(20) default null comment '商品id',
    user_id   bigint(20) default null comment '用户id',
    is_del    char(1)      default '0' comment '删除标志（0代表存在 2代表删除）',
    is_used   char(1)      default '0' comment '删除标志（0代表存在 2代表删除）',
    num       float(20)    default 0.0 comment '消费积分数目',
    create_at datetime,
    update_at datetime,
    primary key (id)
)engine=innodb auto_increment=200 comment = '兑换表';
