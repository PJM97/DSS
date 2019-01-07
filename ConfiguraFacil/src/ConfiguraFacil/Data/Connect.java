package ConfiguraFacil.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {   
    
    // Aqui temos as cenas Basicas para fazer a ponte com a BD
    private static final String URL = "localhost";  // ser localhoast
    private static final String DB = "DSS";     // Nome da BD -> TEMOS DE DAR O NOME DA NOSSA BD
    private static final String USERNAME = "root"; 
    private static final String PASSWORD = "BD_UMinho_1718";    // TB temos de ter cuidado com a passWord
    
    
    
    
    // Basicamente este metudo serve para estabelecer conecção com a bd
    
    /**
     * Estabelece ligação à base de dados
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://"+URL+"/"+DB+"?useSSL=false&user="+USERNAME+"&password="+PASSWORD);    
        //return DriverManager.getConnection("jdbc:mysql://"+URL+"/"+DB+"?user="+USERNAME+"&password="+PASSWORD);    
    }
    
    
    
    /**
     * Fecha a ligação à base de dados, se aberta.
     * @param c 
     */
    public static void close(Connection c) {
        try {
            if(c!=null && !c.isClosed()) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

