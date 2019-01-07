package ConfiguraFacil.Business;

import java.util.List;
import java.util.ArrayList;

public class Componente{
	private String nome;
	private String tipo; /*tipo do componente(ex:pintura,jantes,pneus,motor...)*/
	private String designacao; /*caracteristicas do componente(ex:pintura azul)*/
	private Double preco; /*preço do componente*/
        private int stock; //stock do componente
	private List<String> incomp; /*Lista com os nomes dos componentes incompatíveis*/
	private List<String> obriga; /*Lista com os nomes dos componentes obrigatórios*/

	/* Construtor vazio */
	public Componente(){
		this.nome = "ND";
		this.tipo = "ND";
		this.designacao = "ND";
		this.preco = (double) 0;
                this.stock = 0;
		this.incomp = new ArrayList<>();
		this.obriga = new ArrayList<>();
	}

	/* Construtor com todos os  parâmetros excepto as listas de incompatibilidade e obrigação*/
	public Componente(String nome, String tipo, String designacao, Double preco, int stock){
		this.nome = nome;
		this.tipo = tipo;
		this.designacao = designacao;
		this.preco = preco;
                this.stock = stock;
        this.incomp = new ArrayList<>();
		this.obriga = new ArrayList<>();
	}

	/* Construtor com todos os parâmetros*/
	public Componente(String nome, String tipo, String designacao, Double preco, int stock, List<String> inc, List<String> obr){
		this.nome = nome;
		this.tipo = tipo;
		this.designacao = designacao;
		this.preco = preco;
                this.stock = stock;
		this.incomp = new ArrayList<>();
		this.obriga = new ArrayList<>();
		for(String c : inc) this.incomp.add(c);
		for(String c : obr) this.obriga.add(c);
	}
	
	/* Construtor por cópia*/
	public Componente(Componente c){
		this.nome = c.getNome();
		this.tipo = c.getTipo();
		this.designacao = c.getDesignacao();
		this.preco = c.getPreco();
                this.stock = c.getStock();
		this.incomp = c.getIncomp();
		this.obriga = c.getObriga();
	}

	/*Gets e Sets*/
	public String getNome(){
		return this.nome;
	}

	public void setNome(String nome){
		this.nome = nome;
	}

	public String getTipo(){
		return this.tipo;
	}

	public void setTipo(String tipo){
		this.tipo = tipo;
	}

	public String getDesignacao(){
		return this.designacao;
	}

	public void setDesignacao(String designacao){
		this.designacao = designacao;
	}

	public Double getPreco(){
		return this.preco;
	}

	public void setPreco(Double preco){
		this.preco = preco;
	}
        
    public int getStock(){
		return this.stock;
	}

	public void setStock(int stock){
		this.stock = stock;
	}
        
	public List<String> getIncomp(){
		ArrayList<String> aux = new ArrayList<>();
		for(String c : this.incomp) aux.add(c);
		return aux;
	}
        
	public void setIncomp(List<String> inc){
		this.incomp.clear();
		for(String c : inc) this.incomp.add(c);
	}

	public List<String> getObriga(){
		ArrayList<String> aux = new ArrayList<>();
		for(String c : this.obriga) aux.add(c);
		return aux;
	}

	public void setObriga(List<String> inc){
		this.obriga.clear();
		for(String c : inc) this.obriga.add(c);
	}

	/*Métodos*/
	public void addIncomp(String c){
		if(!this.incomp.contains(c))
        	this.incomp.add(c);
	}
        
	public void remIncomp(String c){
		this.incomp.remove(c);
	}

	public void addObriga(String c){
		if(!this.obriga.contains(c))
			this.obriga.add(c);
	}

	public void remObriga(String c){
		this.obriga.remove(c);
	}

	public Componente clone(){
		return new Componente(this);
	}

	public boolean equals(Object o){
        if (this==o) return true;
        if((o==null) || (this.getClass() != o.getClass())) return false;
        
        Componente c = (Componente) o;
        return(this.nome.equals(c.getNome()) && this.tipo.equals(c.getTipo())
        	   && this.designacao.equals(c.getDesignacao()) && this.preco.equals(c.getPreco())
                   && this.stock==stock && this.incomp.equals(c.getIncomp()) 
                   && this.obriga.equals(c.getObriga()));
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
        
        sb.append("Componente ").append(this.nome).append(":\n");
        sb.append("Tipo: ").append(this.tipo).append("\n");
        sb.append("Descrição: ").append(this.designacao).append("\n");
        sb.append("Preço: ").append(this.preco).append("\n");
        sb.append("Stock: ").append(this.stock).append("\n");
        sb.append("Componentes incompativéis: ").append(this.getIncomp().toString()).append("\n");
        sb.append("Componentes obrigatórios: ").append(this.getObriga().toString()).append("\n");

        return sb.toString();
	}
}