/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import ConfiguraFacil.Business.Administrador;
/**
 *
 * @author joaocosteira
 */
public class AdministradorDAO implements Map<String,Administrador>{
    
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
            stm.executeUpdate("UPDATE Administrador SET visivel = FALSE WHERE username != \"\";");
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage()); 
        } finally {
            Connect.close(conn);    // fechar a connecção
        }
    }
    /**
     * Verifica se um número de Administrador
     * existe na base de dados
     * @param key
     * @return
     * @throws NullPointerException 
     */
    @Override
    public boolean containsKey(Object key) throws NullPointerException {
        boolean r = false; // variavel onde colocar o resultado
        try {
            conn = Connect.connect();
            // string com a querie: Buscar o Administrador com tal chave

            String sql = "SELECT username FROM Administrador WHERE username=? AND visivel=TRUE;";
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
     * Verifica se um Administrador
     * existe na base de dados. Não é testado as suas configurações
     * 
     * @param value
     * @return 
     */
    @Override
    public boolean containsValue(Object value) {
        Administrador a;
        if (value instanceof Administrador)
             a = (Administrador) value; // cast do value do map para Administrador
        else 
            return false;
        
        boolean r = false; // variavel com o resultado
        try {
            conn = Connect.connect();// estabelecer uma conecção
            //String com a querie
            String sql = "SELECT username FROM Administrador WHERE username=? AND password=? AND visivel=TRUE;";
            PreparedStatement stm = conn.prepareStatement(sql);
            // Parse dos dados da 
            stm.setString(1, a.getId()); //passar o id do Administrador
            stm.setString(2, a.getPw()); // passar a passwoard -> que é o 2º atributo da base de dados
    
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
    public Set<Map.Entry<String,Administrador>> entrySet() {
        //resultado
        Set<Map.Entry<String,Administrador>> r = new HashSet<>();
                
        Administrador a;
               
        try {
            conn = Connect.connect();// abrir uma conecção
            // querie que obtem os dados para poder
            PreparedStatement stm = conn.prepareStatement("SELECT username, password FROM Administrador WHERE visivel=TRUE;");
            // agora executa a querie
            ResultSet rs = stm.executeQuery();
            //percorrer o resultado
            while (rs.next()) {
                a = new Administrador(rs.getString("username"),rs.getString("password"));
                r.add(new AbstractMap.SimpleEntry(a.getId(),a));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn); 
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
     * Obter um Administrador
     * , dado o seu número
     * @param key
     * @return 
     */
    @Override
    public Administrador get(Object key) {
        
        Administrador a = null;
                
        try {
            conn = Connect.connect(); // abre a conection
            //querie (obter os dados para construir um Administrador

            PreparedStatement stm = conn.prepareStatement("SELECT username, password FROM Administrador WHERE username=? AND visivel=TRUE");                    
            stm.setString(1, key.toString()); // parse do id para a bd
            ResultSet rs = stm.executeQuery();  // executa e temos o resultado
            if (rs.next()) {
                a = new Administrador(rs.getString("username"),rs.getString("password"));
               
    
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return a;
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
            PreparedStatement stm = conn.prepareStatement("SELECT username FROM Administrador WHERE visivel=TRUE;");
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
     * Insere um Administrador
     * na base de dados
     * @param key
     * @param value
     * @return 
     */
    @Override
    // override do metudo put
    public Administrador put(String key, Administrador value) {
         
                 
        Administrador a = null;
                
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO Administrador(username,password,visivel)\n" +
                "VALUES (?, ?,TRUE)\n" +
                "ON DUPLICATE KEY UPDATE username=VALUES(username), password=VALUES(password),visivel=VALUES(visivel);", Statement.RETURN_GENERATED_KEYS);
            
            stm.setString(1, value.getId());
            stm.setString(2, value.getPw());
            // executa a querie
            stm.executeUpdate();
           
            ResultSet rs = stm.getGeneratedKeys();
            if(rs.next()) {
                String newId = rs.getString("username");
                if (!newId.equals(value.getId()))
                    throw new PutAdministradorFailureException("Put Administradorfailure - falha na inserção de um Administrador na base de dados");
                       }               
                 a = value;
        } catch (SQLException | PutAdministradorFailureException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return a;
    }

    /**
     * Por um conjunto de Administrador
     * s na base de dados
     * @param t 
     */
    
    @Override
    public void putAll(Map<? extends String,? extends Administrador> t) {
        for(Administrador a : t.values()) {
            this.put(a.getId(), a);
        }
    }

    /**
     * Remover um Administrador
     * , a partir da chave do map
     * @param key
     * @return 
     */
    @Override
    public Administrador remove(Object key) {
        Administrador a = this.get(key);
        try {
            conn = Connect.connect();
            
            PreparedStatement stm = conn.prepareStatement("UPDATE Administrador SET visivel=FALSE WHERE username=?;");
            
            stm.setString(1, (String)key); //parse da key para a querie
            stm.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return a;
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
            ResultSet rs = stm.executeQuery("SELECT count(*) FROM Administrador WHERE visivel=TRUE;");
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
     * Obtém todos os Administrador
     * s da base de dados
     * @return 
     */
    @Override
    public Collection<Administrador> values() {
        Collection<Administrador> col = new HashSet<>();
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Administrador WHERE visivel=TRUE");
            Administrador a;
                    
            
            while (rs.next()) {
                a = new Administrador(rs.getString("username"),rs.getString("password"));
               
                
                col.add(a);
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
