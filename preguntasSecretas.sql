insert into pregunta(id,pregunta) values ('1','nombre de la primera mascota');
insert into pregunta(id,pregunta) values ('2','apellido de soltera de la madre'); 


select * from administrador;
select * from pregunta;

insert into administrador(id,pregunta) values ('2','apellido de soltera de la madre');

insert into administrador(idAdmin,email,contrasena,nombre,estado,id) values ('4','sistemas@gmail.com','1234','sistemas','activo','2');

insert into administrador(email,contrasena,nombre,estado,id) values ('sistemas2@gmail.com','1234','sistemas2','activo','2');


/*MODIFICACION DE AUTOINCREMENTO
SET FOREIGN_KEY_CHECKS = 0;
ALTER TABLE `administrador`
MODIFY COLUMN `idAdmin`  int(11) NOT NULL AUTO_INCREMENT;
SET FOREIGN_KEY_CHECKS = 1;
*/

