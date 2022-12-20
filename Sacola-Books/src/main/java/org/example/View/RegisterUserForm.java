package org.example.View;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.example.Entities.SistemaDasTelas.FuncionarioSys;
import org.example.Entities.Usuarios.Endereco;
import org.example.Entities.Usuarios.Telefone;
import org.example.Entities.Usuarios.Usuario;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.MaskFormatter;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

public class RegisterUserForm extends JDialog {
    private JTextField tfNome;
    private JFormattedTextField tfCPF;
    private JTextField tfEmail;
    private JTextField tfUsuario;
    private JPasswordField pfSenha;
    private JPasswordField pfConfirmarSenha;
    private JFormattedTextField tfTelefone;
    private JButton registrarButton;
    private JButton cancelarButton;
    private JComboBox cbTipoDeUsuario;
    private JPanel mainPanel;
    private JTextField tfCidade;
    private JTextField tfBairro;
    private JTextField tfRua;
    private JTextField tfNumero;
    private JFormattedTextField tfCep;
    private JFormattedTextField tfDataDeNascimento;
    private JComboBox cbEstado;


    public RegisterUserForm(JFrame parent) {
        super(parent);
        dialogInit();
        formatarCampos();
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

        registrarButton.addActionListener(e -> {
            try {
                Registrar();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
        cancelarButton.addActionListener(e -> {
            dispose();
            GerenciarUsuario gerenciarUsuarios = new GerenciarUsuario(null);
            gerenciarUsuarios.setVisible(true);
        });
    }

    private void Registrar() throws ParseException {
        String nome = tfNome.getText();
        String cpf = tfCPF.getText();
        String email = tfEmail.getText();
        String usuario = tfUsuario.getText();
        String senha = new String(pfSenha.getPassword());
        String confirmarSenha = new String(pfConfirmarSenha.getPassword());
        String telefone = tfTelefone.getText();
        String tipoDeUsuario = Objects.requireNonNull(cbTipoDeUsuario.getSelectedItem()).toString();
        String estado = Objects.requireNonNull(cbEstado.getSelectedItem()).toString();
        String cidade = tfCidade.getText();
        String bairro = tfBairro.getText();
        String rua = tfRua.getText();
        String numero = tfNumero.getText();
        String cep = tfCep.getText();
        String dataDeNascimento = tfDataDeNascimento.getText();
        Date dataNascimentoFormatada = FuncionarioSys.InserirDataFormatada(dataDeNascimento);


        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || usuario.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty() || telefone.isEmpty() || tipoDeUsuario.isEmpty() || estado.isEmpty() || cidade.isEmpty() || bairro.isEmpty() || rua.isEmpty() || numero.isEmpty() || cep.isEmpty() || dataDeNascimento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!");
            return;
        }

        if (senha.length() < 6) {
            JOptionPane.showMessageDialog(this, "A senha deve ter no mínimo 6 caracteres!");
            return;
        }

        if (cpf.length() != 14) {
            JOptionPane.showMessageDialog(this, "O CPF deve ter 11 caracteres!");
            return;
        }

        if (telefone.length() != 15) {
            JOptionPane.showMessageDialog(this, "O telefone deve ter 11 caracteres!");
            return;
        }

        if (cep.length() != 9) {
            JOptionPane.showMessageDialog(this, "O CEP deve ter 8 caracteres!");
            return;
        }

        if (numero.length() > 5) {
            JOptionPane.showMessageDialog(this, "O número deve ter no máximo 5 caracteres!");
            return;
        }

        if (dataDeNascimento.length() != 10) {
            JOptionPane.showMessageDialog(this, "A data de nascimento deve ter 8 caracteres!");
            return;
        }

        if (dataDeNascimento.charAt(2) != '/' || dataDeNascimento.charAt(5) != '/') {
            JOptionPane.showMessageDialog(this, "A data de nascimento deve estar no formato dd/mm/aaaa");
        }

        Usuario ObjUsuario = new Usuario(null, nome, email, dataNascimentoFormatada, cpf, tipoDeUsuario);
        Endereco ObjEndereco = new Endereco(null, rua, numero, bairro, cidade, estado, cep);
        Telefone ObjTelefone = new Telefone(telefone);

        FuncionarioSys funcionarioSys = new FuncionarioSys();
        if (funcionarioSys.CadastrarUsuario(ObjUsuario, ObjEndereco, ObjTelefone, usuario, senha)) {
            JOptionPane.showMessageDialog(this, "Usuário registrado com sucesso!");

            dispose();
            GerenciarUsuario gerenciarUsuarios = new GerenciarUsuario(null);
            gerenciarUsuarios.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao registrar usuário!");
        }
    }

    private void formatarCampos() {
        tfNome.setDocument(new JTextFieldLimit(50));
        tfEmail.setDocument(new JTextFieldLimit(50));
        tfUsuario.setDocument(new JTextFieldLimit(50));
        pfSenha.setDocument(new JTextFieldLimit(50));
        pfConfirmarSenha.setDocument(new JTextFieldLimit(50));
        tfCidade.setDocument(new JTextFieldLimit(50));
        tfBairro.setDocument(new JTextFieldLimit(50));
        tfRua.setDocument(new JTextFieldLimit(50));
        tfNumero.setDocument(new JTextFieldLimit(5));


        try {
            tfNome.setText(tfNome.getText().toUpperCase());
            tfCidade.setText(tfCidade.getText().toUpperCase());
            tfBairro.setText(tfBairro.getText().toUpperCase());
            tfRua.setText(tfRua.getText().toUpperCase());
            tfNumero.setText(tfNumero.getText().toUpperCase());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao formatar campos!");
        }

        try {
            MaskFormatter maskCPF = new MaskFormatter("###.###.###-##");
            maskCPF.setPlaceholderCharacter('_');
            maskCPF.install(tfCPF);
            MaskFormatter maskTelefone = new MaskFormatter("(##) #####-####");
            maskTelefone.setPlaceholderCharacter('_');
            maskTelefone.install(tfTelefone);
            MaskFormatter maskCep = new MaskFormatter("#####-###");
            maskCep.setPlaceholderCharacter('_');
            maskCep.install(tfCep);
            MaskFormatter maskDataDeNascimento = new MaskFormatter("##/##/####");
            maskDataDeNascimento.setPlaceholderCharacter('_');
            maskDataDeNascimento.install(tfDataDeNascimento);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

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
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(28, 15, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Nome:");
        mainPanel.add(label1, new GridConstraints(5, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfNome = new JTextField();
        mainPanel.add(tfNome, new GridConstraints(5, 6, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("CPF:");
        mainPanel.add(label2, new GridConstraints(7, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfCPF = new JFormattedTextField();
        mainPanel.add(tfCPF, new GridConstraints(7, 6, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Email:");
        mainPanel.add(label3, new GridConstraints(11, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfEmail = new JTextField();
        mainPanel.add(tfEmail, new GridConstraints(11, 6, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Usuário:");
        mainPanel.add(label4, new GridConstraints(15, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfUsuario = new JTextField();
        mainPanel.add(tfUsuario, new GridConstraints(15, 6, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Senha:");
        mainPanel.add(label5, new GridConstraints(19, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pfSenha = new JPasswordField();
        mainPanel.add(pfSenha, new GridConstraints(19, 6, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Confirmar senha:");
        mainPanel.add(label6, new GridConstraints(21, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pfConfirmarSenha = new JPasswordField();
        mainPanel.add(pfConfirmarSenha, new GridConstraints(21, 6, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Telefone:");
        mainPanel.add(label7, new GridConstraints(13, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$(null, -1, 22, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setText("Cadastro de usuário");
        mainPanel.add(label8, new GridConstraints(2, 3, 1, 11, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Tipo de usuário:");
        mainPanel.add(label9, new GridConstraints(17, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbTipoDeUsuario = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Usuário Comum");
        defaultComboBoxModel1.addElement("Professor");
        defaultComboBoxModel1.addElement("Aluno");
        cbTipoDeUsuario.setModel(defaultComboBoxModel1);
        mainPanel.add(cbTipoDeUsuario, new GridConstraints(17, 6, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        mainPanel.add(spacer1, new GridConstraints(6, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        mainPanel.add(spacer2, new GridConstraints(8, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        mainPanel.add(spacer3, new GridConstraints(12, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        mainPanel.add(spacer4, new GridConstraints(16, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        mainPanel.add(spacer5, new GridConstraints(18, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        mainPanel.add(spacer6, new GridConstraints(20, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        mainPanel.add(spacer7, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        mainPanel.add(spacer8, new GridConstraints(22, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        mainPanel.add(spacer9, new GridConstraints(24, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        mainPanel.add(spacer10, new GridConstraints(3, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        mainPanel.add(spacer11, new GridConstraints(3, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        tfTelefone = new JFormattedTextField();
        mainPanel.add(tfTelefone, new GridConstraints(13, 6, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer12 = new Spacer();
        mainPanel.add(spacer12, new GridConstraints(14, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        Font label10Font = this.$$$getFont$$$(null, -1, 16, label10.getFont());
        if (label10Font != null) label10.setFont(label10Font);
        label10.setText("Informações pessoais:");
        mainPanel.add(label10, new GridConstraints(4, 5, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        Font label11Font = this.$$$getFont$$$(null, -1, 16, label11.getFont());
        if (label11Font != null) label11.setFont(label11Font);
        label11.setText("Endereço:");
        mainPanel.add(label11, new GridConstraints(4, 11, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        mainPanel.add(spacer13, new GridConstraints(3, 13, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        cancelarButton = new JButton();
        cancelarButton.setText("CANCELAR");
        mainPanel.add(cancelarButton, new GridConstraints(23, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        registrarButton = new JButton();
        registrarButton.setHorizontalAlignment(0);
        registrarButton.setText("REGISTRAR");
        mainPanel.add(registrarButton, new GridConstraints(23, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Data de Nascimento:");
        mainPanel.add(label12, new GridConstraints(9, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        mainPanel.add(spacer14, new GridConstraints(10, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tfDataDeNascimento = new JFormattedTextField();
        mainPanel.add(tfDataDeNascimento, new GridConstraints(9, 6, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer15 = new Spacer();
        mainPanel.add(spacer15, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("");
        mainPanel.add(label13, new GridConstraints(3, 14, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("");
        mainPanel.add(label14, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("");
        mainPanel.add(label15, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("");
        mainPanel.add(label16, new GridConstraints(25, 12, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer16 = new Spacer();
        mainPanel.add(spacer16, new GridConstraints(26, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("");
        mainPanel.add(label17, new GridConstraints(27, 12, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer17 = new Spacer();
        mainPanel.add(spacer17, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setText("");
        mainPanel.add(label18, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfCep = new JFormattedTextField();
        mainPanel.add(tfCep, new GridConstraints(5, 12, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label19 = new JLabel();
        label19.setText("Cep:");
        mainPanel.add(label19, new GridConstraints(5, 11, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label20 = new JLabel();
        label20.setText("Estado:");
        mainPanel.add(label20, new GridConstraints(15, 11, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbEstado = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Acre");
        defaultComboBoxModel2.addElement("Alagoas");
        defaultComboBoxModel2.addElement("Amapá");
        defaultComboBoxModel2.addElement("Amazonas");
        defaultComboBoxModel2.addElement("Bahia");
        defaultComboBoxModel2.addElement("Ceará");
        defaultComboBoxModel2.addElement("Distrito Federal");
        defaultComboBoxModel2.addElement("Espírito Santo");
        defaultComboBoxModel2.addElement("Goiás");
        defaultComboBoxModel2.addElement("Maranhão");
        defaultComboBoxModel2.addElement("Mato Grosso");
        defaultComboBoxModel2.addElement("Mato Grosso do Sul");
        defaultComboBoxModel2.addElement("Minas Gerais");
        defaultComboBoxModel2.addElement("Pará");
        defaultComboBoxModel2.addElement("Paraíba");
        defaultComboBoxModel2.addElement("Paraná");
        defaultComboBoxModel2.addElement("Pernambuco");
        defaultComboBoxModel2.addElement("Piauí");
        defaultComboBoxModel2.addElement("Rio de Janeiro");
        defaultComboBoxModel2.addElement("Rio Grande do Norte");
        defaultComboBoxModel2.addElement("Rio Grande do Sul");
        defaultComboBoxModel2.addElement("Rondônia");
        defaultComboBoxModel2.addElement("Roraima");
        defaultComboBoxModel2.addElement("Santa Catarina");
        defaultComboBoxModel2.addElement("São Paulo");
        defaultComboBoxModel2.addElement("Sergipe");
        defaultComboBoxModel2.addElement("Tocantins");
        cbEstado.setModel(defaultComboBoxModel2);
        mainPanel.add(cbEstado, new GridConstraints(15, 12, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label21 = new JLabel();
        label21.setText("Cidade:");
        mainPanel.add(label21, new GridConstraints(13, 11, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfCidade = new JTextField();
        mainPanel.add(tfCidade, new GridConstraints(13, 12, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label22 = new JLabel();
        label22.setText("Bairro:");
        mainPanel.add(label22, new GridConstraints(11, 11, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfBairro = new JTextField();
        mainPanel.add(tfBairro, new GridConstraints(11, 12, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfRua = new JTextField();
        mainPanel.add(tfRua, new GridConstraints(9, 12, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label23 = new JLabel();
        label23.setText("Rua:");
        mainPanel.add(label23, new GridConstraints(9, 11, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label24 = new JLabel();
        label24.setText("Número:");
        mainPanel.add(label24, new GridConstraints(7, 11, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfNumero = new JTextField();
        mainPanel.add(tfNumero, new GridConstraints(7, 12, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
