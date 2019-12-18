/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creascriptproduzionechimica1.pkg0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author francescodigaudio
 */
public class LeggiDati {
    
    ArrayList<Data> data;
    
    public LeggiDati(Statement stm) {
          
//////        Connection conn = null;
        try {
////            Class.forName("com.mysql.cj.jdbc.Driver");
////            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/origamidb?user=root&password=isolmix1503");
////            System.out.println("Connessione Effettuata");
////
////            //PreparedStatement prepared = conn.prepareStatement("insert into persone (cognome, nome, eta) values (?,?,?)");
////            //prepared.setString(1, "Marroni");
////            //prepared.setString(2, "Enrico");
////            //prepared.setInt(3, 55);
////            //prepared.executeUpdate();
////            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select * from processo_ori");

            data = new ArrayList<>();
            Data data_temp;
            while (rs.next()) {
                data_temp= new Data();
                data_temp.setId_processo(rs.getInt("id_processo")); 
                data_temp.setCod_prodotto(rs.getString("cod_prodotto"));
                data_temp.setCod_chimica(rs.getString("cod_chimica"));
                data_temp.setCod_sacco(rs.getString("cod_sacco"));
                data_temp.setPeso_reale_sacco(rs.getInt("peso_reale_sacco"));
                data_temp.setCod_comp_peso(rs.getString("cod_comp_peso"));
                data_temp.setCod_colore(rs.getString("cod_colore"));
                data_temp.setCliente(rs.getString("cliente"));
                data_temp.setDt_produzione(rs.getString("dt_produzione"));
                data_temp.setCod_operatore(rs.getString("cod_operatore"));
                data_temp.setCod_comp_in(rs.getString("cod_comp_in"));
                data_temp.setTipo_processo(rs.getInt("tipo_processo"));
                data_temp.setInfo1(rs.getString("info1"));
                data_temp.setInfo2(rs.getString("info2"));
                data_temp.setInfo3(rs.getString("info3"));
                data_temp.setInfo4(rs.getString("info4"));
                data_temp.setInfo5(rs.getString("info5"));
                data_temp.setInfo6(rs.getString("info6"));
                data_temp.setInfo7(rs.getString("info7"));
                data_temp.setInfo8(rs.getString("info8"));
                data_temp.setInfo9(rs.getString("info9"));
                data_temp.setInfo10(rs.getString("info10"));
                data.add(data_temp);
                
            }
        } catch (SQLException e) {
        } finally {
             
        }
    }
   
    
}
