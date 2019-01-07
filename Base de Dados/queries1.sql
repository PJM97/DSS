

select * from cliente;

select cl.nome,cf.estado,cp.nome,cf.idConfiguracao from cliente as cl
	inner join configuracao as cf on username=cliente_username
    inner join configuracao_has_componente as chc on idConfiguracao=chc.configuracao_idConfiguracao
    inner join componente as cp on componente_nome=cp.nome
    
    where cf.idConfiguracao='4'
    
    
    union
    
    
select cl.nome,cf.estado,cp.nome,cf.idConfiguracao from cliente as cl
    inner join configuracao as cf on username=cliente_username
    inner join configuracao_has_pacote as chp on idConfiguracao=chp.configuracao_idConfiguracao
    inner join pacote on nomePacote=Pacote_nomePacote
    inner join pacote_has_componente as phc on nomePacote=phc.pacote_nomePacote
    inner join componente as cp on phc.componente_nome=cp.nome
    
    where cf.idConfiguracao='4'
    
    
;