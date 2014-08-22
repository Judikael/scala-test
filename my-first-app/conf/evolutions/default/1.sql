# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "ITEMS" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"USER_ID" BIGINT NOT NULL,"NAME" VARCHAR NOT NULL,"MODEL" VARCHAR NOT NULL);
create table "TAGS" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"NAME" VARCHAR NOT NULL);
create table "USERS" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"LOGIN" VARCHAR NOT NULL,"SECURITY" VARCHAR NOT NULL);
create table "VALUES" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"ITEM_ID" BIGINT NOT NULL,"TAG_ID" BIGINT NOT NULL,"VALUE" VARCHAR NOT NULL,"MODEL" VARCHAR NOT NULL);
alter table "ITEMS" add constraint "USERS" foreign key("USER_ID") references "USERS"("ID") on update NO ACTION on delete NO ACTION;
alter table "VALUES" add constraint "ITEMS" foreign key("ITEM_ID") references "ITEMS"("ID") on update NO ACTION on delete NO ACTION;
alter table "VALUES" add constraint "TAGS" foreign key("TAG_ID") references "TAGS"("ID") on update NO ACTION on delete NO ACTION;

# --- !Downs

alter table "VALUES" drop constraint "ITEMS";
alter table "VALUES" drop constraint "TAGS";
alter table "ITEMS" drop constraint "USERS";
drop table "VALUES";
drop table "USERS";
drop table "TAGS";
drop table "ITEMS";

