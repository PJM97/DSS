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
import ConfiguraFacil.Business.FuncionarioStand;

/**
 * 
 * Da classe Utilizador (super) temos as var:
 * 
 *  String id (identificado que tb será a chave do map)
 *  String pw (string password)
 *
 * -- Var do FuncionarioStand:
 * String nome   
 * String email
 */

/**
 Aqui tenho uma versao simples da Base de dados
 * 
 *  CREATE TABLE IF NOT EXISTS `NOME_DA_BD`.`FuncionarioStand` (
  `username` VARCHAR(10) NOT NULL, --> chave da bd-> mesmo valor que a id da super classe utilizador e chave do map
  `password` VARCHAR(25) NOT NULL, --> password -> está declarado na classe super utilizador
  `nome` VARCHAR(80) NOT NULL, --> nome do FuncionarioStand
  `email` VARCHAR(80) NOT NULL, --> email do FuncionarioStand
  `visivel` TINYINT NOT NULL, --> Diz se o elemento está visivel (para ao apagar nao ir tudo mesmo de piço)
  PRIMARY KEY (`username`))
ENGINE = InnoDB;
 */



public class FuncionarioStandDAO implements Map<String,FuncionarioStand>{
    
     /**
     * Variavel de instancia connection: O que permite estabelecer a comunicação com a BD.
     */
    private Connection conn;
    
    /**
     * Override do metudo clear: Metudo que apaga a base de dados.
     */
    @Override
    public void clear() {
        try {
            
            conn = Connect.connect(); // abrir a conecção
            
            Statement stm = conn.createStatement(); // abrir o statment (querie a fazer)
            
            
            stm.executeUpdate("UPDATE FuncionarioStand SET visivel = FALSE WHERE username != \"\";");
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage()); 
        } finally {
            Connect.close(conn);    // fechar a connecção
        }
    }
    
     /**
     * Verifica se um número de FuncionarioStand existe na base de dados
     * @param key
     * @return
     * @throws NullPointerException 
     */
    
    
    @Override
    public boolean containsKey(Object key) throws NullPointerException {
        
        boolean r = false; 
        
        try {
            
            conn = Connect.connect();
            
            String sql = "SELECT nome FROM FuncionarioStand WHERE username=? AND visivel=TRUE;";
            
            
            PreparedStatement stm = conn.prepareStatement(sql); 
            
            stm.setString(1, key.toString()); 
            
            ResultSet rs = stm.executeQuery();
            
            r = rs.next(); 
            
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return r;
    }
    
    
    /**
     * Verifica se um FuncionarioStand existe na base de dados. Não é testado as suas configurações
     * 
     * @param value
     * @return 
     */
    @Override
    public boolean containsValue(Object value) {
        
        FuncionarioStand f;
        if (value instanceof FuncionarioStand)
            f = (FuncionarioStand) value; // cast do value do map para FuncionarioStand
        else return false;
        
        boolean r = false; // variavel com o resultado
        try {
            conn = Connect.connect();// estabelecer uma conecção
            
            //String com a querie
            String sql = "SELECT nome FROM FuncionarioStand WHERE username=? AND password=? AND nome=? AND email=? AND visivel=TRUE;";
            

            PreparedStatement stm = conn.prepareStatement(sql);
            
            
            // Parse dos dados da 
            stm.setString(1, f.getId()); 
            stm.setString(2, f.getPw()); 
            stm.setString(3, f.getNome()); 
            stm.setString(4, f.getEmail());
            
            
            
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
    public Set<Map.Entry<String,FuncionarioStand>> entrySet() {
        
        //resultado
        Set<Map.Entry<String,FuncionarioStand>> r = new HashSet<>();
        
       
        FuncionarioStand f;
        
        try {
         
            conn = Connect.connect();// abrir uma conecção
            
            // querie que obtem os dados para poder
            PreparedStatement stm = conn.prepareStatement("SELECT username, password, nome, email FROM FuncionarioStand WHERE visivel=TRUE;");
            
            // agora executa a querie
            ResultSet rs = stm.executeQuery();
            
            //percorrer o resultado
            while (rs.next()) {
                f = new FuncionarioStand(rs.getString("username"),rs.getString("password"),rs.getString("nome"),rs.getString("email"));
                r.add(new AbstractMap.SimpleEntry(f.getId(),f));
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
     * Obter um FuncionarioStand, dado o seu número
     * @param key
     * @return 
     */
    @Override
    public FuncionarioStand get(Object key) {
        
        FuncionarioStand f = null;
        
        try {
            
            conn = Connect.connect(); 

            PreparedStatement stm = conn.prepareStatement("SELECT username, password, nome, email FROM FuncionarioStand WHERE username=? AND visivel=TRUE");
            
            stm.setString(1, key.toString()); // parse do id para a bd
            
     
            ResultSet rs = stm.executeQuery();  // executa e temos o resultado
            
            
            
            if (rs.next()) {
                f = new FuncionarioStand(rs.getString("username"),rs.getString("password"),rs.getString("nome"),rs.getString("email"));
              
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return f;
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
            PreparedStatement stm = conn.prepareStatement("SELECT username FROM FuncionarioStand WHERE visivel=TRUE;");
            
            
            // o resultado colocado em rs
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) // enquanto existir o next
                r.add(rs.getString("username")); // adiciona o aluno
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return r;
    }
    
    
       /**
     * Insere um FuncionarioStand na base de dados
     * @param key
     * @param value
     * @return 
     */
    @Override
    public FuncionarioStand put(String key, FuncionarioStand value) {
        
        
        FuncionarioStand fn = null;
       
        try {
            conn = Connect.connect();
            
            //String com a querie -> inserir um FuncionarioStand na BD
            PreparedStatement stm = conn.prepareStatement("INSERT INTO FuncionarioStand (username,password,nome,email,visivel)\n" +
                "VALUES (?, ?, ?, ?,TRUE)\n" +
                "ON DUPLICATE KEY UPDATE username=VALUES(username), password=VALUES(password),  nome=VALUES(nome), email=VALUES(email), visivel=VALUES(visivel);", Statement.RETURN_GENERATED_KEYS);
           
            //Parse dos dados FuncionarioStand argumento (value)
            stm.setString(1, value.getId());
            stm.setString(2, value.getPw());
            stm.setString(3, value.getNome());
            stm.setString(4,value.getEmail());
            
            // executa a querie
            stm.executeUpdate();
           
            
            ResultSet rs = stm.getGeneratedKeys();
            if(rs.next()) {
                String newId = rs.getString("username");
                if (!newId.equals(value.getId()))
                    throw new PutFuncionarioFailureException("Put FuncionarioStand failure - falha na inserção de um FuncionarioStand na base de dados");
            }
            
            fn = value;
        } catch (SQLException | PutFuncionarioFailureException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return fn;
    }
    
    
    /**
     * Por um conjunto de Funcionarios na base de dados
     * @param t 
     */
    @Override
    public void putAll(Map<? extends String,? extends FuncionarioStand> t) {
        for(FuncionarioStand f : t.values()) {
            this.put(f.getId(), f);
        }
    }
    
      /**
     * Remover um FuncionarioStand, a partir da chave do map
     * @param key
     * @return 
     */
    @Override
    public FuncionarioStand remove(Object key) {
        FuncionarioStand fn = this.get(key);
        try {
            conn = Connect.connect();
            
            PreparedStatement stm = conn.prepareStatement("UPDATE FuncionarioStand SET visivel=FALSE WHERE username=?;");
            
            stm.setString(1, (String)key); //parse da key para a querie
            stm.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return fn;
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
            ResultSet rs = stm.executeQuery("SELECT count(*) FROM FuncionarioStand WHERE visivel=TRUE;");
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
     * Obtém todos os Funcionarios da base de dados
     * @return 
     */
    @Override
    public Collection<FuncionarioStand> values() {
        Collection<FuncionarioStand> col = new HashSet<>();
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM FuncionarioStand WHERE visivel=TRUE");
            FuncionarioStand f;
            
            while (rs.next()) {
                f = new FuncionarioStand(rs.getString("username"),rs.getString("password"),rs.getString("nome"),rs.getString("email"));  
                col.add(f);
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            Connect.close(conn);
        }
        return col;
    }
}


