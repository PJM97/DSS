package ConfiguraFacil.Business;

import java.util.List;
import java.util.Map; 
import java.util.HashMap;
import ConfiguraFacil.Business.Exceptions.*;
import java.util.ArrayList;


public class Configuracao{
	
	private int id;
        private String idCliente;
	private double preco;
	private Map<String,String> componentes; //<Tipo do Componente,Nome do Componente>
	private List<String> pacotes; //<Nome do Pacote>
	private int estado; 
	private int pos;//Posicao na lista de encomendas da fabrica
	//estado atual da configuração(0-Configuração incompleta; 1-Configuração completa 
	//2-Configuração na fila de espera da fábrica; 3-Encomenda concluida e entregue ao cliente

	public Configuracao(String idCliente){
		this.id = 0;
                this.idCliente = idCliente;
		this.preco = 0;
		this.estado = 0;
		this.pos = 0;
		this.componentes = new HashMap<>();
		this.pacotes = new ArrayList<>();
	}
        
  
	public Configuracao(int id, String idCliente){
		this.id = id;
                this.idCliente = idCliente;
		this.preco = 0;
		this.estado = 0;
		this.pos = 0;
		this.componentes = new HashMap<>();
		this.pacotes = new ArrayList<>();
	}

	public Configuracao(int id, Double preco,int estado,String idCliente,int sp){
		this.id = id;
                this.idCliente=idCliente;
		this.preco = preco;
		this.estado = estado;
		this.pos = sp;
		this.componentes = new HashMap<>();
		this.pacotes = new ArrayList<>();
	}

	public Configuracao(int id, double preco, Map<String,String> m, List<String> p, int i, int pos){
		this.id = id;
		this.preco = preco;
		this.estado = i;
		this.pos = pos;
		this.componentes = new HashMap<>();
		this.pacotes = new ArrayList<>();
		for(Map.Entry<String, String> e : m.entrySet()) componentes.put(e.getKey(), e.getValue());
		for(String s : p) pacotes.add(s);
	}
	
	public Configuracao(Configuracao c){
		this.id = c.getId();
		this.preco = c.getPreco();
		this.estado = c.getEstado();
		this.pos = c.getPos();
		this.componentes = c.getComponentes();
		this.pacotes = c.getPacotes();
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}
        
        public String getIdCliente(){
            return this.idCliente;
        }
        
        public void setIdCliente(String idCliente){
            this.idCliente = idCliente;
        }
        
	public double getPreco(){
		return this.preco;
	}

	public void setPreco(double preco){
		this.preco = preco;
	}

	public int getEstado(){
		return this.estado;
	}

	public void setEstado(int estado){
		this.estado = estado;
	}

	public int getPos(){
		return this.pos;
	}

	public void setPos(int pos){
		this.pos = pos;
	}

	public Map<String,String> getComponentes(){
		Map<String,String> aux = new HashMap<>();
		for(Map.Entry<String,String> e : this.componentes.entrySet()) 
			aux.put(e.getKey(), e.getValue());
		return aux;
	}

	public void setComponentes (Map<String,String> m){
		this.componentes.clear();
		for(Map.Entry<String,String> e : m.entrySet()) 
			this.componentes.put(e.getKey(), e.getValue());
	}

	public List<String> getPacotes(){
		List<String> aux = new ArrayList<>();
		for(String s : this.pacotes) aux.add(s);
		return aux;
	}

	public void setPacotes (List<String> p){
		this.pacotes.clear();
		for(String s : p) this.pacotes.add(s);
	}

	//Métodos
	public void addComponente(Componente c){
		this.componentes.put(c.getTipo(),c.getNome());
	}
        
        public void addComponente(String k, String v){
		this.componentes.put(k,v);
	}


	public void remComponente(Componente c){
		this.componentes.remove(c.getTipo(),c.getNome());
	}

	public void addPacote(Pacote p){
		this.pacotes.add(p.getNome());
	}
        
        public void addPacote(String p){
		this.pacotes.add(p);
	}

	public void remPacote(Pacote p){
		this.pacotes.add(p.getNome());
	}

	public boolean validaConfig(){
		boolean flag = false;
		
		if(this.componentes.containsKey("carro") && this.componentes.containsKey("pintura") 
			&& this.componentes.containsKey("jantes") && this.componentes.containsKey("pneus") 
			&& this.componentes.containsKey("motor") && this.componentes.containsKey("vidros")
			&& this.componentes.containsKey("para-choques") && this.componentes.containsKey("estofos"))
				flag = true;

		if(flag) this.setEstado(1);;
		return flag;
	}

	public Configuracao clone(){
		return new Configuracao(this);
	}

	public boolean equals(Object o){
		if (this==o) return true;
        if((o==null) || (this.getClass() != o.getClass())) return false;
        
        Configuracao p = (Configuracao) o;
        return(this.id == p.getId() && this.idCliente.equals(p.getIdCliente()) && this.preco == p.getPreco()
        	   && this.estado == p.getEstado() && this.pos == p.getPos()
        	   && this.componentes.equals(p.getComponentes()) && this.pacotes.equals(p.getPacotes()));
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
        
        sb.append("Configuracao ").append(this.id).append(":\n");
        sb.append("IdCliente: ").append(this.idCliente).append("\n");
        sb.append("Estado: ").append(this.estado).append("\n");
        sb.append("Posição na fila de encomendas da fábrica: ").append(this.pos).append("\n");
        sb.append("Preço: ").append(this.preco).append("\n");
        sb.append("Componentes da configuração:\n").append(this.componentes.toString()).append("\n");
        sb.append("Pacotes da configuração:\n").append(this.pacotes.toString());

        return sb.toString();
	}

}