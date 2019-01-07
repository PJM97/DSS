package ConfiguraFacil.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.List;
import ConfiguraFacil.Business.Pacote;
       
/**
 private String nome;
private String nome;
private double preco;
private List<Componente> componentes;

Logo a BD vai ser algo do tipo:
* 
*  CREATE TABLE IF NOT EXISTS `NOME_DA_BD`.`Pacote` (
  `nome` VARCHAR(10) NOT NULL, 
  `nome` VARCHAR(50) NOT NULL,
  `preco` float NOT NULL,
  `visivel` TINYINT NOT NULL,
  PRIMARY KEY (`nome`))
ENGINE = InnoDB;
       
 */
public class PacoteDAO implements Map<String,Pacote>{
    
    /**
     * Variavel de instancia connection: O que permite estabelecer a comunicação com a BD.
     */
    private Connection conn;
    
    
    /**
     * Override do metudo clear: Metudo que apaga a base de dados.
     * Para nao livrar dos dados a 100%, simplesmente é passado o atributo visivel para FALSE
     */
    @Override
    public void clear() {
        try {
            
            conn = Connect.connect(); // abrir a conecção
            
            Statement stm = conn.createStatement(); // abrir o statment (querie a fazer)
            
            
            stm.executeUpdate("UPDATE Pacote SET visivel = FALSE WHERE nome != \"\";");
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage()); 
        } finally {
            Connect.close(conn);    // fechar a connecção
        }
    }
    
        /**
     * Verifica se um número de Pacote existe na base de dados
     * @param key
     * @return
     * @throws NullPointerException 
     */
    @Override
    public boolean containsKey(Object key) throws NullPointerException {
        
        boolean r = false; // variavel onde colocar o resultado
        
        try {
            
            conn = Connect.connect();
            
            // string com a querie: Buscar o Pacote com a tal chave
            String sql = "SELECT nomePacote FROM Pacote WHERE nomePacote=? AND visivel=TRUE;";
            
            
            PreparedStatement stm = conn.prepareStatement(sql); 
            
            stm.setString(1, key.toString()); // passa para a querie a chave(o username)
            
            ResultSet rs = stm.executeQuery(); // Executa a querie e coloca o resultado em rs
            
            r = rs.next(); // buscar o valor do resultado
            
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return r;
    }
    
    /**
     * Verifica se um Pacote existe na base de dados. Não é testado as suas configurações
     * 
     * @param value
     * @return 
     */
    @Override
    public boolean containsValue(Object value) {
        
        Pacote p;
        if (value instanceof Pacote)
            p = (Pacote) value; // cast do value do map para Pacote
        else return false;
        
        boolean r = false; // variavel com o resultado
        try {
            conn = Connect.connect();// estabelecer uma conecção
            
            //String com a querie
            String sql = "SELECT nomePacote FROM Pacote WHERE nomePacote=? AND preco=? AND visivel=TRUE;";
            

            PreparedStatement stm = conn.prepareStatement(sql);
            
            
            // Parse dos dados da 
            stm.setString(1, p.getNome()); 
            stm.setDouble(2, p.getPreco()); 
            
            
            
            
            ResultSet rs = stm.executeQuery(); // executa a querie
            
            r = rs.next(); // agora em r é colocado o resultado
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return r;
    }
    
        /**
     Override do metudo entrySet -> devolve tanto a chave como o valor associado
     */
    @Override
    public Set<Map.Entry<String,Pacote>> entrySet() {
        
        //resultado
        Set<Map.Entry<String,Pacote>> r = new HashSet<>();
        
       
        Pacote p;
        
        try {
         
            conn = Connect.connect();// abrir uma conecção
            
            // querie que obtem os dados para poder
            PreparedStatement stm = conn.prepareStatement("SELECT nomePacote, preco FROM Pacote WHERE visivel=TRUE;");
            
            // agora executa a querie
            ResultSet rs = stm.executeQuery();
            
            //percorrer o resultado
            while (rs.next()) {
                p = new Pacote(rs.getString("nomePacote"),rs.getDouble("preco"));
                
                
                /*
                    AGORA FALTA OS METUDOS PARA PREENCHER A LISTA DAS CONFIGURAÇÕES
                */
                
                this.addComponentes(p);
              
                
                
                r.add(new AbstractMap.SimpleEntry(p.getNome(),p));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn); // fechar a connecção
        }
        return r;
    }
    
    
    /**
     * Override do metudo equals
     */
    @Override
    public boolean equals(Object o) {
        return (o!=null && o.getClass().equals(this.getClass()));
    }
    
    /**
     * Obter um Pacote, dado o seu número
     * @param key
     * @return 
     */
    @Override
    public Pacote get(Object key) {
        
        //Pacote onde será colocado o resultado
        Pacote p = null;
        
        try {
            
            conn = Connect.connect(); // abre a conection
           
            //querie (obter os dados para construir um Cliente)
            PreparedStatement stm = conn.prepareStatement("SELECT nomePacote, preco FROM Pacote WHERE nomePacote=? AND visivel=TRUE");
            
            stm.setString(1, key.toString()); // parse do nome para a bd
            
     
            ResultSet rs = stm.executeQuery();  // executa e temos o resultado
             
            if (rs.next()) {
                p = new Pacote(rs.getString("nomePacote"),rs.getDouble("preco"));
                this.addComponentes(p);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return p;
    }
    
        /**
     *Override do hashcode 
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
     * Metudo que devolve todas as chaves da base de dados
     */
    @Override 
    public Set<String> keySet() {
        
        
        // definido um set onde colocar o resultado
        Set<String> r = new HashSet<>();
        
        try {
            
            // abre uma conecção
            conn = Connect.connect();
            
            // abrir um statment com a querie que temos de executar
            PreparedStatement stm = conn.prepareStatement("SELECT nomePacote FROM Pacote WHERE visivel=TRUE;");
            
            
            // o resultado colocado em rs
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) // enquanto existir o next
                r.add(rs.getString("nomePacote")); 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return r;
    }
    
    
        /**
     * Insere um Pacote na base de dados
     * @param key
     * @param value
     * @return 
     */
    @Override
    
    // override do metudo put
    public Pacote put(String key, Pacote value) {
        
        
        Pacote pt = null;
       
        try {
            conn = Connect.connect();
            
            //String com a querie -> inserir um Pacote na BD
            PreparedStatement stm = conn.prepareStatement("INSERT INTO Pacote (nomePacote,preco,visivel)\n" +
                "VALUES (?, ?,TRUE)\n" +
                "ON DUPLICATE KEY UPDATE nomePacote=VALUES(nomePacote), preco=VALUES(preco), visivel=VALUES(visivel);", Statement.RETURN_GENERATED_KEYS);
           
            //Parse dos dados Cliente argumento (value)
            stm.setString(1, value.getNome());
            stm.setDouble(2, value.getPreco());
            
            // executa a querie
            stm.executeUpdate();
            
             this.addComponentes(pt);
            
            ResultSet rs = stm.getGeneratedKeys();
            if(rs.next()) {
                String newNome = rs.getString("nome");
                if (!newNome.equals(value.getNome()))
                    throw new PutPacoteFailureException("Put Pacote failure - falha na inserção de um Pacote na base de dados");
            }
            
            pt = value;
        } catch (SQLException | PutPacoteFailureException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return pt;
    }
    
     /**
     * Por um conjunto de Pacotes na base de dados
     * @param t 
     */
    @Override
    public void putAll(Map<? extends String,? extends Pacote> t) {
        for(Pacote p : t.values()) {
            this.put(p.getNome(), p);
        }
    }
    
        /**
     * Remover um Pacote, a partir da chave do map
     * @param key
     * @return 
     */
    @Override
    public Pacote remove(Object key) {
        Pacote pt = this.get(key);
        try {
            conn = Connect.connect();
            
            PreparedStatement stm = conn.prepareStatement("UPDATE Pacote SET visivel=FALSE WHERE nomePacote=?;");
            
            stm.setString(1, (String)key); //parse da key para a querie
            stm.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return pt;
    }
    
    
        /**
     * Retorna o número de entradas na base de dados
     * @return 
     */
    @Override
    public int size() {
        int i = 0;
        try {
            conn = Connect.connect();
            
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT count(*) FROM Pacote WHERE visivel=TRUE;");
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        }
        finally {
            Connect.close(conn);
        }
        return i;
    }
    
        /**
     * Obtém todos os Pacotes da base de dados
     * @return 
     */
    @Override
    public Collection<Pacote> values() {
        Collection<Pacote> col = new HashSet<>();
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Pacote WHERE visivel=TRUE");
            Pacote pt;
            
            while (rs.next()) {
                pt = new Pacote(rs.getString("nomePacote"),rs.getDouble("preco"));
                 this.addComponentes(pt);
                
                col.add(pt);
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            Connect.close(conn);
        }
        return col;
    }
    
    
     /**
     *
     * Métudo que adiciona Pacotes À configuracao
     * 
     */
    private void addComponentes(Pacote p) throws SQLException {
        try {
            
            // criar um statment
            PreparedStatement stm = conn.prepareStatement(
                    
   
                    "SELECT pc.Componente_nome AS cn" 
      
                            + " FROM Pacote_has_Componente AS pc\n" +
                        "WHERE pc.Pacote_nomePacote=?;"    //buscar as componentes da configuracao
            );
            //parse do id
            stm.setString(1, p.getNome());
            
            ResultSet rs = stm.executeQuery(); //agora aqui temos os ids das componentes desta configuracao
            
            while (rs.next())
                p.addComponente(rs.getString("cn")); //adiciona o elemento
            
            }catch (Throwable e)  {
                
                e.printStackTrace();
        }
        
    }
}
