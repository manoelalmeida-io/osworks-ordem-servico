/**
 * Author:  manoel
 * Created: 19 de jun de 2020
 */

create table cliente (
  id bigint not null auto_increment,
  nome varchar(60) not null,
  email varchar(255) not null,
  telefone varchar(20) not null,
  primary key (id)
);