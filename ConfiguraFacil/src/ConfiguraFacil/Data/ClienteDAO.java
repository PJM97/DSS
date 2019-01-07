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
import ConfiguraFacil.Business.Cliente;

/**
 Aqui tenho comentada as variaveis da classe Cliente para ajudar a entender:
 * 
 * Da classe Utilizador (super) temos as var:
 * 
 *  String id (identificado que tb será a chave do map)
 *  String pw (string password)
 *
 * -- Var do cliente:
 * String nome   
 * String email
 * Map<String,Configuracao> configuracoes
 */

/**
 Aqui tenho uma versao simples da Base de dados
 * 
 *  CREATE TABLE IF NOT EXISTS `NOME_DA_BD`.`Cliente` (
  `username` VARCHAR(10) NOT NULL, --> chave da bd-> mesmo valor que a id da super classe utilizador e chave do map
  `password` VARCHAR(25) NOT NULL, --> password -> está declarado na classe super utilizador
  `nome` VARCHAR(80) NOT NULL, --> nome do cliente
  `email` VARCHAR(80) NOT NULL, --> email do cliente
  `visivel` TINYINT NOT NULL, --> Diz se o elemento está visivel (para ao apagar nao ir tudo mesmo de piço)
  PRIMARY KEY (`username`))
ENGINE = InnoDB;
 */

public class ClienteDAO implements Map<String,Cliente> {
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
            stm.executeUpdate("UPDATE Cliente SET visivel = FALSE WHERE username != \"\";");
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage()); 
        } finally {
            Connect.close(conn);    // fechar a connecção
        }
    }
    /**
     * Verifica se um número de Cliente existe na base de dados
     * @param key
     * @return
     * @throws NullPointerException 
     */
    @Override
    public boolean containsKey(Object key) throws NullPointerException {
        boolean r = false; // variavel onde colocar o resultado
        try {
            conn = Connect.connect();
            // string com a querie: Buscar o Cliente com a tal chave
            String sql = "SELECT username FROM Cliente WHERE username=? AND visivel=TRUE;";
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
     * Verifica se um Cliente existe na base de dados. Não é testado as suas configurações
     * 
     * @param value
     * @return 
     */
    @Override
    public boolean containsValue(Object value) {
        Cliente c;
        if (value instanceof Cliente)
            c = (Cliente) value; // cast do value do map para Cliente
        else return false;
        boolean r = false; // variavel com o resultado
        try {
            conn = Connect.connect();// estabelecer uma conecção
            //String com a querie
            String sql = "SELECT username FROM Cliente WHERE username=? AND password=? AND nome=? AND email=? AND visivel=TRUE;";
            PreparedStatement stm = conn.prepareStatement(sql);
            // Parse dos dados da 
            stm.setString(1, c.getId()); //passar o id do cliente(username), que é a 1º atrubuto da base de dados
            stm.setString(2, c.getPw()); // passar a passwoard -> que é o 2º atributo da base de dados
            stm.setString(3, c.getNome()); // passar o nome -> o 3º atributo da base de dados
            stm.setString(4, c.getEmail()); // passar o email -> o 4º atributo da base de dados
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
    public Set<Map.Entry<String,Cliente>> entrySet() {
        //resultado
        Set<Map.Entry<String,Cliente>> r = new HashSet<>();
        Cliente c;
        try {
            conn = Connect.connect();// abrir uma conecção
            // querie que obtem os dados para poder
            PreparedStatement stm = conn.prepareStatement("SELECT username, password, nome, email FROM Cliente WHERE visivel=TRUE;");
            // agora executa a querie
            ResultSet rs = stm.executeQuery();
            //percorrer o resultado
            while (rs.next()) {
                c = new Cliente(rs.getString("username"),rs.getString("password"),rs.getString("nome"),rs.getString("email"));
                r.add(new AbstractMap.SimpleEntry(c.getId(),c));
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
     * Obter um Cliente, dado o seu número
     * @param key
     * @return 
     */
    @Override
    public Cliente get(Object key) {
        //Cliente onde será colocado o resultado
        Cliente c = null;
        try {
            conn = Connect.connect(); // abre a conection
            //querie (obter os dados para construir um Cliente)
            PreparedStatement stm = conn.prepareStatement("SELECT username, password, nome, email FROM Cliente WHERE username=? AND visivel=TRUE");
            stm.setString(1, key.toString()); // parse do id para a bd
            ResultSet rs = stm.executeQuery();  // executa e temos o resultado
            if (rs.next()) {
                c = new Cliente(rs.getString("username"),rs.getString("password"),rs.getString("nome"),rs.getString("email"));
               
    
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
            PreparedStatement stm = conn.prepareStatement("SELECT username FROM Cliente WHERE visivel=TRUE;");
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
     * Insere um Cliente na base de dados
     * @param key
     * @param value
     * @return 
     */
    @Override
    // override do metudo put
    public Cliente put(String key, Cliente value) {
        Cliente cl = null;
        try {
            conn = Connect.connect();
            //String com a querie -> inserir um Cliente na BD
            PreparedStatement stm = conn.prepareStatement("INSERT INTO Cliente (username,password,nome,email,visivel)\n" +
                "VALUES (?, ?, ?, ?,TRUE)\n" +
                "ON DUPLICATE KEY UPDATE username=VALUES(username), password=VALUES(password),  nome=VALUES(nome), email=VALUES(email), visivel=VALUES(visivel);", Statement.RETURN_GENERATED_KEYS);
            //Parse dos dados Cliente argumento (value)
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
                    throw new PutClienteFailureException("Put Cliente failure - falha na inserção de um cliente na base de dados");
            }
            
            cl = value;
        } catch (SQLException | PutClienteFailureException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return cl;
    }

    /**
     * Por um conjunto de Clientes na base de dados
     * @param t 
     */
    
    @Override
    public void putAll(Map<? extends String,? extends Cliente> t) {
        for(Cliente c : t.values()) {
            this.put(c.getId(), c);
        }
    }

    /**
     * Remover um Cliente, a partir da chave do map
     * @param key
     * @return 
     */
    @Override
    public Cliente remove(Object key) {
        Cliente cl = this.get(key);
        try {
            conn = Connect.connect();
            
            PreparedStatement stm = conn.prepareStatement("UPDATE Cliente SET visivel=FALSE WHERE username=?;");
            
            stm.setString(1, (String)key); //parse da key para a querie
            stm.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return cl;
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
            ResultSet rs = stm.executeQuery("SELECT count(*) FROM Cliente WHERE visivel=TRUE;");
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
     * Obtém todos os Clientes da base de dados
     * @return 
     */
    @Override
    public Collection<Cliente> values() {
        Collection<Cliente> col = new HashSet<>();
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Cliente WHERE visivel=TRUE");
            Cliente c;
            
            while (rs.next()) {
                c = new Cliente(rs.getString("username"),rs.getString("password"),rs.getString("nome"),rs.getString("email"));
               
                
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
}


