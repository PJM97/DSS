package ConfiguraFacil.Business;

public class FuncionarioFabrica extends Utilizador{

	private String nome;
	private String email;

	public FuncionarioFabrica(){
		super();
		this.nome = "ND";
		this.email = "ND";
	}

	public FuncionarioFabrica(String id, String pw, String nome, String email){
		super(id,pw);
		this.nome = nome;
		this.email = email;
	}

	public FuncionarioFabrica(FuncionarioFabrica f){
		super(f);
		this.nome = f.getNome();
		this.email = f.getEmail();
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

	//Métodos
	public FuncionarioFabrica clone(){
		return new FuncionarioFabrica(this);
	}

	public boolean equals(Object o){
		if (this==o) return true;
        if((o==null) || (this.getClass() != o.getClass())) return false;
        
        FuncionarioFabrica f = (FuncionarioFabrica) o;
        return(super.equals(f) && this.nome.equals(f.getNome())
        	   && this.email.equals(f.getEmail()));
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Funcionário da fábrica:\n").append(super.toString());
        sb.append("Nome: ").append(this.nome).append("\n");
        sb.append("Email: ").append(this.email).append("\n");
        return sb.toString();
	}
}