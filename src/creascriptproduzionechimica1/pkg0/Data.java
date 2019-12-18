/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creascriptproduzionechimica1.pkg0;

import java.util.ArrayList;

/**
 *
 * @author francescodigaudio
 */
    

public class Data { 
     
Integer id_processo;
String cod_prodotto;
String cod_chimica;
String cod_sacco;
Integer peso_reale_sacco;
String cod_comp_peso;
String cod_colore;
String cliente;
String dt_produzione;
String cod_operatore;
String cod_comp_in;
Integer tipo_processo;
String info1;
String info2;
String info3;
String info4;
String info5;
String info6;
String info7;
String info8;
String info9;
String info10;

ArrayList<DatiComponenti> dati_componenti = new ArrayList();
Integer peso_totale_reale = 0; 
    
    
    //  ArrayList<Integer> id_processo = new ArrayList<>();
//  ArrayList<String> cod_prodotto = new ArrayList<>();
//  ArrayList<String> cod_chimica = new ArrayList<>();
//  ArrayList<String> cod_sacco = new ArrayList<>();
//  ArrayList<Integer> peso_reale_sacco = new ArrayList<>();
//  ArrayList<String> cod_comp_peso = new ArrayList<>();
//  ArrayList<String> cod_colore = new ArrayList<>();
//  ArrayList<String> cliente = new ArrayList<>();
//  ArrayList<String> dt_produzione = new ArrayList<>();
//  ArrayList<String> cod_operatore = new ArrayList<>();
//  ArrayList<String> cod_comp_in = new ArrayList<>();
//  ArrayList<Integer> tipo_processo = new ArrayList<>();
//  ArrayList<String> info1 = new ArrayList<>();
//  ArrayList<String> info2 = new ArrayList<>();
//  ArrayList<String> info3 = new ArrayList<>();
//  ArrayList<String> info4 = new ArrayList<>();
//  ArrayList<String> info5 = new ArrayList<>();
//  ArrayList<String> info6 = new ArrayList<>();
//  ArrayList<String> info7 = new ArrayList<>();
//  ArrayList<String> info8 = new ArrayList<>();
//  ArrayList<String> info9 = new ArrayList<>();
//  ArrayList<String> info10 = new ArrayList<>();

    public Integer getId_processo() {
        return id_processo;
    }

    public void setId_processo(Integer id_processo) {
        this.id_processo = id_processo;
    }

    public String getCod_prodotto() {
        return cod_prodotto;
    }

    public void setCod_prodotto(String cod_prodotto) {
        this.cod_prodotto = cod_prodotto;
    }

    public String getCod_chimica() {
        return cod_chimica;
    }

    public void setCod_chimica(String cod_chimica) {
        this.cod_chimica = cod_chimica;
    }

    public String getCod_sacco() {
        return cod_sacco;
    }

    public void setCod_sacco(String cod_sacco) {
        this.cod_sacco = cod_sacco;
    }

    public Integer getPeso_reale_sacco() {
        return peso_reale_sacco;
    }

    public void setPeso_reale_sacco(Integer peso_reale_sacco) {
        this.peso_reale_sacco = peso_reale_sacco;
    }

    public String getCod_comp_peso() {
        return cod_comp_peso;
    }

    public void setCod_comp_peso(String cod_comp_peso) {
        this.cod_comp_peso = cod_comp_peso;
    }

    public String getCod_colore() {
        return cod_colore;
    }

    public void setCod_colore(String cod_colore) {
        this.cod_colore = cod_colore;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDt_produzione() {
        return dt_produzione;
    }

    public void setDt_produzione(String dt_produzione) {
        this.dt_produzione = dt_produzione;
    }

    public String getCod_operatore() {
        return cod_operatore;
    }

    public void setCod_operatore(String cod_operatore) {
        this.cod_operatore = cod_operatore;
    }

    public String getCod_comp_in() {
        return cod_comp_in;
    }

    public void setCod_comp_in(String cod_comp_in) {
        this.cod_comp_in = cod_comp_in;
    }

    public Integer getTipo_processo() {
        return tipo_processo;
    }

    public void setTipo_processo(Integer tipo_processo) {
        this.tipo_processo = tipo_processo;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getInfo4() {
        return info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }

    public String getInfo5() {
        return info5;
    }

    public void setInfo5(String info5) {
        this.info5 = info5;
    }

    public String getInfo6() {
        return info6;
    }

    public void setInfo6(String info6) {
        this.info6 = info6;
    }

    public String getInfo7() {
        return info7;
    }

    public void setInfo7(String info7) {
        this.info7 = info7;
    }

    public String getInfo8() {
        return info8;
    }

    public void setInfo8(String info8) {
        this.info8 = info8;
    }

    public String getInfo9() {
        return info9;
    }

    public void setInfo9(String info9) {
        this.info9 = info9;
    }

    public String getInfo10() {
        return info10;
    }

    public void setInfo10(String info10) {
        this.info10 = info10;
    }
    
    
}
