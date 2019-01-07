package ConfiguraFacil.Business;

import java.util.List;
import java.util.ArrayList;
                 
public class Pacote{
	private String nome;
	private double preco;
	private List<String> componentes;

	/*Construtor vazio*/
	public Pacote(){
		this.nome = "ND";
		this.preco = 0;
		this.componentes = new ArrayList<>();
	}
        
        /*Construtor por parâmetros*/
       public Pacote(String nome, double preco){
			this.nome = nome;
			this.preco = preco;
	}
        
	/*Construtor com parâmetros*/
	public Pacote(String nome, double preco, List<String> comp){
		this.nome = nome;
		this.preco = preco;
		this.componentes = new ArrayList<>();
		for(String c : comp) componentes.add(c);
	}
	
	/*Construtor por cópia*/
	public Pacote(Pacote p){
		this.nome = p.getNome();
		this.preco = p.getPreco();
		this.componentes = p.getComponentes();
	}

	/*Gets e Sets*/
	public String getNome(){
		return this.nome;
	}

	public void setNome(String n){
		this.nome = n;
	}

	public double getPreco(){
		return this.preco;
	}

	public void setPreco(double p){
		this.preco = p;
	}

	public List<String> getComponentes(){
		List<String> aux = new ArrayList<>();

		for(String c : this.componentes) aux.add(c);
		return aux;
	}

	public void setComponentes(List<String> l){
		this.componentes.clear();

		for(String c : l) this.componentes.add(c);
	}

	/*Métodos*/
	public void addComponente(String c){
		this.componentes.add(c);
	}

	public void remComponente(String c){
		this.componentes.remove(c);
	}

	public Pacote clone(){
		return new Pacote(this);
	}

	public boolean equals(Object o){
        if (this==o) return true;
        if((o==null) || (this.getClass() != o.getClass())) return false;
        
        Pacote p = (Pacote) o;
        return(this.nome.equals(p.getNome())
        	   && this.preco == p.getPreco()
        	   && this.componentes.equals(p.getComponentes()));
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();

		sb.append("Pacote ").append(this.nome).append(":\n");
		sb.append("Preço: ").append(this.preco).append("\n");
		sb.append("Componentes do pacote:\n").append(this.componentes).append("\n");

		return sb.toString();
	}
}