package br.nnpe.memoria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import br.nnpe.memoria.client.LinhaJogadorCliente;
import br.nnpe.memoria.server.LinhaServidor;

/*
 * Memoria.java
 *
 * Created on 10 de Outubro de 2003, 16:57
 */

/**
 * @author Paulo Sobreira
 */
public class MemoriaMain extends javax.swing.JFrame {

    /**
     * Creates new form Memoria
     */
    public MemoriaMain() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {// GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        painelCartas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setTitle("Memória");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        painelCartas.setLayout(new java.awt.GridLayout(4, 5));

        jPanel1.add(painelCartas, java.awt.BorderLayout.CENTER);

        jTextArea1.setColumns(15);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(6);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.SOUTH);

        jTextField1.setColumns(20);
        jPanel2.add(jTextField1);

        jButton1.setText("Enviar Msg");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.add(jButton1);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("Menu");
        jMenuItem3.setText("Conectar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });

        jMenu1.add(jMenuItem3);


        jMenuItem6.setText("Jogar Sozinho");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jogarSozinho();
            }
        });

        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Servidor");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenuItem2.setText("Iniciar Servidor");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });

        jMenu2.add(jMenuItem2);

        jMenuItem4.setText("Iniciar Jogo");
        jMenuItem4.setEnabled(false);
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });

        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Debug");
        jMenuItem1.setText("Teste Cartas");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });

        jMenuItem5.setText("Sobre");
        jMenuItem5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String msg = "Feito por Paulo Sobreira \n" + "sowbreira@gmail.com \n"
                        + "https://sowbreira-26fe1.firebaseapp.com/\n Outubro de 2003 Maio de 2023";
                JOptionPane.showMessageDialog(MemoriaMain.this, msg, "Sobre", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        jMenu3.add(jMenuItem1);

        jMenu3.add(jMenuItem5);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// GEN-END:initComponents

    private void jogarSozinho() {
        try {
            servidor = new LinhaServidor(8080, jTextArea1);
            servidor.start();
            jog = new LinhaJogadorCliente(
                    new java.net.Socket(java.net.InetAddress.getByName("localhost"), 8080), jTextArea1);
            jog.setNome(javax.swing.JOptionPane.showInputDialog(this, "Entre com Seu Nome"));
            jog.setPainel(painelCartas);
            jog.setMainFrame(this);
            jog.start();
            servidor.setJogoIniciado(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem4ActionPerformed
        // Add your handling code here:
        servidor.setJogoIniciado(true);
    }// GEN-LAST:event_jMenuItem4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        // Add your handling code here:
        jog.enviaMsg(jTextField1.getText());
    }// GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem3ActionPerformed
        // Add your handling code here:
        if (jog != null) {
            JOptionPane.showMessageDialog(MemoriaMain.this, "Abra outra instância para outro jogador na mesma maquina",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            String ip = javax.swing.JOptionPane.showInputDialog(this, "Entre com IP do Servidor");
            String porta = javax.swing.JOptionPane.showInputDialog(this, "Entre com o Número da Porta do Servidor");
            jog = new LinhaJogadorCliente(
                    new java.net.Socket(java.net.InetAddress.getByName(ip), Integer.parseInt(porta)), jTextArea1);
            jog.setNome(javax.swing.JOptionPane.showInputDialog(this, "Entre com Seu Nome"));
            jog.setPainel(painelCartas);
            jog.setMainFrame(this);
            jog.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }// GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem2ActionPerformed
        // Add your handling code here:
        int porta = 0;
        try {
            porta = Integer
                    .parseInt(javax.swing.JOptionPane.showInputDialog(this, "Entre com o Número da Porta do Servidor"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        jMenuItem4.setEnabled(true);
        servidor = new LinhaServidor(porta, jTextArea1);
        servidor.start();
        jMenuItem2.setEnabled(false);
    }// GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenu2ActionPerformed
        // Add your handling code here:
    }// GEN-LAST:event_jMenu2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem1ActionPerformed
        // Add your handling code here:
        painelCartas.removeAll();
        java.util.ArrayList list = new java.util.ArrayList();
        for (int c = 0; c < 10; c++) {
            Carta par1 = new Carta(c);
            Carta par2 = new Carta(c);
            list.add(par1);
            list.add(par2);
        }
        java.util.Collections.shuffle(list);
        java.util.Iterator it = list.iterator();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                final Carta c = (Carta) it.next();
                c.setLinha(i);
                c.setColuna(j);
                c.getLabel().addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        c.setCoberta(!c.isCoberta());
                        painelCartas.revalidate();
                    }
                });
                painelCartas.add(c.getLabel());

            }
        }

        this.pack();

    }// GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_exitForm
        System.exit(0);
    }// GEN-LAST:event_exitForm

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new MemoriaMain().show();
    }

    private LinhaServidor servidor;
    private LinhaJogadorCliente jog;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel painelCartas;
    // End of variables declaration//GEN-END:variables

}