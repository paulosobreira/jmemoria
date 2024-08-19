package br.nnpe.memoria.client;

import br.nnpe.memoria.Carta;

/*
 * LinhaJogadorCliente.java
 *
 * Created on 9 de Outubro de 2003, 21:25
 */

/**
 * @author Paulo Sobreira
 */
public class LinhaJogadorCliente extends LinhaJogador {
    private javax.swing.JPanel painel;
    private javax.swing.JFrame mainFrame;
    private java.util.Hashtable listaCarta;

    private String ip;

    public LinhaJogadorCliente(java.net.Socket con, javax.swing.JTextArea ta) {
        super(con);
        saidaTxt = ta;
        listaCarta = new java.util.Hashtable();
    }

    public void run() {
        try {
            while (!interrupted() && getCon().isConnected()) {
                precessaCmd(getIn().readUTF());
            }
            getOut().close();
            getIn().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void precessaCmd(java.lang.String cmd) {
        java.util.StringTokenizer tokens = new java.util.StringTokenizer(cmd, SEPARADOR);
        String t1 = tokens.nextToken();
        if (t1 == null)
            return;
        if (MSG.equals(t1))
            adicionarTxt(tokens.nextToken());
        else if (INICIAR_TABULERO.equals(t1))
            gerarPainel();
        else if (NAO_HE_SUA_VEZ.equals(t1))
            adicionarTxt("Não é sua vez aguarde... ");
        else if (DESVIRA.equals(t1)) {
            synchronized (listaCarta) {
                Carta c = (Carta) listaCarta.get(tokens.nextToken());
                c.setCoberta(true);
            }
        } else if (VIRA.equals(t1)) {
            synchronized (listaCarta) {
                Carta c = (Carta) listaCarta.get(tokens.nextToken());
                c.setId(Integer.parseInt(tokens.nextToken()));
                getPainel().revalidate();
                c.setCoberta(false);
            }
        } else if (BEEP.equals(t1)) {
            java.awt.Toolkit tk = getMainFrame().getToolkit();
            tk.beep();
        }

    }

    public void setNome(java.lang.String nome) {
        super.setNome(nome);
        try {
            getOut().writeUTF(NOME + SEPARADOR + nome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for property painel.
     *
     * @return Value of property painel.
     */
    public javax.swing.JPanel getPainel() {
        return painel;
    }

    /**
     * Setter for property painel.
     *
     * @param painel New value of property painel.
     */
    public void setPainel(javax.swing.JPanel painel) {
        this.painel = painel;
    }


    public void gerarPainel() {
        painel.removeAll();
        java.util.ArrayList list = new java.util.ArrayList();
        for (int c = 0; c < 20; c++) {
            Carta par1 = new Carta();
            list.add(par1);
        }
        java.util.Iterator it = list.iterator();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                final Carta c = (Carta) it.next();
                c.setLinha(i);
                c.setColuna(j);
                listaCarta.put(String.valueOf(i) + String.valueOf(j), c);
                final int _i = i;
                final int _j = j;
                c.getLabel().addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        jogadaCliente(_i, _j);
                    }
                });
                painel.add(c.getLabel());

            }
        }
        getMainFrame().pack();
    }

    public void jogadaCliente(int i, int j) {
        try {
            getOut().writeUTF(JOGAR + SEPARADOR + String.valueOf(i) + String.valueOf(j));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Getter for property mainFrame.
     *
     * @return Value of property mainFrame.
     */
    public javax.swing.JFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * Setter for property mainFrame.
     *
     * @param mainFrame New value of property mainFrame.
     */
    public void setMainFrame(javax.swing.JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * Getter for property ip.
     *
     * @return Value of property ip.
     */
    public java.lang.String getIp() {
        return ip;
    }

    /**
     * Setter for property ip.
     *
     * @param ip New value of property ip.
     */
    public void setIp(java.lang.String ip) {
        this.ip = ip;
    }

}
