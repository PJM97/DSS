
call deleteDB();

insert into Cliente
	(username,password,nome,email,visivel)
		values
			 ('pussydestroyer69','floresta12345','Legolas','cabelopantene@hotmail.com','1')
			,('marega11','marEgolo','Moussa Marega','mangalhaoescurinho@gmail.com','0')
;

insert into configuracao
	(idConfiguracao,preco,estado,visivel,cliente_username,sp)
		values
			('1','23.4','2','1','marega11','1')
;

insert into pacote
	(nomePacote,preco,visivel)
		values
			('Pacote do Aço','69.99','1')
;


insert into componente
	(nome,tipo,designacao,preco,visivel,stock)
		values
			('Motor XPTO cor-de-rosa','Motor','este motor é muito bom, mas é cor-de-rosa :( ','25.95','1','17')
;

select * from componente where visivel=True;

insert into componente 
	(nome,tipo,designacao,preco,visivel,stock)
		values
			('Motor XPTO cor-de-rosa','Motor','este motor é muito bom, mas é cor-de-rosa :( ','25.95','1','20')
		on duplicate key update
			nome=values(nome),
            tipo=values(tipo),
            designacao=values(designacao),
            preco=values(preco),
            visivel=values(visivel),
            stock=values(stock)
            ;


