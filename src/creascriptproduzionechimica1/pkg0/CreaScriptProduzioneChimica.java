/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creascriptproduzionechimica1.pkg0;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author francescodigaudio
 */
public class CreaScriptProduzioneChimica {

    ////////////////
    // COSTANTI  ///
    //////////////// 
    static final String APP_NAME = "ASSOCIA CHIMICA ORIGAMI";
    static final String APP_VERSIONE = "v1.0";

    static final String APP_T0 = "ASSOCIA BOLLA";
    static final String APP_T2 = "Personal Factory SPA";

    static final String APP_M0 = "Associa Bolla Unica       ";
    static final String APP_M1 = "Inserire id bolla";
    static final String APP_M2 = "Inserire data bolla (aaaa-mm-gg)";
    static final String APP_M3 = "Inserire numero bolla";
    static final String APP_M4 = "Inserire id macchina";
    static final String APP_M5 = "Inserire codice stabilimento";
    static final String APP_M6 = "Inserire id Miscela (id ultima miscela+1)";
    static final String APP_M7 = "Avvia verifica dati....";

    static final String APP_M8 = "Inserire Numero Sacchi Lotto";
    static final String APP_M9 = "Inserire Numero Lotti";
    static final String APP_M10 = "Inserire Quantita Sacco";
    static final String APP_M11 = "Inserire Quantita Lotto";
    static final String APP_M12 = "Inserire Id Utente";
    static final String APP_M13 = "Inserire Id Azienda";
    static final String APP_M14 = "Inserire Data lotto (aaaa-mm-gg)";

    static final String APP_M15 = "Codice Lungo Componente";
    static final String APP_M16 = "Prezzo Componente (0.0 euro) ";
    static final String APP_M17 = "Fornitore";
    static final String APP_M18 = "Codice Artico Fornitore";
    static final String APP_M19 = "Data Arrivo Merce (aaaa-mm-gg)";
    static final String APP_M20 = "Correggi sacchi non validi";

    static final String NUMERO_CONTENITORE = "00099";
    static final String GAZ_MOV_MAG_OPERAZIONE = "-1";
    static final String GAZ_MOV_MAG_CAUSALE = "UNLOADING FOR PROCESSING";
    static final String GAZ_MOV_MAG_TIPO_DOC = "PRO-OUT";
    static final String GAZ_MOV_MAG_PROTOCOLLO = "0";
    static final String GAZ_MOV_MAG_DES_DOC = "MIXTURE PRODUCTION";
    static final String GAZ_MOV_MAG_CFLOCO = "103000071";
    static final String GAZ_MOV_MAG_DESCRI_ARTICO = "LOTTO KIT";
    static final String GAZ_MOV_MAG_CAT_MER = "2";
    static final String GAZ_MOV_MAG_UNITA_MISURA = "kg";
    static final String GAZ_MOV_MAG_VALUTAZIONE_MERCE = "1";
    static final String GAZ_MOV_MAG_VERIFICA_STABILITA = "1";
    static final String GAZ_MOV_MAG_PROCEDURA_ADOTTATA = "MIXTURE PRODUCTION";
    static final String GAZ_MOV_MAG_OPERATORE = "vito.umbrello";
    static final String GAZ_MOV_MAG_RESPONSABILE_PRODUZIONE = "Salvatore Nardi";
    static final String GAZ_MOV_MAG_RESPONSABILE_QUALITA = "Francesco Tassone";
    static final String GAZ_MOV_MAG_CONSULENTE_TECNICO = "Iuri Berbicato";
    static final String GAZ_MOV_MAG_NOTE = "";
    static final String GAZ_MOV_MAG_DOC_LINK = "";
    static final String GAZ_MOV_MAG_NUMERO_ORDINE = "0";
    static final String GAZ_MOV_MAG_DT_ORDINE = "1970-01-02";
    static final String CHIMICA_DESCRI_FORMULA = "KIT";
    static final String LOTTO_DESCRI_LOTTO = "LOTTO KIT";
    static final String LOTTO_PARENT = "1";
    
    static final String SCRIPT_PATH = "script.sql";

    /////////////////
    // VARIABILI  ///
    /////////////////
    static Connection conn = null;
    static Statement stm;
    static int id_miscela;
    static int num_sac_in_lotto;
    static int num_lotti;
    static int qta_sac;
    static int qta_lotto;
    static int qta_tot_miscela;
    static int id_utente;
    static int id_azienda;

    static String cod_comp_prec = "";
    static int id_prodotto;
    static String nome_prodotto;
    static String data_lotto;
    static String id_bolla = "";
    static String data_bolla = "";
    static String num_bolla = "";
    static String default_date = "";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
 
        
        //Schermata Inziale App
        JOptionPane.showMessageDialog(null, APP_NAME + "\n " + APP_VERSIONE + "\n\n" + APP_M7, APP_T2,1);
         
        //Creazione connessione database
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/origamidb?user=root&password=isolmix1503"); 
        stm = conn.createStatement(); 
        ArrayList<Data> data = new LeggiDati(stm).data;
         
        //Lettura dati da database (tabella processo_ori)
        data = FiltraDati(data);

        //costruzione e salvataggio script (tabella processo_ori, prodotto_ori, componente_ori)
        salvaScript(CostruisciScript(data));

    }

    //Verifica codici sacco ed estrazione informazioni da condice componente 
    public static ArrayList<Data> FiltraDati(ArrayList<Data> data) {

        boolean correggi_sacchi = (JOptionPane.showConfirmDialog(null, APP_M20, APP_T0, 0)) == 0;

        for (int i = 0; i < data.size(); i++) {

            ////////////////////////////////////////////////////////////
            // INDIVIDUAZIONE CODICI SACCO FORMALMENTE NON CORRETTI  ///
            ////////////////////////////////////////////////////////////
            if (correggi_sacchi) {

                //Codice Sacco
                while (!verificaCodice(data.get(i).getCod_sacco(), data.get(i).getCod_prodotto())) {

                    String nuovoCodice = JOptionPane.showInputDialog(null, "Processo id= " + data.get(i).getId_processo() + "\nCodice Errato =" + data.get(i).getCod_sacco() + "\n\n Inserire codice", "ERRORE: CODICE NON VALIDO", 2);

                    if (nuovoCodice != null && !"".equals(nuovoCodice)) {
                        data.get(i).setCod_sacco(nuovoCodice);
                    }
                }
            }
            /////////////////////////////////////
            // SCORPORAZIONE DATI COMPONENTI  ///
            /////////////////////////////////////
            //Componente
            String cod_comp_peso = data.get(i).getCod_comp_peso();
            ArrayList<Integer> dati_componenti;

            while (cod_comp_peso.length() > 0) {

                if (cod_comp_peso.contains(".")) {

                    String temp = cod_comp_peso.substring(0, cod_comp_peso.indexOf("."));
                    cod_comp_peso = cod_comp_peso.substring(cod_comp_peso.indexOf(".") + 1, cod_comp_peso.length());
                    dati_componenti = EstraiDatiComponenti(temp);

                } else {

                    dati_componenti = EstraiDatiComponenti(cod_comp_peso);
                    cod_comp_peso = "";

                }

                if (dati_componenti.size() > 0) {
                    DatiComponenti dati_comp = new DatiComponenti();
                    dati_comp.id_componente = dati_componenti.get(0);
                    dati_comp.peso_reale = dati_componenti.get(1);
                    dati_comp.peso_teorico = dati_componenti.get(2);
                    data.get(i).dati_componenti.add(dati_comp);
                    data.get(i).peso_totale_reale = data.get(i).peso_totale_reale + dati_componenti.get(1);

                }
            }
        }

        return data;

    }

    //Verifica Codice sacco / codice chimica
    public static Boolean verificaCodice(String codice, String codice_prodotto) {

        boolean res = true;
        if (codice.length() < 20) {
            res = false;
        } else if (!codice.contains(".")) {
            res = false;
        } else if (!codice.startsWith("L")) {
            res = false;
        } else if (!codice.contains(codice_prodotto)) {
            res = false;
        }

        return res;
    }

    //Estrazione dati componenti
    public static ArrayList<Integer> EstraiDatiComponenti(String cod_comp_peso) {

        ArrayList<Integer> data_comp = new ArrayList();
        String temp = "";
        int i = 0;
        if (!cod_comp_peso.equals("COD_FORMULA_SVUOTA")) {
            try {
                while (i < cod_comp_peso.length()) {

                    if (cod_comp_peso.charAt(i) == '_') {
                        data_comp.add(Integer.parseInt(temp));
                        temp = "";
                    } else {
                        temp += cod_comp_peso.charAt(i);
                    }
                    i++;

                }
                data_comp.add(Integer.parseInt(temp));
            } catch (NumberFormatException e) {

                System.err.println("Errore " + e);
            }
        }

        return data_comp;

    }

    //Costruzione Script
    public static ArrayList<String> CostruisciScript(ArrayList<Data> data) throws SQLException {

        ArrayList<String> script_row = new ArrayList<>();
 
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        default_date = simpleDateFormat.format(calendar.getTime());

        try {

            boolean associa_bolla = (JOptionPane.showConfirmDialog(null, APP_M0, APP_T0, 0)) == 0;

            if (associa_bolla) {
                id_bolla = JOptionPane.showInputDialog(APP_M1, 0);
                data_bolla = JOptionPane.showInputDialog(APP_M2, default_date);
                num_bolla = JOptionPane.showInputDialog(APP_M3, 0);

            }

            id_miscela = Integer.parseInt(JOptionPane.showInputDialog(APP_M6, 0));

            for (int i = 0; i < data.size(); i++) {

                ////////////////////////
                // DATI IN INGRESSO  ///
                ////////////////////////
                if (!cod_comp_prec.equals(data.get(i).getCod_prodotto())) {

                    cod_comp_prec = data.get(i).getCod_prodotto();

                    //NOME PRODOTTO 
                    ResultSet rs1 = stm.executeQuery("SELECT * FROM prodotto_ori WHERE cod_prodotto='" + data.get(i).getCod_prodotto() + "'");

                    while (rs1.next()) {

                        id_prodotto = rs1.getInt("id_prodotto");
                        nome_prodotto = rs1.getString("nome_prodotto");

                    }

                    num_sac_in_lotto = Integer.parseInt(JOptionPane.showInputDialog(APP_M8, 0));
                    num_lotti = Integer.parseInt(JOptionPane.showInputDialog(APP_M9, 0));
                    qta_sac = Integer.parseInt(JOptionPane.showInputDialog(APP_M10, 0));
                    qta_lotto = Integer.parseInt(JOptionPane.showInputDialog(APP_M11, 0));
                    qta_tot_miscela = qta_lotto * num_lotti;
                    id_utente = Integer.parseInt(JOptionPane.showInputDialog(APP_M12, 56));
                    id_azienda = Integer.parseInt(JOptionPane.showInputDialog(APP_M13, 1));
                    data_lotto = JOptionPane.showInputDialog(APP_M14, default_date);

                    for (int k = 0; k < data.get(i).dati_componenti.size(); k++) {

                        //NOME COMPONENTE
                        ResultSet rs2 = stm.executeQuery("SELECT * FROM componente_ori WHERE id_comp='" + data.get(i).dati_componenti.get(k).id_componente + "'");
                        String nome_comp = "";
                        while (rs2.next()) {

                            nome_comp = rs2.getString("descri_componente");

                        }

                        String cod_mov = JOptionPane.showInputDialog(APP_M15 + " :\n" + nome_comp, ".");
                        String prezzo = JOptionPane.showInputDialog(APP_M16 + " :\n" + nome_comp, 0.0);
                        String fornitore = JOptionPane.showInputDialog(APP_M17 + " :\n" + nome_comp, "");
                        String codice_artico_fornitore = JOptionPane.showInputDialog(APP_M18 + " :\n" + nome_comp, 0);
                        String data_arrivo_merce = JOptionPane.showInputDialog(APP_M19 + " :\n" + nome_comp, default_date);

                        for (int l = i; l < data.size(); l++) {

                            if (data.get(l).dati_componenti.size() > 0) {
                                data.get(l).dati_componenti.get(k).codice_movimento = cod_mov;
                                data.get(l).dati_componenti.get(k).prezzo = prezzo;
                                data.get(l).dati_componenti.get(k).fornitore = fornitore;
                                data.get(l).dati_componenti.get(k).codice_artico_fornitore = codice_artico_fornitore;
                                data.get(l).dati_componenti.get(k).data_arrivo_merce = data_arrivo_merce;
                            }
                        }
                    }

                }

                /////////////////////////////
                // TABELLA mov_magazzino  ///
                /////////////////////////////
                for (int j = 0; j < data.get(i).dati_componenti.size(); j++) {
                    String mov_magazzino = "UPDATE mov_magazzino SET stato = stato + 1  "
                            + "WHERE cod_mov='" + data.get(i).dati_componenti.get(j).codice_movimento + "';";

                    script_row.add(mov_magazzino);
                }

                ///////////////////////
                // TABELLA miscela  ///
                ///////////////////////
                String tab_miscela = "INSERT INTO miscela ("
                        + "id_miscela,"
                        + "cod_formula,"
                        + "cod_contenitore,"
                        + "dt_miscela,"
                        + "peso_reale,"
                        + "dt_inizio_formulazione,"
                        + "dt_fine_formulazione,"
                        + "operatore,"
                        + "dt_inizio_miscelazione,"
                        + "dt_fine_miscelazione,"
                        + "dt_inizio_confezionamento,"
                        + "dt_fine_confezionamento,"
                        + "dt_abilitato,"
                        + "num_sac_in_lotto,"
                        + "num_lotti,"
                        + "qta_sac,"
                        + "qta_lotto,"
                        + "qta_tot_miscela,"
                        + "id_utente,"
                        + "id_azienda) "
                        + "VALUES ("
                        + Integer.toString(id_miscela + i) + "," //id_miscela
                        + "'K" + data.get(i).getCod_prodotto() + "','" //cod_formula
                        + NUMERO_CONTENITORE + "','" //cod_contenitore
                        + data.get(i).getDt_produzione() + "','" //dt_miscela
                        + data.get(i).peso_totale_reale + "','" //peso_reale
                        + data.get(i).getDt_produzione() + "','" //dt_inizio_formulazione',"
                        + data.get(i).getDt_produzione() + "'," //dr_fine_formulazione
                        + data.get(i).cod_operatore + ",'" //operatore',"
                        + data.get(i).getDt_produzione() + "','" //dt_inizio_miscelazione',"
                        + data.get(i).getDt_produzione() + "','" //dt_fine_miscelazione',"
                        + data.get(i).getDt_produzione() + "','" //dt_inizio_confezionamento',"
                        + data.get(i).getDt_produzione() + "','" //dt_fine_confezionamento',"
                        + data.get(i).getDt_produzione() + "'," //dt_abilitato` timestamp',"
                        + num_sac_in_lotto + ","
                        + num_lotti + ","
                        + qta_sac + ","
                        + qta_lotto + ","
                        + qta_tot_miscela + ","
                        + id_utente + ","
                        + id_azienda + ");";

                script_row.add(tab_miscela);

                //////////////////////////////////
                // TABELLA miscela_componente  ///
                //////////////////////////////////
                for (int j = 0; j < data.get(i).dati_componenti.size(); j++) {
                    String tab_miscela_componente = "INSERT INTO miscela_componente ("
                            + "cod_mov,"
                            + "id_miscela,"
                            + "peso_reale_mat,"
                            + "peso_reale_mat_kit,"
                            + "dt_produzione)"
                            + "VALUES ('"
                            + data.get(i).dati_componenti.get(j).codice_movimento + "','" //cod_mov
                            + Integer.toString(id_miscela + i) + "','" //id_miscela
                            + data.get(i).dati_componenti.get(j).peso_reale + "','" //peso_reale_mat
                            + data.get(i).dati_componenti.get(j).peso_reale / num_sac_in_lotto + "','" //peso_reale_mat_kit
                            + data.get(i).getDt_produzione() + "');";                                   //dt_produzione

                    script_row.add(tab_miscela_componente);

                }

                ///////////////////////////
                // TABELLA gaz_mov_mag  ///
                ///////////////////////////
                for (int j = 0; j < data.get(i).dati_componenti.size(); j++) {

                    String gaz_mov_mag = "INSERT INTO gaz_movmag ("
                            + "dt_mov,"
                            + "operazione,"
                            + "causale,"
                            + "tip_doc,"
                            + "num_doc,"
                            + "protocollo,"
                            + "des_doc,"
                            + "dt_doc,"
                            + "clfoco,"
                            + "artico,"
                            + "descri_artico,"
                            + "quanti,"
                            + "prezzo,"
                            + "cat_mer,"
                            + "fornitore,"
                            + "cod_artico_fornitore,"
                            + "uni_mis,"
                            + "dt_arrivo_merce,"
                            + "valutazione_merce,"
                            + "verifica_stabilita,"
                            + "procedura_adottata,"
                            + "operatore,"
                            + "resp_produzione,"
                            + "resp_qualita,"
                            + "consulente_tecnico,"
                            + "note,"
                            + "doc_link,"
                            + "num_ordine,"
                            + "dt_ordine)"  
                            + "VALUES ('"
                            + data.get(i).getDt_produzione() + "','" //dt_mov
                            + GAZ_MOV_MAG_OPERAZIONE + "','" //operazione
                            + GAZ_MOV_MAG_CAUSALE + "','" //causale
                            + GAZ_MOV_MAG_TIPO_DOC + "','" //tip_doc
                            + Integer.toString(id_miscela + i) + "','" //num_doc
                            + GAZ_MOV_MAG_PROTOCOLLO + "','" //protocollo
                            + GAZ_MOV_MAG_DES_DOC + "','" //des_doc
                            + data.get(i).getDt_produzione() + "','" //dt_doc
                            + GAZ_MOV_MAG_CFLOCO + "','" //clfoco
                            + data.get(i).dati_componenti.get(j).codice_movimento.substring(
                                    0, data.get(i).dati_componenti.get(j).codice_movimento.indexOf(".")) + "','" //artico
                            + GAZ_MOV_MAG_DESCRI_ARTICO + " " + nome_prodotto + "','" //descri_artico
                            + data.get(i).getPeso_reale_sacco() + "','" //quanti
                            + data.get(i).dati_componenti.get(j).prezzo + "','" //prezzo
                            + GAZ_MOV_MAG_CAT_MER + "','" //cat_mer
                            + data.get(i).dati_componenti.get(j).fornitore + "','" //fornitore
                            + data.get(i).dati_componenti.get(j).codice_artico_fornitore + "','" //codice_artico_fornitore
                            + GAZ_MOV_MAG_UNITA_MISURA + "','" //uni_mis
                            + data.get(i).dati_componenti.get(j).data_arrivo_merce + "','" //dt_arrivo_merce
                            + GAZ_MOV_MAG_VALUTAZIONE_MERCE + "','" //valutazione_merce
                            + GAZ_MOV_MAG_VERIFICA_STABILITA + "','" //verifica_stabilita
                            + GAZ_MOV_MAG_PROCEDURA_ADOTTATA + "','" //procedura_adottata
                            + GAZ_MOV_MAG_OPERATORE + "','" //operatore
                            + GAZ_MOV_MAG_RESPONSABILE_PRODUZIONE + "','" //resp_produzione
                            + GAZ_MOV_MAG_RESPONSABILE_QUALITA + "','" //resp_qualita
                            + GAZ_MOV_MAG_CONSULENTE_TECNICO + "','" //consulente_tecnico
                            + GAZ_MOV_MAG_NOTE + "','" //note
                            + GAZ_MOV_MAG_DOC_LINK + "','" //doc_link
                            + GAZ_MOV_MAG_NUMERO_ORDINE + "','" //num_ordine
                            + GAZ_MOV_MAG_DT_ORDINE + "');";//dt_ordine

                    script_row.add(gaz_mov_mag);
                }

                /////////////////////////////////
                // TABELLA sacchetto_chimica  ///
                /////////////////////////////////
                String sacchetto_chimica = "INSERT INTO sacchetto_chimica ("
                        + "cod_chimica,"
                        + "id_miscela)"
                        + "VALUES ('"
                        + data.get(i).getCod_sacco() + "','" //id_miscela
                        + Integer.toString(id_miscela + i) + "');";//num_doc

                script_row.add(sacchetto_chimica);

                if (associa_bolla) {

                    /////////////////////
                    // TABELLA lotto  ///
                    /////////////////////
                    String lotto = "INSERT INTO lotto ("
                            + "cod_lotto,"
                            + "descri_lotto,"
                            + "dt_lotto,"
                            + "parent,"
                            + "id_bolla,"
                            + "num_bolla,"
                            + "dt_bolla)"
                            + " VALUES ('"
                            + data.get(i).getCod_sacco() + "','" //cod_lotto
                            + LOTTO_DESCRI_LOTTO + " " + nome_prodotto + "','" //descri_lotto
                            + data_lotto + "','" //data_lotto
                            + LOTTO_PARENT + "','" //parent
                            + id_bolla + "','" //id_bolla
                            + num_bolla + "','" //num_bolla
                            + data_bolla + "');"; //data_bolla

                    script_row.add(lotto);

                } else {

                    /////////////////////
                    // TABELLA lotto  ///
                    /////////////////////
                    String lotto = "INSERT INTO lotto ("
                            + "cod_lotto,"
                            + "descri_lotto,"
                            + "dt_lotto,"
                            + "parent)"
                            + "VALUES ('"
                            + data.get(i).getCod_sacco() + "','" //cod_lotto
                            + LOTTO_DESCRI_LOTTO + " " + nome_prodotto + "','" //descri_lotto
                            + data_lotto + "','" //data_lotto
                            + LOTTO_PARENT + "');"; //parent

                    script_row.add(lotto);

                }

                ///////////////////////
                // TABELLA chimica  ///
                ///////////////////////
                String chimica = "INSERT INTO chimica ("
                        + "cod_chimica,"
                        + "descri_formula,"
                        + "data,"
                        + "cod_prodotto,"
                        + "cod_lotto)"
                        + "VALUES ('"
                        + data.get(i).getCod_sacco() + "','" //cod_miscela
                        + CHIMICA_DESCRI_FORMULA + " " + nome_prodotto + "','" //descri_formula 
                        + data.get(i).getDt_produzione() + "','" //data
                        + data.get(i).getCod_prodotto() + "','" //cod_prodotto
                        + data.get(i).getCod_sacco() + "');"; //cod_lotto

                script_row.add(chimica);

            }

        } catch (HeadlessException | NumberFormatException | SQLException e) {
                
            script_row.add("ERRORE DURANTE LA COSTRUZIONE DELLO SCRITP e:" + e);
        }

        return script_row;
    }

    //Salvataggio script
    public static void salvaScript(ArrayList<String> rows) {
       
      
        try {
            File file = new File(SCRIPT_PATH);
            FileWriter fw = new FileWriter(file);
            fw.write("use serverdb;");
            fw.write("\n"); 
            for (int i = 0; i < rows.size(); i++) { 
                fw.write(rows.get(i));
                fw.write("\n"); 
            }

            fw.flush();
            fw.close();
        } catch (IOException e) {
        }

    }

}
