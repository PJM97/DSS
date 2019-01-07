package ConfiguraFacil.Business;

import ConfiguraFacil.Data.ConfiguracaoDAO;
import java.util.HashMap;
import java.util.Map;

public class Cliente extends Utilizador{

	private String nome;
	private String email;
	private ConfiguracaoDAO configuracoes; //Map<idConfiguracao,Configuracao>

	public Cliente(){
		super();
		this.nome = "ND";
		this.email = "ND";
		this.configuracoes = new ConfiguracaoDAO();
	}

	public Cliente(String id, String pw, String nome, String email){
		super(id,pw);
		this.nome = nome;
		this.email = email;
		this.configuracoes = new ConfiguracaoDAO();
	}

	public Cliente(String id, String pw, String nome, String email, Map<Integer,Configuracao> conf){
		super(id,pw);
		this.nome = nome;
		this.email = email;
		this.configuracoes = new ConfiguracaoDAO();
	}

	public Cliente(Cliente c){
		super(c);
		this.nome = c.getNome();
		this.email = c.getEmail();
		this.configuracoes = c.getConfiguracoes();
	}

	//Gets e Sets
	public String getNome(){
		return this.nome;
	}

	public void setNome(String n){
		this.nome = n;
	}

	public String getEmail(){
		return this.email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public ConfiguracaoDAO getConfiguracoes(){
		return this.configuracoes;
	}

	public void setConfiguracoes(Map<Integer,Configuracao> m){
		this.configuracoes.clear();
		for(Map.Entry<Integer,Configuracao> e : m.entrySet()) 
			this.configuracoes.put(e.getKey(), e.getValue());
	}
	
	//Métodos
	public void addConfiguracao(Configuracao c){
		this.configuracoes.put(c.getId(),c);
	}

	public void remConfiguracao(Configuracao c){
		this.configuracoes.remove(c.getId());
	}

	public Cliente clone(){
    	return new Cliente(this);
    }

    public boolean equals(Object o){
		if (this==o) return true;
        if((o==null) || (this.getClass() != o.getClass())) return false;
        
        Cliente p = (Cliente) o;
        return(super.equals(p) && this.nome.equals(p.getNome())
        	   && this.email.equals(p.getEmail())
        	   && this.configuracoes.equals(p.getConfiguracoes()));
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Cliente:\n").append(super.toString());
        sb.append("Nome: ").append(this.nome).append("\n");
        sb.append("Email: ").append(this.email).append("\n");
        sb.append("Configuracoes do cliente:\n").append(this.configuracoes.toString()).append("\n");
        return sb.toString();
	}
}