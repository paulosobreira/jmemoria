package br.nnpe.memoria;
/*
 * Carta.java
 *
 * Created on 6 de Outubro de 2003, 15:33
 */

import java.util.Objects;

/**
 * @author sobreira
 */
public class Carta {

    private boolean coberta;
    private javax.swing.JLabel label;
    private int id;
    private int Linha;
    private int Coluna;
    private javax.swing.Icon ico, verso;

    private boolean par;

    /**
     * Creates a new instance of Carta
     */
    public Carta() {
        label = new javax.swing.JLabel();
        verso = new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/verso.gif")));
        label.setIcon(verso);
    }

    public Carta(int i) {
        id = i;
        label = new javax.swing.JLabel();
        ico = new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/img" + id + ".gif")));
        verso = new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/verso.gif")));
        label.setIcon(ico);
    }

    /**
     * Getter for property coberta.
     *
     * @return Value of property coberta.
     */
    public boolean isCoberta() {
        return coberta;
    }

    /**
     * Setter for property coberta.
     *
     * @param coberta New value of property coberta.
     */
    public void setCoberta(boolean coberta) {
        if (coberta)
            label.setIcon(verso);
        else if (label == null) {
            label = new javax.swing.JLabel();
            ico = new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/img" + id + ".gif")));
            label.setIcon(ico);
        } else
            label.setIcon(ico);

        this.coberta = coberta;
    }

    /**
     * Getter for property id.
     *
     * @return Value of property id.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for property id.
     *
     * @param id New value of property id.
     */
    public void setId(int id) {
        if (label == null) {
            label = new javax.swing.JLabel();
            ico = new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/img" + id + ".gif")));
            label.setIcon(ico);
        } else {
            ico = new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/img" + id + ".gif")));
            label.setIcon(ico);
        }
        this.id = id;
    }

    /**
     * Getter for property label.
     *
     * @return Value of property label.
     */
    public javax.swing.JLabel getLabel() {
        return label;
    }

    /**
     * Setter for property label.
     *
     * @param label New value of property label.
     */
    public void setLabel(javax.swing.JLabel label) {
        this.label = label;
    }

    /**
     * Getter for property Coluna.
     *
     * @return Value of property Coluna.
     */
    public int getColuna() {
        return Coluna;
    }

    /**
     * Setter for property Coluna.
     *
     * @param Coluna New value of property Coluna.
     */
    public void setColuna(int Coluna) {
        this.Coluna = Coluna;
    }

    /**
     * Getter for property Linha.
     *
     * @return Value of property Linha.
     */
    public int getLinha() {
        return Linha;
    }

    /**
     * Setter for property Linha.
     *
     * @param Linha New value of property Linha.
     */
    public void setLinha(int Linha) {
        this.Linha = Linha;
    }

    /**
     * Getter for property par.
     *
     * @return Value of property par.
     */
    public boolean isPar() {
        return par;
    }

    /**
     * Setter for property par.
     *
     * @param par New value of property par.
     */
    public void setPar(boolean par) {
        this.par = par;
    }

}
