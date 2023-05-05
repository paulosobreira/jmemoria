package br.nnpe.memoria.client;
/*
 * JogadorCliente.java
 *
 * Created on 17 de Outubro de 2003, 15:54
 */

/**
 *
 * @author  sobreira
 */
public interface JogadorCliente {
    public void setPainel(javax.swing.JPanel painel);
    public void setNome(java.lang.String nome);
    public void setMainFrame(javax.swing.JFrame mainFrame);
    public void setSaidaTxt(javax.swing.JTextArea saidaTxt);
    public void jogadaCliente(int i,int j);
}
