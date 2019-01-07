package ConfiguraFacil.Business;

import ConfiguraFacil.Business.Exceptions.*;

public abstract class Utilizador{

	private String id;
	private String pw;

	//Construtor vazio
	public Utilizador(){
		this.id = "ND";
		this.pw = "ND";
	}

	//Construtor por parâmetros
	public Utilizador(String id, String pw){
		this.id = id;
		this.pw = pw;
	}

	//Construtor por cópia
	public Utilizador(Utilizador u){
		this.id = u.getId();
		this.pw = u.getPw();
	}

	//Gets e Sets
	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getPw(){
		return this.pw;
	}

	public void setPw(String pw){
		this.pw = pw;
	}

    public abstract Utilizador clone();

    public boolean equals(Object o){
    	if (this==o) return true;
        if((o==null) || (this.getClass() != o.getClass())) return false;
        
        Utilizador u = (Utilizador) o;
        return (this.pw.equals(u.getPw())&& this.id.equals(u.getId()));
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("Identificador: ").append(this.id).append("\n");
        sb.append("Palavra Passe: ").append(this.pw).append("\n");
        
    	return sb.toString();
    }

}
