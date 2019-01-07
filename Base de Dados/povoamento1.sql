
call deleteDB();

insert into administrador
	(username,password)
		values
			('admin_001','pwA_001')
           ,('admin_002','pwA_002')
;


insert into Cliente
	(username,password,nome,email,visivel)
		values
			 ('legoman1','floresta12345','Legolas','cabelopantene@hotmail.com','1')
			,('marega11','marEgolo','Moussa Marega','forcaporto@gmail.com','1')
            ,('zemaria86','zm86','José Maria','cervejolascervejolas@gmail.com','1')
;


insert into funcionarioStand
	(username,password,nome,email,visivel)
		values
			('funcS_001','pwFS_001','Zé','func001@eStand.com','1')
           ,('funcS_002','pwFS_002','Rita','func002@eStand.com','1') 
;


insert into funcionarioFabrica
	(username,password,nome,email,visivel)
		values
			('funcF_001','pwFF_001','Zé','func001@eFabrica.com','1')
           ,('funcF_002','pwFF_002','Rita','func002@eFabrica.com','1') 
;


insert into configuracao
	(idConfiguracao,preco,estado,visivel,cliente_username,sp)
		values
			('1','23.4','2','1','marega11','2')
           ,('2','45.7','3','1','legoman1','1')
           ,('3','28.39','1','1','zemaria86','0')
           ,('4','38.28','2','1','marega11','3')
;


insert into pacote
	(nomePacote,preco,visivel)
		values
			('Pacote do Aço','69.99','1')
           ,('Pacote Scooby-doo Papa','83.36','1')
           ,('Pacote Especial','39.29','1')
           ,('Pacote Sobre Rodas','82.38','1')
;


insert into componente
	(nome,tipo,designacao,preco,visivel,stock)
		values
			('Motor XPTO cor-de-rosa','Motor','este motor é muito bom, mas é cor-de-rosa :( ','25.95','1','17')
           ,('Motor XPTO azul e branco','Motor','este motor é do melhor que há!','42.92','1','23')
           ,('Motor Polivalente','Motor','dá com tudo! ;)','37.94','1','38')
           ,('Pneus Lelo','Pneu','versão mais barata dos Pneus Lino','22.73','1','48')
           ,('Pneus Continente','Pneu','quase tão bons como os do PingoDoce','37.48','1','32')
           ,('Pneus PingoDoce','Pneu','...PingoDoce, venha cá!','38.26','1','57')
           ,('Jantes Leves','Jante','jantes de liga leve','38.94','1','96')
           ,('Jantes Pesadas','Jante','jantes de liga não tão leve...','39.50','1','25')
           ,('Bateria Duracell','Bateria','dura muito tempo','28.84','1','48')
           ,('Bateria Feira Nova','Bateria','inserir descrição aqui...','42.12','1','38')
           ,('Vidro Transparente','Vidro','dá para ver através dele','34.12','1','83')
           ,('Vidro do Bom','Vidro','não há melhor!','38.49','1','39')
           
;



insert into configuracao_has_pacote
	(configuracao_idconfiguracao,pacote_nomepacote)
		values
			('1','Pacote do Aço')
           ,('1','Pacote Scooby-doo Papa')
           ,('2','Pacote Especial')
           ,('2','Pacote do Aço')
           ,('4','Pacote Sobre Rodas')
           
;


insert into configuracao_has_componente
	(configuracao_idconfiguracao,componente_nome)
		values
			('2','Vidro do Bom')
           ,('3','Motor Polivalente')
           ,('3','Pneus PingoDoce')
           ,('3','Jantes Leves')
           ,('3','Vidro do Bom')
           ,('3','Bateria Duracell')
           ,('4','Bateria Feira Nova')
           ,('4','Vidro Transparente')
           
;


insert into pacote_has_componente
	(pacote_nomepacote,componente_nome)
		values
			('Pacote do Aço','Pneus Lelo')
           ,('Pacote do Aço','Jantes Pesadas')
           ,('Pacote Especial','Motor XPTO azul e branco')
           ,('Pacote Especial','Bateria Duracell')
           ,('Pacote Scooby-doo Papa','Motor XPTO cor-de-rosa')
           ,('Pacote Scooby-doo Papa','Bateria Feira Nova')
           ,('Pacote Scooby-doo Papa','Vidro Transparente')
           ,('Pacote Sobre Rodas','Motor Polivalente')
           ,('Pacote Sobre Rodas','Pneus Continente')
           ,('Pacote Sobre Rodas','Jantes Pesadas')
           
;          


insert into obrigatorio
	(componente_nome,componente_nome1)
		values
			('Pneus Continente','Jantes Pesadas')
           
;


insert into incompativel
	(componente_nome,componente_nome1)
		values
			('Motor XPTO cor-de-rosa','Bateria Duracell')
           ,('Pneus Lelo','Jantes Leves')
           ,('Pneus PingoDoce','Jantes Pesadas')
           
;






           


-- select * from componente where visivel=True;
/*

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

*/









