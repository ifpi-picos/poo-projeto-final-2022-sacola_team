/*
 * Created by JFormDesigner on Tue Nov 22 10:34:05 BRT 2022
 */

package org.example.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author unknown
 */
public class GerenciarUsuarios extends JDialog {
    public GerenciarUsuarios(Window owner) {
        super(owner);
        initComponents();
    }

    private void btnVoltar(ActionEvent e) {
        FuncionarioForm funcionarioForm = new FuncionarioForm(null);
        this.dispose();
        funcionarioForm.setVisible(true);

    }









    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Armando Pereira de Sousa
        dialogPane = new JPanel();
        panel1 = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        btnCadastrar = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();
        btnVoltar = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (
            new javax. swing. border. EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion"
            , javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM
            , new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 )
            , java. awt. Color. red) ,dialogPane. getBorder( )) ); dialogPane. addPropertyChangeListener (
            new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e
            ) {if ("bord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( )
            ; }} );
            dialogPane.setLayout(new BorderLayout());

            //======== panel1 ========
            {
                panel1.setLayout(new BorderLayout());

                //======== contentPanel ========
                {

                    //---- label1 ----
                    label1.setText("Usu\u00e1rios");
                    label1.setFont(new Font("JetBrains Mono", label1.getFont().getStyle(), 18));

                    //---- btnCadastrar ----
                    btnCadastrar.setText("CADASTRAR USU\u00c1RIO");
                    btnCadastrar.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));

                    //---- button2 ----
                    button2.setText("ALTERAR USU\u00c1RIO");
                    button2.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));

                    //---- button3 ----
                    button3.setText("REMOVER USU\u00c1RIO");
                    button3.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));

                    //---- button4 ----
                    button4.setText("LISTAR USU\u00c1RIO");
                    button4.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));

                    //---- btnVoltar ----
                    btnVoltar.setText("VOLTAR");
                    btnVoltar.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
                    btnVoltar.addActionListener(e -> btnVoltar(e));

                    GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                    contentPanel.setLayout(contentPanelLayout);
                    contentPanelLayout.setHorizontalGroup(
                        contentPanelLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                                .addGap(0, 199, Short.MAX_VALUE)
                                .addComponent(btnVoltar)
                                .addGap(195, 195, 195))
                            .addGroup(contentPanelLayout.createSequentialGroup()
                                .addGroup(contentPanelLayout.createParallelGroup()
                                    .addGroup(contentPanelLayout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(button3)
                                            .addComponent(btnCadastrar))
                                        .addGap(29, 29, 29)
                                        .addGroup(contentPanelLayout.createParallelGroup()
                                            .addComponent(button2)
                                            .addComponent(button4)))
                                    .addGroup(contentPanelLayout.createSequentialGroup()
                                        .addGap(208, 208, 208)
                                        .addComponent(label1)))
                                .addContainerGap(25, Short.MAX_VALUE))
                    );
                    contentPanelLayout.setVerticalGroup(
                        contentPanelLayout.createParallelGroup()
                            .addGroup(contentPanelLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(label1)
                                .addGap(86, 86, 86)
                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnCadastrar)
                                    .addComponent(button2))
                                .addGap(68, 68, 68)
                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(button3)
                                    .addComponent(button4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(106, 106, 106)
                                .addComponent(btnVoltar)
                                .addContainerGap(165, Short.MAX_VALUE))
                    );
                }
                panel1.add(contentPanel, BorderLayout.CENTER);
            }
            dialogPane.add(panel1, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Armando Pereira de Sousa
    private JPanel dialogPane;
    private JPanel panel1;
    private JPanel contentPanel;
    private JLabel label1;
    private JButton btnCadastrar;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton btnVoltar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
