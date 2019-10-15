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
   username             varchar(20) comment '����Աname',
   password             varchar(200) comment '����Ա����',
   roles                varchar(20) default 'general' comment '��ɫ   ��Ϊ���� super /  general',
   status               int default 1 comment '����Ա״̬   0������     1��ʹ����',
   primary key (id)
);

alter table admin comment '��̨����Ա��';

/*==============================================================*/
/* Table: appointment                                           */
/*==============================================================*/
create table appointment
(
   id                   varchar(50) not null,
   userName             varchar(50) comment 'ԤԼ���û�����Ĭ��΢����',
   category             char(10) comment 'ѡ��ķ������',
   createDate           date comment '����ʱ��',
   appointmentDate      date comment 'ԤԼ��ʱ��',
   phone                varchar(20) comment 'ԤԼ�û����ֻ���',
   shop                 char(10) comment 'ԤԼ�ĵ���',
   description          varchar(100) comment '��������ע',
   status               int default 1 comment 'ԤԼ��״̬��1�����ڷ���    2��������    3�������     4���ѹ���  ',
   primary key (id)
);

alter table appointment comment 'ԤԼ��';

/*==============================================================*/
/* Table: category                                              */
/*==============================================================*/
create table category
(
   id                   varchar(50) not null,
   name                 varchar(20) comment '���������',
   iconUrl              varchar(200) comment '�����ͼ��url',
   status               int default 1 comment '״̬��0������    1��ʹ����',
   primary key (id)
);

alter table category comment '�����
';

/*==============================================================*/
/* Table: picture                                               */
/*==============================================================*/
create table picture
(
   id                   varchar(50) not null,
   name                 varchar(200) comment 'ͼƬ��',
   category             char(10) comment 'ͼƬ��������',
   type                 char(10) comment 'ͼƬ����С�ࣨ�������ࣩ',
   description          varchar(500) comment 'ͼƬ����',
   tags                 char(10) comment '�����ͼƬtag',
   uploadDate           timestamp(0) comment '�ϴ�����',
   url                  varchar(600) comment 'ͼƬurl',
   status               int default 1 comment '״̬   0������    1��ʹ����',
   primary key (id)
);

alter table picture comment 'ͼƬ��';

/*==============================================================*/
/* Table: shop                                                  */
/*==============================================================*/
create table shop
(
   id                   varchar(50) not null,
   name                 varchar(50) comment '��������',
   address              varchar(150) comment '���̵�ַ',
   status               int default 1 comment '����״̬  0������     1��ʹ����',
   primary key (id)
);

alter table shop comment '���̱�';

/*==============================================================*/
/* Table: slideshow                                             */
/*==============================================================*/
create table slideshow
(
   id                   varchar(50),
   link                 varchar(100) comment '�ֲ�ͼָ�������',
   pictureUrl           varchar(100) comment '�ֲ�ͼurl',
   status               int comment '�ֲ�ͼ��״̬��0������      1��ʹ����'
);

alter table slideshow comment '�ֲ�ͼ';

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

alter table tag comment '��ǩ��
';

/*==============================================================*/
/* Table: type                                                  */
/*==============================================================*/
create table type
(
   id                   varchar(50) not null,
   name                 varchar(20) comment '�������������',
   parent               char(10) comment '����������Ĵ���',
   iconUrl              varchar(200) comment 'ͼ��url',
   status               int default 1 comment '���������״̬��0������    1��ʹ����',
   primary key (id)
);

alter table type comment '���������';

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

