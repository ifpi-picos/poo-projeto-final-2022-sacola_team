package org.example.View;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.example.Dao.AcervoDAO.BibliotecaDAO;
import org.example.Entities.Acervo.Livro;
import org.example.Entities.SistemaDasTelas.FuncionarioSys;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Objects;
import java.util.Vector;

public class RegisterBookForm extends JDialog {
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JComboBox<String> comboBoxAreaDeConhecimento;
    private JFormattedTextField txtFormattedDataDePublicacao;
    private JPanel MainPanel;
    private JSpinner spinnerQuantidadeDeCopias;
    private JButton CADASTRARButton;
    private JButton CANCELARButton;

    public RegisterBookForm(JFrame parent) {
        super(parent, "RegisterBookForm", true);

        dialogInit();
        formatarCampos();
        preencherComboBox();


        this.setContentPane(MainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

        CADASTRARButton.addActionListener(e -> {
            try {
                cadastrarLivro();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    Vector<Long> idAreasDeConhecimento = new Vector<Long>();

    public void preencherComboBox() {
        try {
            ResultSet resultSet = new BibliotecaDAO().findAllAreasForComboBox();
            while (resultSet.next()) {
                comboBoxAreaDeConhecimento.addItem(resultSet.getString("titulo"));
                idAreasDeConhecimento.add(resultSet.getLong("idAreaDeConhecimento"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    private void cadastrarLivro() throws ParseException {
        BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();
        FuncionarioSys funcionarioSys = new FuncionarioSys();

        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String dataDePublicacao = txtFormattedDataDePublicacao.getText();
        int quantidadeDeCopias = (int) spinnerQuantidadeDeCopias.getValue();
        String areaDeConhecimento = Objects.requireNonNull(comboBoxAreaDeConhecimento.getSelectedItem()).toString();
        Long idAreaDeConhecimento = bibliotecaDAO.findIdAreaByTitulo(areaDeConhecimento);


        funcionarioSys.cadastrarLivro(titulo, autor, idAreaDeConhecimento, dataDePublicacao, quantidadeDeCopias);
    }


    private void formatarCampos() {
        try {
            MaskFormatter maskDataPublicacao = new MaskFormatter("##/##/####");
            maskDataPublicacao.setPlaceholderCharacter('_');
            maskDataPublicacao.install(txtFormattedDataDePublicacao);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        RegisterBookForm dialog = new RegisterBookForm(new JFrame());
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MainPanel = new JPanel();
        MainPanel.setLayout(new GridLayoutManager(8, 2, new Insets(20, 20, 20, 20), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Cadastrar Livro");
        MainPanel.add(label1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTitulo = new JTextField();
        MainPanel.add(txtTitulo, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtAutor = new JTextField();
        txtAutor.setText("");
        MainPanel.add(txtAutor, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        comboBoxAreaDeConhecimento = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        comboBoxAreaDeConhecimento.setModel(defaultComboBoxModel1);
        MainPanel.add(comboBoxAreaDeConhecimento, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtFormattedDataDePublicacao = new JFormattedTextField();
        MainPanel.add(txtFormattedDataDePublicacao, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        spinnerQuantidadeDeCopias = new JSpinner();
        MainPanel.add(spinnerQuantidadeDeCopias, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CADASTRARButton = new JButton();
        CADASTRARButton.setText("CADASTRAR");
        MainPanel.add(CADASTRARButton, new GridConstraints(6, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CANCELARButton = new JButton();
        CANCELARButton.setText("CANCELAR");
        MainPanel.add(CANCELARButton, new GridConstraints(7, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Título:");
        MainPanel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Autor:");
        MainPanel.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Area de conhecimento:");
        MainPanel.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Data de publicação:");
        MainPanel.add(label5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Quantidade de cópias:");
        MainPanel.add(label6, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainPanel;
    }

}
