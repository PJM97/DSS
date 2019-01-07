package ConfiguraFacil.Business;

import ConfiguraFacil.Business.Exceptions.*;
import ConfiguraFacil.Data.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Set;

public class ConfiguraFacil extends Observable implements Serializable{
   
	private String idUtilizadorAtual; //id do utilizador registado no sistema
        private int count=0;//contador para a lista de encomendas da fabrica
        private int nConfiguracao=0;//numero de configuracao
	private UtilizadorDAO utilizadores; //<String id(username),Utilizador u
	private ComponenteDAO componentes; //<String nomeComponente,Componente c>
	private PacoteDAO pacotes; //<String nomePacote,Pacote p>
    private List<Configuracao> queue;

	public ConfiguraFacil(){
		super();
		this.idUtilizadorAtual = "ND";
                this.count = 0;
		this.utilizadores = new UtilizadorDAO();
		this.componentes = new ComponenteDAO();
		this.pacotes = new PacoteDAO();
		this.queue = new LinkedList<>();
                
	}
        
    public ConfiguraFacil(ConfiguraFacil cf){
	    super();
	    this.idUtilizadorAtual = cf.getIdUtilizadorAtual();
            this.count = cf.getCount();
	    this.utilizadores = cf.getUtilizadores();
        this.componentes = cf.getComponentes();
		this.pacotes = cf.getPacotes();
		this.queue = cf.getQueue();
    }

    //Gets e Sets
    private String getIdUtilizadorAtual() {
    	return this.idUtilizadorAtual; 
    }

    public void setIdAtual(String id){
    	this.idUtilizadorAtual = id;
    }
    
    public int getCount(){
        return this.count;
    }
    
    public void setCount(int c){
        this.count = c;
    }
    
    public UtilizadorDAO getUtilizadores(){
    	return this.utilizadores;
    }

    public Utilizador getUtilizador(String key) throws UtilizadorInexistenteException{
        Utilizador u = this.utilizadores.get(key);
        if (u==null)
            throw new UtilizadorInexistenteException("Utilizador inexistente - utilizador indicado não existe no sistema");
        return u;
    }

    public Map<String,Utilizador> getUtilizadoresM(){
    	Map<String,Utilizador> aux = new HashMap<>();
        Set<Map.Entry<String, Utilizador>> us = this.utilizadores.entrySet();

    	for(Map.Entry<String,Utilizador> u: us){
            aux.put(u.getKey(), u.getValue().clone());
    	}
    	return aux;
    }

    public void setUtilizadores(Map<String,Utilizador> ut){
    	this.utilizadores.clear();

    	for(Map.Entry<String,Utilizador> u: ut.entrySet()){
    		this.utilizadores.put(u.getKey(),u.getValue().clone());
    	}
        
        this.setChanged();
        this.notifyObservers();
    }

    private ComponenteDAO getComponentes() {
        return this.componentes;
    }

    public Componente getComponente(String key) throws ComponenteInexistenteException{
        Componente c = this.componentes.get(key);
        if (c==null)
            throw new ComponenteInexistenteException("Componente inexistente - Componente indicada não existe no sistema");
        return c;
    }

    private Map<String,Componente> getComponentesM(){
    	Map<String,Componente> aux = new HashMap<>();
        Set<Map.Entry<String,Componente>> cs = this.componentes.entrySet();

    	for(Map.Entry<String,Componente> m : cs){
            aux.put(m.getKey(), m.getValue().clone());
    	}
    	return aux;
    }

    public void setComponentes(Map<String,Componente> mc){
    	this.componentes.clear();

    	for(Map.Entry<String,Componente> m: mc.entrySet()){
    		this.componentes.put(m.getKey(),m.getValue().clone());
    	}
        
        this.setChanged();
        this.notifyObservers();
    }

    private PacoteDAO getPacotes() {
    	return this.pacotes;
    }

    public Pacote getPacote(String key) throws PacoteInexistenteException{
        Pacote p = this.pacotes.get(key);
        if (p==null)
            throw new PacoteInexistenteException("Pacote inexistente - Pacote indicado não existe no sistema");
        return p;
    }

    private Map<String,Pacote> getPacotesM(){
    	Map<String,Pacote> aux = new HashMap<>();
        Set<Map.Entry<String,Pacote>> ps = this.pacotes.entrySet();

    	for(Map.Entry<String,Pacote> m : ps){
            aux.put(m.getKey(), m.getValue().clone());
    	}
    	return aux;
    }

    public void setPacotes(Map<String,Pacote> mp){
    	this.pacotes.clear();

    	for(Map.Entry<String,Pacote> m: mp.entrySet()){
    		this.pacotes.put(m.getKey(),m.getValue().clone());
    	}
        
        this.setChanged();
        this.notifyObservers();
    }

    public List<Configuracao> getQueue(){
        LinkedList<Configuracao> aux = new LinkedList<>();
        for(Configuracao c : this.queue)
            aux.add(c);
        return aux;
    }

    public void setQueue(List<Configuracao> l){
        this.queue.clear();
        for(Configuracao c : l)
            this.queue.add(c);
    }

    //Métodos
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("ConfiguraFacil:");
        sb.append("Utilizador atual: ").append(this.idUtilizadorAtual).append("\n");
        sb.append("Utilizadores Registados:\n").append(this.utilizadores.entrySet().toString()).append("\n");
        sb.append("Componentes Registados:\n").append(this.componentes.entrySet().toString()).append("\n");
        sb.append("Pacotes Registados:\n").append(this.pacotes.entrySet().toString()).append("\n");
        sb.append("Lista de encomendas da fábrica:\n").append(this.queue.toString()).append("\n");

        return sb.toString();
    }
    
    public ConfiguraFacil clone(){
        return new ConfiguraFacil(this);
    }
    
    public boolean equals(Object o){
        if (this==o) return true;
        if((o==null) || (this.getClass() != o.getClass())) return false;
        
        ConfiguraFacil c = (ConfiguraFacil) o;
        return (this.idUtilizadorAtual.equals(c.getIdUtilizadorAtual()) && this.count==c.getCount() 
        	&& this.utilizadores.equals(c.getUtilizadores()) && this.componentes.equals(c.getComponentes())
        	&& this.pacotes.equals(c.getPacotes()) && this.queue.equals(c.getQueue()));
    }

    public void registaCliente(String id, String pass, String nome, String email) throws UtilizadorExistenteException, AcaoInvalidaException{

    	if(this.idUtilizadorAtual==null || this.utilizadores.get(this.idUtilizadorAtual) instanceof Administrador){
			if(this.utilizadores.containsKey(id))
    			throw new UtilizadorExistenteException("Utilizador existente - já existe um utilizador registado com o id indicado");

    		Cliente c = new Cliente(id,pass,nome,email);
    		this.utilizadores.put(id,c);
    	}
        else{
            throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");
        }
    }

    public void registaFuncionarioStand(String id, String pass, String nome, String email) throws UtilizadorExistenteException, AcaoInvalidaException{

    	if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof Administrador))
    		throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

    	if(this.utilizadores.containsKey(id))
    		throw new UtilizadorExistenteException("Utilizador existente - já existe um utilizador registado com o id indicado");

    	FuncionarioStand c = new FuncionarioStand(id,pass,nome,email);
    	this.utilizadores.put(id,c);
    }

    public void registaFuncionarioFabrica(String id, String pass, String nome, String email) throws UtilizadorExistenteException, AcaoInvalidaException{

    	if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof Administrador))
    		throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

    	if(this.utilizadores.containsKey(id))
    		throw new UtilizadorExistenteException("Utilizador existente - já existe um utilizador registado com o id indicado");

    	FuncionarioFabrica c = new FuncionarioFabrica(id,pass,nome,email);
    	this.utilizadores.put(id,c);
    }

    public void removeUtilizador(String id) throws AcaoInvalidaException, UtilizadorExistenteException{

        if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof Administrador))
            throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

        if(this.utilizadores.containsKey(id))
            throw new UtilizadorExistenteException("Utilizador existente - já existe um utilizador registado com o id indicado");   

        this.utilizadores.remove(id);    
    }

    //utilizador autentica-se no sistema
    public void autenticar(String id, String pass) throws UtilizadorInexistenteException,PasswordIncorretaException{
        
        if (!this.utilizadores.containsKey(id)) throw new UtilizadorInexistenteException("Utilizador Inexistente - utilizador não existe no sistema.");
        
        Utilizador u = this.utilizadores.get(id);
        if (!u.getPw().equals(pass)) throw new PasswordIncorretaException("Password Incorreta - password inserida incorreta.");
        
        this.idUtilizadorAtual = id;
    }

    //utilizador desautentica-se no sistema
    public void desautenticar(){
        this.idUtilizadorAtual = null;
    }

    //utilizador altera a password
    public void alterarPassword(String antigaPass, String novaPass) throws PasswordIncorretaException{
        Utilizador u = this.utilizadores.get(this.idUtilizadorAtual);
        
        if(!u.getPw().equals(antigaPass)) throw new PasswordIncorretaException("Password Incorreta - password atual inserida incorreta.");
        
        u.setPw(novaPass);
        
        this.utilizadores.put(this.idUtilizadorAtual,u);
    }

    //adiciona um componente ao stand
    public void adicionaComponenteStand(String nome, String tipo, String designacao, Double preco, int stock,
        List<String> inc, List<String> obr) throws AcaoInvalidaException, ComponenteExistenteException{

        if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof FuncionarioStand))
            throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");
    
        if(this.componentes.containsKey(nome))
            throw new ComponenteExistenteException("Componente existente - o componente com o nome indicado já está registado no sistema");

        Componente c = new Componente(nome,tipo,designacao,preco,stock,inc,obr);
        
        this.componentes.put(c.getNome(),c);
    }

    //remove um componente do stand
    public void removeComponenteStand(String nomeComponente) throws AcaoInvalidaException, ComponenteInexistenteException{

        if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof FuncionarioStand))
            throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");
    
        if(!this.componentes.containsKey(nomeComponente))
            throw new ComponenteInexistenteException("Componente Inexistente - o componente indicado não se encontra registado no sistema");
        
        this.componentes.remove(nomeComponente);
    }

    public void adicionaPacoteStand(String nome, double preco, List<String> comp) throws AcaoInvalidaException, PacoteExistenteException, ComponenteInexistenteException, ComponenteIncompativelException{

        if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof FuncionarioStand))
            throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");
    
        if(this.pacotes.containsKey(nome))
            throw new PacoteExistenteException("Pacote existente - o pacote com o nome indicado já está registado no sistema");
        
        for(String nomeComp : comp){
            if(!this.componentes.containsKey(nomeComp))
                throw new ComponenteInexistenteException("Componente Inexistente - O componente " + nomeComp + " contido no pacote não está registado no sistema");
            Componente c = this.componentes.get(nomeComp);
            for(String compIncomp : c.getIncomp())
                if(comp.contains(compIncomp))
                    throw new ComponenteIncompativelException("Componente Incompativel - O pacote inserido contem componentes incompatíveis");
        }
        
        Pacote p = new Pacote(nome,preco,comp);
        this.pacotes.put(p.getNome(),p);   
    }

    public void removePacoteStand(String nomePacote) throws AcaoInvalidaException, PacoteInexistenteException{

        if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof FuncionarioStand))
            throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");
    
        if(!this.pacotes.containsKey(nomePacote))
            throw new PacoteInexistenteException("Pacote Inexistente - o pacote indicado não se encontra registado no sistema");
        
        this.pacotes.remove(nomePacote);
    }
    
    //Funcionario do stand altera preco de um componente
    public void alteraPrecoComponente(String nomeComponente, Double novoPreco) throws AcaoInvalidaException, FuncionarioInexistenteException, ComponenteInexistenteException{
    	if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof FuncionarioStand))
        	throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");
   
    	if(!this.componentes.containsKey(nomeComponente))
    		throw new ComponenteInexistenteException("Componente Inexistente - o componente indicado não existe no sistema");

    	Componente c = this.componentes.get(nomeComponente);
    	c.setPreco(novoPreco);
    	this.componentes.put(nomeComponente,c);
    }

    //Funcionario do stand altera preco de um pacote(nomepacote,preco)
    public void alteraPrecoPacote(String nomePacote, Double novoPreco) throws AcaoInvalidaException, FuncionarioInexistenteException, PacoteInexistenteException{
    	
    	if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof FuncionarioStand))
    		throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");
    
    	if(!this.pacotes.containsKey(nomePacote))
    		throw new PacoteInexistenteException("Pacote Inexistente - o pacote indicado não existe no sistema");

    	Pacote p = this.pacotes.get(nomePacote);
    	p.setPreco(novoPreco);
    	this.pacotes.put(nomePacote,p);
    }

    //funcionario da fabrica atualiza stock(nome do componente,quantidade adicionada/retirada do stock)
    public void atualizarStock(String nomeC, Integer valor) throws AcaoInvalidaException, FabricaInexistenteException, ComponenteInexistenteException, FuncionarioInexistenteException, QuantidadeInseridaInvalidaException{
    	
    	if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof FuncionarioFabrica))
    	    throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

    	if(!this.componentes.containsKey(nomeC))
    		throw new ComponenteInexistenteException("Componente Inexistente - o componente indicado não existe no sistema");
    	
        Componente c = this.componentes.get(nomeC);
       if((c.getStock() + valor)<0)
           throw new QuantidadeInseridaInvalidaException("Quantidade Inserida Invalida - a quantidade que pretende retirar é superior à quantidade existente");
           
        c.setStock(c.getStock() + valor);
		this.componentes.put(nomeC,c);
    }
    
    public void criaConfiguracao() throws AcaoInvalidaException{
        
        if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof Cliente))
    		throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");
       
       nConfiguracao+=1;
       Configuracao c = new Configuracao(nConfiguracao,this.idUtilizadorAtual);
       
       Cliente cl = (Cliente) this.utilizadores.get(this.idUtilizadorAtual);
       
       cl.addConfiguracao(c);
       this.utilizadores.put(this.idUtilizadorAtual,cl);    
    }
    
    //Cliente consulta as suas configuracoes
    public Map<Integer,Configuracao> consultaConfiguracoes() throws AcaoInvalidaException{
    	
    	if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof Cliente))
    		throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

    	Cliente c = (Cliente) this.utilizadores.get(this.idUtilizadorAtual);

    	return c.getConfiguracoes();
    }

    //Funcionario do stand consulta dados de um cliente
    public Cliente consultaDadosCliente(String nome) throws AcaoInvalidaException{

    	if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof FuncionarioStand))
    		throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

    	for(Utilizador u : this.utilizadores.values())
    		if(u instanceof Cliente){
    			Cliente c = (Cliente) u;
    			if(c.getNome().equals(nome))
    				return c;
    		}
        return null;
    }

    //Funcionario da fábrica consulta encomendas
    public List<Configuracao> consultaEncomendas() throws AcaoInvalidaException, FabricaInexistenteException, FuncionarioInexistenteException{

    	if(this.utilizadores.get(this.idUtilizadorAtual) instanceof FuncionarioFabrica)
    		throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");
                
    	return this.getQueue();
    }

    //Cliente consulta componentes disponíveis
    public Map<String,Componente> consultaComponentes() throws AcaoInvalidaException{
    	if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof Cliente))
    		throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

    	return this.getComponentes();
    }

    //Cliente consulta pacotes disponíveis
    public Map<String,Pacote> consultaPacotes() throws AcaoInvalidaException{
    	if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof Cliente))
    		throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

    	return this.getPacotes();
    }

    //funcionário consulta o stock de um componente da fábrica, se o nome for null mostra o stock completo
    public Map<String,Integer> consultaStock(String nome) throws AcaoInvalidaException, FabricaInexistenteException, FuncionarioInexistenteException, ComponenteInexistenteException{

    	if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof FuncionarioFabrica))
    		throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

        Map<String,Integer> aux = new HashMap<>();

        if(nome!=null){
            if(!this.componentes.containsKey(nome))
                throw new ComponenteInexistenteException("Componente Inexistente - O componente selecionado não está registado no sistema");
            
            Componente c = this.componentes.get(nome);
            aux.put(nome,c.getStock());
        }
        else{
            for(Componente c : this.componentes.values())
                aux.put(c.getNome(),c.getStock());
    	}
        return aux;
    }

    //Cliente insere componente na configuração(Nome do Componente,Id da configuração)
    public void insereComponenteConfig(String c,String ci) throws AcaoInvalidaException, ComponenteIncompativelException, ComponenteObrigatorioException, TipoDeComponenteException, ConfiguracaoInexistenteException, ComponenteInexistenteException{
    	
    	if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof Cliente))
    		throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

        Cliente cl = (Cliente) utilizadores.get(this.idUtilizadorAtual);

        if(!(this.componentes.containsKey(c)))
            throw new ComponenteInexistenteException("Componente Inexistente - O componente selecionado não está registado no sistema");

        Componente co = this.componentes.get(c);

    	if(!(cl.getConfiguracoes().containsKey(ci)))
    		throw new ConfiguracaoInexistenteException("Configuração inexistente - A configuração selecionada não pertence ao utilizador atual");

        Configuracao cf = cl.getConfiguracoes().get(ci);

        //if(existe algum componente do tipo do componente escolhido já inserido na configuração)
        if(!cf.getComponentes().containsKey(co.getTipo())){ 
            for(String s : cf.getComponentes().values())//s = id do componente que pertence à cfg
                if(this.componentes.containsKey(s)){
                    Componente cs = this.componentes.get(s);
        //se a lista dos incompativeis de um componente da config tem o que queremos inserir ou o inverso 
                    if(cs.getIncomp().contains(c) || co.getIncomp().contains(s))
                        throw new ComponenteIncompativelException("O componente inserido é incompativel com o componente "
                            + s);
                }

            for(String s : co.getObriga())          
                if(!cf.getComponentes().containsValue(s))
                    throw new ComponenteObrigatorioException("O componente inserido obriga a inserção do componente "+
                        s);
        }
        else{
            throw new TipoDeComponenteException("Já foi inserido um componente do tipo: " + co.getTipo().toString());
        }

        cf.setPreco(cf.getPreco() + co.getPreco());
        cf.addComponente(co);
        cl.addConfiguracao(cf);
        this.utilizadores.put(this.idUtilizadorAtual,cl);
    }

    //Cliente remove componente da configuração(nome componente,id configuracao)
    public void removeComponenteConfig(String c, String ci) throws AcaoInvalidaException, ComponenteObrigatorioException, ComponenteInexistenteException, ComponenteNaoPodeSerRemovidoException, ConfiguracaoInexistenteException, PacoteInexistenteException{

    	if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof Cliente))
            throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

        Cliente cl = (Cliente) utilizadores.get(this.idUtilizadorAtual);

        if(!(this.componentes.containsKey(c)))
            throw new ComponenteInexistenteException("Componente Inexistente - O componente selecionado não está registado no sistema");

        Componente co = this.componentes.get(c);

        if(!(cl.getConfiguracoes().containsKey(ci)))
            throw new ConfiguracaoInexistenteException("Configuração inexistente - A configuração selecionada não pertence ao utilizador atual");

        Configuracao cf = cl.getConfiguracoes().get(ci);
    	
        if(cf.getComponentes().containsValue(c)){ //só avança se o componente estiver no map dos componentes da config
            for(String sp : cf.getPacotes()) //verifica se o componente está presente em algum pacote da config
                if(this.pacotes.containsKey(sp)){
                    Pacote p = this.pacotes.get(sp);
                    if(p.getComponentes().contains(c))
                        throw new ComponenteNaoPodeSerRemovidoException("O componente " + co.getDesignacao() 
                    + " não pode ser removido porque está contido no pacote " + p.getNome());
                }
                else{
                    throw new PacoteInexistenteException("O pacote " + sp + " inserido na configuração não está disponível no sistema");
                }

            for(String sc : cf.getComponentes().values())//verifica se não há nenhum componente que obrigue a presença do componente que pretende remover
                if(this.componentes.containsKey(sc)){
                    Componente csc = this.componentes.get(sc);
                    if(csc.getObriga().contains(c))
                        throw new ComponenteObrigatorioException("Este componente não pode ser retirado porque é um componente obrigatório da componente " 
                            + csc.getDesignacao());
                }
                else{
                    throw new ComponenteInexistenteException("O componente " + sc + " inserido na configuração não está disponível no sistema");
                }
        }
        else{
            throw new ComponenteInexistenteException("O componente inserido não existe na configuração");
        }
        cf.setPreco(cf.getPreco() - co.getPreco());
        cf.remComponente(co);
        cl.addConfiguracao(cf);
        this.utilizadores.put(this.idUtilizadorAtual,cl);
    }
    //insere o pacote com o nome pi na configuracao ci
    public void inserePacoteConfig(String pi,String ci) throws AcaoInvalidaException, ComponenteIncompativelException, ComponenteObrigatorioException, TipoDeComponenteException, ConfiguracaoInexistenteException, ComponenteExistenteException, PacoteInexistenteException, ComponenteInexistenteException{
        
        if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof Cliente))
            throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

        Cliente cl = (Cliente) utilizadores.get(this.idUtilizadorAtual);

        if(!(this.pacotes.containsKey(pi)))
            throw new PacoteInexistenteException("Pacote Inexistente - O pacote selecionado não está registado no sistema");

        Pacote p = this.pacotes.get(pi);

        if(!(cl.getConfiguracoes().containsKey(ci)))
            throw new ConfiguracaoInexistenteException("Configuração inexistente - A configuração selecionada não pertence ao utilizador atual");

        Configuracao cf = cl.getConfiguracoes().get(ci);

        for(String sc : p.getComponentes())
            if(this.componentes.containsKey(sc)){
                Componente c = this.componentes.get(sc);
                if(cf.getComponentes().containsKey(c.getTipo())) //Verifica se existe algum componente do mesmo tipo
                    throw new TipoDeComponenteException("TipoDeComponenteException - Já foi inserido um componente do tipo: " + c.getTipo());
                
                for(String scp : cf.getComponentes().values())
                    if(this.componentes.containsKey(scp)){
                        Componente cp = this.componentes.get(scp);
                        if(cp.getIncomp().contains(sc) || c.getIncomp().contains(scp)) //Verifica componentes incompativeis
                            throw new ComponenteIncompativelException("O componente " + c.getDesignacao() + " do pacote "
                            + p.getNome() + " é incompatível com o componente " + cp.getDesignacao() + " da configuração");
                    }
                    else{
                        throw new ComponenteInexistenteException("O componente " + scp + " inserido na configuração não está disponível no sistema");
                    }
            }
            else{
                throw new ComponenteInexistenteException("O componente " + sc + " inserido no pacote " + p.getNome() + " não está disponível no sistema");
            }
            
        for(String sc : p.getComponentes()){          
            Componente c = this.componentes.get(sc);
            for(String co : c.getObriga())
                if(!cf.getComponentes().containsValue(co))
                    throw new ComponenteObrigatorioException("O componente " + c.getDesignacao() + " do pacote "
                        + p.getNome() + " obriga a colocação do componente com o id " + co + " na configuração");
        }

        cf.setPreco(cf.getPreco() + p.getPreco());
        cf.addPacote(p);
        for(String s : p.getComponentes())
            cf.addComponente(this.componentes.get(s));
        cl.addConfiguracao(cf);
        this.utilizadores.put(this.idUtilizadorAtual,cl);
        }

    //remove o pacote com o nome pi da configuracao com o id ci
    public void removePacoteConfig(String pi, String ci) throws AcaoInvalidaException, ComponenteObrigatorioException, ComponenteInexistenteException, ComponenteNaoPodeSerRemovidoException, ConfiguracaoInexistenteException, PacoteInexistenteNaConfiguracaoException, PacoteInexistenteException{

        if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof Cliente))
            throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

        Cliente cl = (Cliente) utilizadores.get(this.idUtilizadorAtual);

        if(!(this.pacotes.containsKey(pi)))
            throw new PacoteInexistenteException("Pacote Inexistente - O pacote selecionado não está registado no sistema");

        Pacote p = this.pacotes.get(pi);

        if(!(cl.getConfiguracoes().containsKey(ci)))
            throw new ConfiguracaoInexistenteException("Configuração inexistente - A configuração selecionada não pertence ao utilizador atual");

        Configuracao cf = cl.getConfiguracoes().get(ci);

        for(String sc : p.getComponentes())
            for(String sco : cf.getComponentes().values())
                if(this.componentes.containsKey(sco)){
                    Componente co = this.componentes.get(sco);
                    if(co.getObriga().contains(sc))
                        throw new ComponenteObrigatorioException("Componente Obrigatório -  O componente " + co.getDesignacao() 
                            + " obriga a existência do componente " + sc + " do pacote " + p.getNome() + " na configuração");           
                }
                else{
                    throw new ComponenteInexistenteException("O componente com o id " + sco + " inserido na configuração não está disponível no sistema");
                }

        cf.setPreco(cf.getPreco() - p.getPreco());
        cf.remPacote(p);
        for(String c : p.getComponentes())
            cf.remComponente(this.componentes.get(c));
    	cl.addConfiguracao(cf);
    	this.utilizadores.put(this.idUtilizadorAtual,cl);
    }

    //faz a encomenda de uma configuração(envia para a lista de espera da fábrica de montagem)
    public boolean encomendaConfig(Configuracao c) throws AcaoInvalidaException, ConfiguracaoInexistenteException, FabricaInexistenteException{

    	if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof Cliente))
    		throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

    	Cliente cl = (Cliente) utilizadores.get(this.idUtilizadorAtual);

    	if(!(cl.getConfiguracoes().containsValue(c)))
    		throw new ConfiguracaoInexistenteException("Configuração inexistente - A configuração selecionada não pertence ao utilizador atual");

    	if(c.validaConfig()){	
    	   
            c.setEstado(2);
            count+=1;c.setPos(count);
            this.queue.add(c);
            cl.addConfiguracao(c);
            this.utilizadores.put(this.idUtilizadorAtual,cl);
    	   
           return true;
    	}
    	else return false;
    }

    //produz todos os carros na lista de espera da fábrica com componentes suficientes em stock
    public void produzCarros() throws AcaoInvalidaException, FabricaInexistenteException, FuncionarioInexistenteException, ComponenteInexistenteException, QuantidadeInseridaInvalidaException{

    	if(!(this.utilizadores.get(this.idUtilizadorAtual) instanceof FuncionarioFabrica))
    		throw new AcaoInvalidaException("Ação inválida - tipo de utilizador não tem permissões para efetuar esta operação");

        boolean flag = true;

        for(Configuracao cq : this.queue)
            for(Utilizador u : this.utilizadores.values()){
                if(u instanceof Cliente){
                    Cliente cl = (Cliente) u;
                    if(cl.getConfiguracoes().containsKey(cq.getId())){
                        Configuracao c = cl.getConfiguracoes().get(cq.getId());
                        for(String nomeComp : c.getComponentes().values()){
                            if(!this.componentes.containsKey(nomeComp))
                                throw new ComponenteInexistenteException("Componente Inexistente - O componente " + nomeComp + " não está registado no sistema");

                            Componente comp = this.componentes.get(nomeComp);
                            if(!(comp.getStock() > 0)) 
                                flag = false; 
                        }

                        if(flag){
                            for(String nomeComp : c.getComponentes().values())
                                this.atualizarStock(nomeComp,-1);
                            c.setEstado(3);
                            cl.addConfiguracao(c);
                            this.utilizadores.put(cl.getId(),cl);
                            this.queue.remove(c);
                        }
                    }
                }
                flag = true;
            }
    }
}
