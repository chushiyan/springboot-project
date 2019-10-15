/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/8/5 16:25:59                            */
/*==============================================================*/


drop table if exists admin;

drop table if exists appointment;

drop table if exists category;

drop table if exists picture;

drop table if exists shop;

drop table if exists slideshow;

drop table if exists tag;

drop table if exists type;

/*==============================================================*/
/* Table: admin                                                 */
/*==============================================================*/
create table admin
(
   id                   varchar(50) not null,
   username             varchar(20) comment '管理员name',
   password             varchar(200) comment '管理员密码',
   roles                varchar(20) default 'general' comment '角色   分为两种 super /  general',
   status               int default 1 comment '管理员状态   0：弃用     1：使用中',
   primary key (id)
);

alter table admin comment '后台管理员表';

/*==============================================================*/
/* Table: appointment                                           */
/*==============================================================*/
create table appointment
(
   id                   varchar(50) not null,
   userName             varchar(50) comment '预约的用户名，默认微信名',
   category             char(10) comment '选择的服务大类',
   createDate           date comment '创建时间',
   appointmentDate      date comment '预约的时间',
   phone                varchar(20) comment '预约用户的手机号',
   shop                 char(10) comment '预约的店铺',
   description          varchar(100) comment '描述、备注',
   status               int default 1 comment '预约的状态，1：正在服务    2：待处理    3：已完成     4：已过期  ',
   primary key (id)
);

alter table appointment comment '预约表';

/*==============================================================*/
/* Table: category                                              */
/*==============================================================*/
create table category
(
   id                   varchar(50) not null,
   name                 varchar(20) comment '大类的名称',
   iconUrl              varchar(200) comment '大类的图标url',
   status               int default 1 comment '状态，0：弃用    1：使用中',
   primary key (id)
);

alter table category comment '大类表
';

/*==============================================================*/
/* Table: picture                                               */
/*==============================================================*/
create table picture
(
   id                   varchar(50) not null,
   name                 varchar(200) comment '图片名',
   category             char(10) comment '图片所属大类',
   type                 char(10) comment '图片所属小类（二级分类）',
   description          varchar(500) comment '图片描述',
   tags                 char(10) comment '外键，图片tag',
   uploadDate           timestamp(0) comment '上传日期',
   url                  varchar(600) comment '图片url',
   status               int default 1 comment '状态   0：弃用    1：使用中',
   primary key (id)
);

alter table picture comment '图片表';

/*==============================================================*/
/* Table: shop                                                  */
/*==============================================================*/
create table shop
(
   id                   varchar(50) not null,
   name                 varchar(50) comment '店铺名称',
   address              varchar(150) comment '店铺地址',
   status               int default 1 comment '店铺状态  0：弃用     1：使用中',
   primary key (id)
);

alter table shop comment '店铺表';

/*==============================================================*/
/* Table: slideshow                                             */
/*==============================================================*/
create table slideshow
(
   id                   varchar(50),
   link                 varchar(100) comment '轮播图指向的链接',
   pictureUrl           varchar(100) comment '轮播图url',
   status               int comment '轮播图的状态，0：弃用      1：使用中'
);

alter table slideshow comment '轮播图';

/*==============================================================*/
/* Table: tag                                                   */
/*==============================================================*/
create table tag
(
   id                   varchar(50) not null,
   name                 varchar(100),
   status               int default 1,
   primary key (id)
);

alter table tag comment '标签表
';

/*==============================================================*/
/* Table: type                                                  */
/*==============================================================*/
create table type
(
   id                   varchar(50) not null,
   name                 varchar(20) comment '二级分类的名称',
   parent               char(10) comment '外键，所属的大类',
   iconUrl              varchar(200) comment '图标url',
   status               int default 1 comment '二级分类的状态，0：弃用    1：使用中',
   primary key (id)
);

alter table type comment '二级分类表';

alter table appointment add constraint FK_Reference_1 foreign key (shop)
      references shop (id) on delete restrict on update restrict;

alter table appointment add constraint FK_Reference_6 foreign key (category)
      references category (id) on delete restrict on update restrict;

alter table picture add constraint FK_Reference_2 foreign key (tags)
      references tag (id) on delete restrict on update restrict;

alter table picture add constraint FK_Reference_4 foreign key (category)
      references category (id) on delete restrict on update restrict;

alter table picture add constraint FK_Reference_5 foreign key (type)
      references type (id) on delete restrict on update restrict;

alter table type add constraint FK_Reference_3 foreign key (parent)
      references category (id) on delete restrict on update restrict;

