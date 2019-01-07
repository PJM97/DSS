package ConfiguraFacil.Data;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import ConfiguraFacil.Business.Cliente;
import ConfiguraFacil.Business.FuncionarioStand;
import ConfiguraFacil.Business.FuncionarioFabrica;
import ConfiguraFacil.Business.Utilizador;
import ConfiguraFacil.Business.Administrador;
import java.util.AbstractMap;

public class UtilizadorDAO implements Map<String,Utilizador> {
    // temos a dao conecção
    private Connection conn;
    
    private ClienteDAO clDAO;
    private FuncionarioStandDAO fsDAO;
    private FuncionarioFabricaDAO ffDAO;
    private AdministradorDAO adDAO;
    
    
     /**
      * construtor vazio
      */
    public UtilizadorDAO() {
        this.clDAO = new ClienteDAO();
        this.fsDAO = new FuncionarioStandDAO();
        this.ffDAO = new FuncionarioFabricaDAO();
        this.adDAO = new AdministradorDAO();
}

    
    /**
     * Apagar todos os utilizadores.
     */
    @Override
    
    // o clear é basicamente o clear de cada um dos elementos
    public void clear() {
        this.clDAO.clear();
        this.fsDAO.clear();
        this.ffDAO.clear();
        this.adDAO.clear();
    }
    
     /**
     * Verifica se um número de utilizador existe na base de dados
     * @param key
     * @return
     * @throws NullPointerException 
     */
    @Override
    
    // o contains key -> logo vai ser o ou de todas as classes
    public boolean containsKey(Object key) throws NullPointerException {
        return this.clDAO.containsKey(key) || this.fsDAO.containsKey(key) || this.ffDAO.containsKey(key)|| this.adDAO.containsKey(key);
    }
    
    /**
     * Contains Value, indica se um objeto existe
     * @param value
     * @return 
     */
    @Override // semelhante o cantains value
    public boolean containsValue(Object value) {
        return this.clDAO.containsValue(value) || this.fsDAO.containsValue(value) || this.ffDAO.containsValue(value)|| this.adDAO.containsValue(value);
    }
    
    /**
     *Entry Set 
     */
    @Override 
    public Set<Map.Entry<String,Utilizador>> entrySet() {

        Set<Map.Entry<String,Utilizador>> r = new HashSet<>();
        
        for (Map.Entry<String, Cliente> e: this.clDAO.entrySet())
            r.add(new AbstractMap.SimpleEntry<>(e.getKey(), (Utilizador) e.getValue()));
        
        for (Map.Entry<String, FuncionarioStand> e: this.fsDAO.entrySet())
            r.add(new AbstractMap.SimpleEntry<>(e.getKey(), (Utilizador) e.getValue()));
        
        for (Map.Entry<String, FuncionarioFabrica> e: this.ffDAO.entrySet())
            r.add(new AbstractMap.SimpleEntry<>(e.getKey(), (Utilizador) e.getValue()));
        
        for (Map.Entry<String, Administrador> e: this.adDAO.entrySet())
            r.add(new AbstractMap.SimpleEntry<>(e.getKey(), (Utilizador) e.getValue()));
        
        return r;
    }
    
    /**
     * equals
    */
    @Override
    public boolean equals(Object o) {
        return this.clDAO.equals(o) || this.fsDAO.equals(o) || this.ffDAO.equals(o)|| this.adDAO.equals(o);
    }
    
    /**
     * Obter utilizador
     * @param key
     * @return 
     */
    @Override
    public Utilizador get(Object key) {
        if (key==null) return null;
        Utilizador u = this.clDAO.get(key);
        if (u==null) u = this.fsDAO.get(key);
        if (u==null) u = this.ffDAO.get(key);
         if (u==null) u = this.adDAO.get(key);
        return u;
    }
    
    /**
     * Override do hashCode
    */
    @Override
    public int hashCode() {
        return this.conn.hashCode();
    }
    
    
    /**
     * Verifica se existem entradas
     * @return 
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
    
    /**
     * Override do KeySet
     */
    @Override
    public Set<String> keySet() {
        Set<String> r = this.clDAO.keySet();
        r.addAll(this.fsDAO.keySet());
        r.addAll(this.ffDAO.keySet());
        r.addAll(this.adDAO.keySet());
        return r;
    }
    
    /**
     * Inserir um Utilizador
     * @param key
     * @param value
     * @return 
     */
    @Override
    public Utilizador put(String key, Utilizador value) {
        Utilizador u = null;
        if (value instanceof Cliente)
            u = this.clDAO.put(key, (Cliente) value);
        else if (value instanceof FuncionarioStand)
            u = this.fsDAO.put(key, (FuncionarioStand) value);
        else if (value instanceof FuncionarioFabrica)
            u = this.ffDAO.put(key, (FuncionarioFabrica) value);
        else if (value instanceof Administrador)
            u = this.adDAO.put(key, (Administrador) value);
        return u;
    }
    
    /**
     * Override de putAll
     * @param t 
     */
    @Override
    public void putAll(Map<? extends String,? extends Utilizador> t) {
        for(Utilizador u : t.values()) {
            if (u instanceof Cliente)
                this.clDAO.put(u.getId(), (Cliente) u);
            else if (u instanceof FuncionarioStand)
                this.fsDAO.put(u.getId(), (FuncionarioStand) u);
            else if (u instanceof FuncionarioFabrica)
                this.ffDAO.put(u.getId(), (FuncionarioFabrica) u);
             else if (u instanceof Administrador)
                this.adDAO.put(u.getId(), (Administrador) u);
        }
    }
    
     /**
     * Remover utilizado
     * @param key
     * @return 
     */
    @Override
    public Utilizador remove(Object key) {
        Utilizador u = this.get(key);
        Utilizador r = null;
        if (u instanceof Cliente)
            r = this.clDAO.remove(key);
        else if (u instanceof FuncionarioStand)
            r = this.fsDAO.remove(key);
        else if (u instanceof FuncionarioFabrica)
            r = this.ffDAO.remove(key);
        else if (u instanceof Administrador)
            r = this.adDAO.remove(key);
        return r;
    }
 
    
     /**
     * Tamanho do numero de utilizadores
     * @return 
     */
    @Override
    public int size() {
        return this.clDAO.size() + this.fsDAO.size() + this.ffDAO.size()+ this.adDAO.size();
    }
    
    /**
     * Override de Values
     * @return 
     */
    @Override
    public Collection<Utilizador> values() {
        Collection<Utilizador> col = new HashSet<>();
        
        Collection<Cliente> client = this.clDAO.values();
        for (Cliente cl: client)
            col.add(cl);
        
        Collection<FuncionarioStand> funcs = this.fsDAO.values();
        for (FuncionarioStand f: funcs)
            col.add(f);
        
        Collection<FuncionarioFabrica> funcf = this.ffDAO.values();
        for (FuncionarioFabrica f: funcf)
            col.add(f);
        
        Collection<Administrador> ads = this.adDAO.values();
        for (Administrador a: ads)
            col.add(a);
        
        return col;
    }
    
}
