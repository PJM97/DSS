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
import java.util.AbstractMap;
import ConfiguraFacil.Business.Configuracao;

/*

    Variaveis de instancia de Configuracao
    private String id;
    private double preco;
    private Map<String,String> config;
    private Map<String,String> pacotes;
    private int estado; 


        -- Logo vamos fazer uma base de dados com os seguintes componentes:

         Aqui tenho uma versao simples da Base de dados
 idConfiguracao
preco
estado
visivel


        */


public class ConfiguracaoDAO implements Map<Integer,Configuracao>{
    
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
            
            
            stm.executeUpdate("UPDATE Configuracao SET visivel = FALSE WHERE id != \"\";");
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage()); 
        } finally {
            Connect.close(conn);    // fechar a connecção
        }
    }
    
    //--------------------------------------
    /**
     * Verifica se um número de Configuracao existe na base de dados
     * @param key
     * @return
     * @throws NullPointerException 
     */
    @Override
    public boolean containsKey(Object key) throws NullPointerException {
        
        boolean r = false; // variavel onde colocar o resultado
        
        try {
            
            conn = Connect.connect();
            
            // string com a querie: Buscar o Configuracao com a tal chave
            String sql = "SELECT idConfiguracao FROM Configuracao WHERE idConfiguracao=? AND visivel=TRUE;";
            
            
            PreparedStatement stm = conn.prepareStatement(sql); 
            
            stm.setInt(1, (int) key); // passa para a querie a chave(o id)
            
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
     * Verifica se um Configuracao existe na base de dados. Não é testado as suas configurações (não é percorrida a lista de
     * componentes)
     * 
     * @param value
     * @return 
     */
    @Override
    public boolean containsValue(Object value) {
        
        Configuracao c;
        if (value instanceof Configuracao)
            c = (Configuracao) value; // cast do value do map para Configuracao
        else return false;
        
        boolean r = false; // variavel com o resultado
        try {
            conn = Connect.connect();// estabelecer uma conecção
            //String com a querie
            String sql = "SELECT idConfiguracao FROM Configuracao WHERE idConfiguracao=? AND preco=? AND estado=? AND visivel=TRUE AND Cliente_username=? AND sp=?;";
            PreparedStatement stm = conn.prepareStatement(sql);
            // Parse dos dados da info
            stm.setInt(1, c.getId()); 
            stm.setDouble(2, c.getPreco());
            stm.setInt(3,c.getEstado());
            stm.setString(4,c.getIdCliente());
            stm.setInt(5, c.getPos());
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
    public Set<Map.Entry<Integer,Configuracao>> entrySet() {
        
        //resultado
        Set<Map.Entry<Integer,Configuracao>> r = new HashSet<>();
        
       
        Configuracao c;
        
        try {
         
            conn = Connect.connect();// abrir uma conecção
            
            // querie que obtem os dados para poder devolver
            PreparedStatement stm = conn.prepareStatement("SELECT idConfiguracao, preco, estado,Cliente_username, sp FROM Configuracao WHERE visivel=TRUE;");
            
            // agora executa a querie
            ResultSet rs = stm.executeQuery();
            
            //percorrer o resultado
            while (rs.next()) {
                c = new Configuracao(rs.getInt("idConfiguracao"),rs.getDouble("preco"),rs.getInt("estado"),rs.getString("Cliente_username"),rs.getInt("sp"));
                /*
                    Agora para cada configuração temos de preencher a lista de componentes 
                    compativeis e a lista das componentes incompativeis
                */
                
                this.adicionarCompAConf(c); //preenche as componentes
                
                
                this.adicionarPacoteAConf(c);   // adiciona Pacotes
                
                
                r.add(new AbstractMap.SimpleEntry(c.getId(),c));
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
    public Configuracao get(Object key) {
        
        //Configuracao onde será colocado o resultado
        Configuracao c = null;
        
        try {
            
            conn = Connect.connect(); // abre a conection
           
            //querie (obter os dados para construir um Cliente)
            PreparedStatement stm = conn.prepareStatement("SELECT idConfiguracao, preco, estado,Cliente_username,sp"
                                                           + "FROM Configuracao WHERE"
                                                        + " idConfiguracao=? AND visivel=TRUE");
            
            stm.setInt(1, (int)key); // parse do id para a bd
            
     
            ResultSet rs = stm.executeQuery();  // executa e temos o resultado
            
            
            
            if (rs.next()) {
                c = new Configuracao(rs.getInt("id"),rs.getDouble("preco"),rs.getInt("estado"),rs.getString("Cliente_username"),rs.getInt("sp"));
     
                this.adicionarCompAConf(c); //preenche as componentes
                this.adicionarPacoteAConf(c);   // adiciona Pacotes
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
    public Set<Integer> keySet() {
        
        
        // definido um set onde colocar o resultado
        Set<Integer> r = new HashSet<>();
        
        try {
            
            // abre uma conecção
            conn = Connect.connect();
            
            // abrir um statment com a querie que temos de executar
            PreparedStatement stm = conn.prepareStatement("SELECT idConfiguracao As id FROM Configuracao WHERE visivel=TRUE;");
            
            
            // o resultado colocado em rs
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) // enquanto existir o next
                r.add(rs.getInt("id")); // adiciona o aluno
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
    public Configuracao put(Integer key, Configuracao value) {
        
        
        Configuracao cp = null;
       
        try {
            conn = Connect.connect();
           
            PreparedStatement stm = conn.prepareStatement(
                    "INSERT INTO Configuracao (idConfiguracao,preco,estado,visivel,Cliente_username,sp)" +
                "VALUES (?,?,?,TRUE,?,?)" +
                "ON DUPLICATE KEY UPDATE idConfiguracao=VALUES(idConfiguracao), preco=VALUES(preco), estado=VALUES(estado), visivel=VALUES(visivel),Cliente_username=VALUES(Cliente_username);", Statement.RETURN_GENERATED_KEYS);
           
            //Parse dos dados Componentes argumento (value)
            stm.setInt(1, value.getId());
            stm.setDouble(2,value.getPreco());
            stm.setInt(3,value.getEstado());
            stm.setString(4,value.getIdCliente());
            stm.setInt(5,value.getPos());
            
            // executa a querie
            stm.executeUpdate();
            
           
             /*
            Adiciona dados À bd(as listas)
             */
            this.adicionarCompAConf(cp); //preenche as componentes
            this.adicionarPacoteAConf(cp);   // adiciona Pacotes
            
            ResultSet rs = stm.getGeneratedKeys();
            if(rs.next()) {
                String newId = rs.getString("id");
                if (!newId.equals(value.getId()))
                    throw new PutComponenteFailureException("Put Configuracao failure - falha na inserção de uma Configuracao na base de dados");
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
    public void putAll(Map<? extends Integer,? extends Configuracao> t) {
        for(Configuracao c : t.values()) {
            this.put(c.getId(), c);
        }
    }
    
        /**
     * Remover um Configuracao a partir da chave do map
     * @param key
     * @return 
     */
    @Override
    public Configuracao remove(Object key) {
        Configuracao cp = this.get(key);
        try {
            conn = Connect.connect();
            
            PreparedStatement stm = conn.prepareStatement("UPDATE Configuracao SET visivel=FALSE WHERE idConfiguracao=?;");
            
            stm.setInt(1, (int)key); //parse da key para a querie
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
            ResultSet rs = stm.executeQuery("SELECT count(*) FROM Configuracao WHERE visivel=TRUE;");
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
    public Collection<Configuracao> values() {
        Collection<Configuracao> col = new HashSet<>();
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Configuracao WHERE visivel=TRUE");
            Configuracao c;
            
            while (rs.next()) {
               c = new Configuracao(rs.getInt("idConfiguracao"),rs.getDouble("preco"),rs.getInt("estado"),rs.getString("Cliente_username"),rs.getInt("sp"));
                
                this.adicionarCompAConf(c); //preenche as componentes
                this.adicionarPacoteAConf(c);   // adiciona Pacotes
                
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
     *Metudo que completa a lista das componentes compativeis
     * 
     * 
     */
    private void  adicionarCompAConf(Configuracao c)throws SQLException {
        try {
            
            // criar um statment
            PreparedStatement stm = conn.prepareStatement(
                    
                    
                    
                    "SELECT c.nome AS nome,c.tipo As tipo" // selct cenas do componente
      
                            + " FROM Configuracao_has_Componente AS cc\n"
                            + "Inner Join Componente As c"  // buscar a comp para aceder ao nome dela
                            + "On c.nome=cc.Componente_nome" + 
                            
                        "WHERE cc.Configuracao_idConfiguracao=?;" // buscar todos os ids da configuracao
            );
            //parse do id
            stm.setInt(1, c.getId());
            
            ResultSet rs = stm.executeQuery(); //agora aqui temos os ids das componentes desta configuracao
            
            while (rs.next())
                c.addComponente(rs.getString("tipo"),rs.getString("nome")); //adiciona o elemento
            
            }catch (Throwable e) {
                
                e.printStackTrace();
        }
        
    }
    
    
    /**
     *
     * Métudo que adiciona Pacotes À configuracao
     * 
     */
    private void  adicionarPacoteAConf(Configuracao c) throws SQLException {
        try {
            
            // criar um statment
            PreparedStatement stm = conn.prepareStatement(
                    
   
                    "SELECT cp.Pacote_nomePacote AS paulo" 
      
                            + " FROM Configuracao_has_Pacote AS cp\n" +
                        "WHERE cp.Configuracao_idConfiguracao=?;"    //buscar as componentes da configuracao
            );
            //parse do id
            stm.setInt(1, c.getId());
            
            ResultSet rs = stm.executeQuery(); //agora aqui temos os ids das componentes desta configuracao
            
            while (rs.next())
                c.addPacote(rs.getString("paulo")); //adiciona o elemento
            
            }catch (Throwable e)  {
                
                e.printStackTrace();
        }
        
    }
    
   }