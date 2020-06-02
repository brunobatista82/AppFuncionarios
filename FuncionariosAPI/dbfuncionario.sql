drop database dbFuncionario;
create database dbFuncionario;

use dbFuncionario;

CREATE TABLE tbFuncionarios (
  id int(11) NOT NULL AUTO_INCREMENT primary key,
  cargo varchar(50) NOT NULL,
  atividades varchar(200) NOT NULL,
  salario decimal(5,2) NOT NULL);



INSERT INTO tbFuncionarios (cargo, atividades, salario) 
VALUES ('Gerente','Gerencia um devido setor','3500.00');
INSERT INTO tbFuncionarios (cargo, atividades, salario) 
VALUES ('Supervisor','Supervisionar os colaboradores','2000.00');


