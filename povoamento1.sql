
call deleteDB();

insert into Cliente
	(idCliente,nomeCliente,email,passwordC)
		values
			('1','Legolas','cabelopantene@hotmail.com','floresta12345')
;

-- select * from Cliente;

insert into configuracao
	(idConfiguracao,estado,precoConfig)
		values
			('1','1','23.4')
;






            