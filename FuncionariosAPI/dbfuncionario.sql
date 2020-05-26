
create database dbFuncionarios;

use dbFuncionarios;

CREATE TABLE tbFuncionarios (
  id int(11) NOT NULL AUTO_INCREMENT primary key,
  cargo varchar(50) NOT NULL,
  atividades varchar(200) NOT NULL,
  salario decimal(5,2) NOT NULL,
  foto varchar(30) NOT NULL);



INSERT INTO tbFuncionarios (cargo, atividades, salario. foto) 
VALUES ('Gerente','Gerencia um devido setor',3500.00,'gerente.png');
INSERT INTO tbFuncionarios (cargo, atividades, salario. foto) 
VALUES ('Supervisor','Supervisionar os colaboradores',2000.00,'supervisor.png');


