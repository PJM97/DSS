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
import ConfiguraFacil.Business.Componente;

/*

        Variaveis de instancia de Componente
        String nome; 
	private String tipo; 
	private String designacao;
	private Double preco; 
	private List<Componente> incomp;
	private List<Componente> obriga;


        -- Logo vamos fazer uma base de dados com os seguintes componentes:

         Aqui tenho uma versao simples da Base de dados
 * 
 *  CREATE TABLE IF NOT EXISTS `NOME_DA_BD`.`Compomente` (
  `nome` VARCHAR(50) NOT NULL, 
  `tipo` VARCHAR(50) NOT NULL,
  `designacao` VARCHAR(80) NOT NULL, 
  `preco` float NOT NULL,
  `visivel` TINYINT NOT NULL, --> Diz se o elemento está visivel (para ao apagar nao ir tudo mesmo de piço)
  PRIMARY KEY (`username`))
ENGINE = InnoDB;

        */


public class ComponenteDAO implements Map<String,Componente>{
    
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
            
            
            stm.executeUpdate("UPDATE Componente SET visivel = FALSE WHERE nome != \"\";");
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage()); 
        } finally {
            Connect.close(conn);    // fechar a connecção
        }
    }
    
    //--------------------------------------
    /**
     * Verifica se um número de Componente existe na base de dados
     * @param key
     * @return
     * @throws NullPointerException 
     */
    @Override
    public boolean containsKey(Object key) throws NullPointerException {
        
        boolean r = false; // variavel onde colocar o resultado
        
        try {
            
            conn = Connect.connect();
            
            // string com a querie: Buscar o Componente com a tal chave
            String sql = "SELECT nome FROM Componente WHERE nome=? AND visivel=TRUE;";
            
            
            PreparedStatement stm = conn.prepareStatement(sql); 
            
            stm.setString(1, key.toString()); // passa para a querie a chave(o nome)
            
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
     * Verifica se um Componente existe na base de dados. Não é testado as suas configurações
     * 
     * @param value
     * @return 
     */
    @Override
    public boolean containsValue(Object value) {
        
        Componente c;
        if (value instanceof Componente)
            c = (Componente) value; // cast do value do map para Componente
        else return false;
        
        boolean r = false; // variavel com o resultado
        try {
            conn = Connect.connect();// estabelecer uma conecção
            //String com a querie
            String sql = "SELECT nome FROM Componente WHERE nome=? AND tipo=? AND designacao=? AND preco=? AND visivel=TRUE AND stock=?;";
            PreparedStatement stm = conn.prepareStatement(sql);
            // Parse dos dados da info
            stm.setString(1, c.getNome()); 
            stm.setString(2, c.getTipo()); 
            stm.setString(3, c.getDesignacao()); 
            stm.setDouble(4, c.getPreco());
            stm.setInt(5, c.getStock());
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
    public Set<Map.Entry<String,Componente>> entrySet() {
        
        //resultado
        Set<Map.Entry<String,Componente>> r = new HashSet<>();
        
       
        Componente c;
        
        try {
         
            conn = Connect.connect();// abrir uma conecção
            
            // querie que obtem os dados para poder
            PreparedStatement stm = conn.prepareStatement("SELECT nome, tipo, designacao, preco, stock FROM Componente WHERE visivel=TRUE;");
            
            // agora executa a querie
            ResultSet rs = stm.executeQuery();
            
            //percorrer o resultado
            while (rs.next()) {
                c = new Componente(rs.getString("nome"),rs.getString("tipo"),rs.getString("designacao"),rs.getDouble("preco"),rs.getInt("stock"));
                
                /*
                    AGORA FALTA OS METUDOS PARA PREENCHER A LISTA DAS CONFIGURAÇÕES
                
                    Agora temos de ter 
                */
                
                this.adicionarIncompativeis(c);
                this.adicionarObrigatorias(c);

                
                r.add(new AbstractMap.SimpleEntry(c.getNome(),c));
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
    
    
    @Override
    public Componente get(Object key) {
        
        //Componente onde será colocado o resultado
        Componente c = null;
        
        try {
            
            conn = Connect.connect(); // abre a conection
           
            //querie (obter os dados para construir um Cliente)
            PreparedStatement stm = conn.prepareStatement("SELECT nome, tipo, designacao, preco, stock FROM Componente WHERE nome=? AND visivel=TRUE");
            
            stm.setString(1, key.toString()); // parse do id para a bd
            
     
            ResultSet rs = stm.executeQuery();  // executa e temos o resultado
            
            
            
            if (rs.next()) {
                c = new Componente(rs.getString("nome"),rs.getString("tipo"),rs.getString("designacao"),rs.getDouble("preco"),rs.getInt("stock"));
                this.adicionarIncompativeis(c);
                this.adicionarObrigatorias(c);
                
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return c;
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
            PreparedStatement stm = conn.prepareStatement("SELECT nome FROM Componente WHERE visivel=TRUE;");
            
            
            // o resultado colocado em rs
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) // enquanto existir o next
                r.add(rs.getString("nome")); // adiciona o aluno
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return r;
    }
    
    
        /**
     * Insere um Cliente na base de dados
     * @param key
     * @param value
     * @return 
     */
    @Override

    public Componente put(String key, Componente value) {
        
        
        Componente cp = null;
       
        try {
            conn = Connect.connect();
            
            //String com a querie -> inserir um Componente na BD
            PreparedStatement stm = conn.prepareStatement("INSERT INTO Componente (nome,tipo,designacao,preco,visivel,stock)\n" +
                "VALUES (?, ?, ?, ?,TRUE,?)\n" +
                "ON DUPLICATE KEY UPDATE nome=VALUES(nome), tipo=VALUES(tipo),  designcao=VALUES(designcao), preco=VALUES(preco), visivel=VALUES(visivel),stock=VALUES(stock);", Statement.RETURN_GENERATED_KEYS);
           
            //Parse dos dados Componentes argumento (value)
            stm.setString(1, value.getNome());
            stm.setString(2, value.getTipo());
            stm.setString(3, value.getDesignacao());
            stm.setDouble(4,value.getPreco());
            stm.setInt(5,value.getStock());
            
            // executa a querie
            stm.executeUpdate();
            
            this.adicionarIncompativeis(cp);
            this.adicionarObrigatorias(cp);

            
            ResultSet rs = stm.getGeneratedKeys();
            if(rs.next()) {
                String newId = rs.getString("nome");
                if (!newId.equals(value.getNome()))
                    throw new PutComponenteFailureException("Put Componente failure - falha na inserção de uma componente na base de dados");
            }
            
            cp = value;
        } catch (SQLException | PutComponenteFailureException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return cp;
    }
    
     /**
     * Por um conjunto de Funcionarios na base de dados
     * @param t 
     */
    @Override
    public void putAll(Map<? extends String,? extends Componente> t) {
        for(Componente c : t.values()) {
            this.put(c.getNome(), c);
        }
    }
    
        /**
     * Remover um Componente a partir da chave do map
     * @param key
     * @return 
     */
    @Override
    public Componente remove(Object key) {
        Componente cp = this.get(key);
        try {
            conn = Connect.connect();
            
            PreparedStatement stm = conn.prepareStatement("UPDATE Componente SET visivel=FALSE WHERE nome=?;");
            
            stm.setString(1, (String)key); //parse da key para a querie
            stm.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return cp;
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
            ResultSet rs = stm.executeQuery("SELECT count(*) FROM Componente WHERE visivel=TRUE;");
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
     * Obtém todos as Componentes da base de dados
     * @return 
     */
    @Override
    public Collection<Componente> values() {
        Collection<Componente> col = new HashSet<>();
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Componente WHERE visivel=TRUE");
            Componente c;
            
            while (rs.next()) {
                 c = new Componente(rs.getString("nome"),rs.getString("tipo"),rs.getString("designacao"),rs.getDouble("preco"),rs.getInt("stock"));
                
                /*
                    AGORA FALTA OS METUDOS PARA PREENCHER A LISTA DAS CONFIGURAÇÕES
                
                    Agora temos de ter 
                */
                
                this.adicionarIncompativeis(c);
                this.adicionarObrigatorias(c);
                
                col.add(c);
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
     * Métudo que adiciona Componentes incompativeis  à Componente
     * 
     */
    private void adicionarIncompativeis(Componente c) throws SQLException {
        try {
            
            // criar um statment
            PreparedStatement stm = conn.prepareStatement(
                    
   
                    "SELECT i.Componente_nome1 AS id" 
      
                            + " FROM Incompativel AS i\n" +
                        "WHERE i.Componente_nome=?;"    //buscar as componentes da configuracao
            );
            //parse do id
            stm.setString(1, c.getNome());
            
            ResultSet rs = stm.executeQuery(); //agora aqui temos os ids das componentes desta configuracao
            
            while (rs.next())
                c.addIncomp(rs.getString("id")); //adiciona o elemento
            
            }catch (Throwable e)  {
                
                e.printStackTrace();
        }
        
    }
    
     /**
     *
     * Métudo que adiciona Componentes incompativeis  à Componente
     * 
     */
    private void  adicionarObrigatorias(Componente c) throws SQLException {
        try {
            
            // criar um statment
            PreparedStatement stm = conn.prepareStatement(
                    
   
                    "SELECT o.Componente_nome1 AS id" 
      
                            + " FROM Obrigatorio AS o\n" +
                        "WHERE o.Componente_nome=?;"    //buscar as componentes da configuracao
            );
            //parse do id
            stm.setString(1, c.getNome());
            
            ResultSet rs = stm.executeQuery(); //agora aqui temos os ids das componentes desta configuracao
            
            while (rs.next())
                c.addObriga(rs.getString("id")); //adiciona o elemento
            
            }catch (Throwable e)  {
                
                e.printStackTrace();
        }
        
    }
    
    
    
}
