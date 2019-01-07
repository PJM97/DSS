package ConfiguraFacil.Business;

public class Administrador extends Utilizador{

	public Administrador(){
		super();
	}

	public Administrador(String id, String pw){
		super(id,pw);
	}

	public Administrador(Administrador f){
		super(f);
	}

	//Métodos
	public Administrador clone(){
		return new Administrador(this);
	}

	public boolean equals(Object o){
		if (this==o) return true;
        if((o==null) || (this.getClass() != o.getClass())) return false;
        
        Administrador f = (Administrador) o;
        return(super.equals(f));
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Administrador:\n").append(super.toString());

        return sb.toString();
	}
}