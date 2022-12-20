package org.example.View;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.example.Dao.UsuariosDAO.UsuarioDAO;
import org.example.Entities.SistemaDasTelas.FuncionarioSys;
import org.example.Entities.Usuarios.Endereco;
import org.example.Entities.Usuarios.Logins;
import org.example.Entities.Usuarios.Telefone;
import org.example.Entities.Usuarios.Usuario;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class AlterarUsuario extends JDialog {
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    private JLabel tfAlteracaoDeUsuario;
    private JTextField tfCep;
    private JTextField tfNome;
    private JButton btAlterar;
    private JButton btCancelar;
    private JTextField tfCPF;
    private JTextField tfDataDeNascimento;
    private JTextField tfEmail;
    private JTextField tfTelefone;
    private JTextField tfUsuario;
    private JComboBox cbTipoDeUsuario;
    private JPasswordField tfSenha;
    private JPasswordField tfConfirmarSenha;
    private JTextField tfNumero;
    private JTextField tfRua;
    private JTextField tfBairro;
    private JTextField tfCidade;
    private JComboBox cbEstado;
    private JPanel MainPanel;
    private Long idUsuario;

    public AlterarUsuario(JFrame parent) {
        super(parent, "Alterar Usuário", true);
        inicializarComponentes();
        this.setContentPane(MainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

        getRootPane().setDefaultButton(btAlterar);


        btAlterar.addActionListener(e -> {
            try {
                onAlterar();
            } catch (ParseException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        btCancelar.addActionListener(e -> onCancel());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });


        MainPanel.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void inicializarComponentes() {
        idUsuario = pegarIdUsuario();
        preencherCampos();
        converterDataSQLToString();
    }

    private void onAlterar() throws ParseException, SQLException {
        if (validarCampos()) {
            String nome = tfNome.getText();
            String cpf = tfCPF.getText();
            String dataDeNascimento = tfDataDeNascimento.getText();
            Date dataDeNascimentoSQL = FuncionarioSys.InserirDataFormatada(dataDeNascimento);
            String email = tfEmail.getText();
            String telefone = tfTelefone.getText();
            String usuario = tfUsuario.getText();
            String senha = String.valueOf(tfSenha.getPassword());
            String confirmarSenha = String.valueOf(tfConfirmarSenha.getPassword());
            String cep = tfCep.getText();
            String numero = tfNumero.getText();
            String rua = tfRua.getText();
            String bairro = tfBairro.getText();
            String cidade = tfCidade.getText();
            String estado = Objects.requireNonNull(cbEstado.getSelectedItem()).toString();
            String tipoDeUsuario = Objects.requireNonNull(cbTipoDeUsuario.getSelectedItem()).toString();

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

            Usuario ObjUsuario = new Usuario(null, nome, cpf, dataDeNascimentoSQL, email, tipoDeUsuario);
            Endereco ObjEndereco = new Endereco(null, rua, numero, bairro, cidade, estado, cep);
            Telefone ObjTelefone = new Telefone(telefone);

            usuarioDAO.alterarUsuario(ObjUsuario, ObjEndereco, ObjTelefone, idUsuario);
            usuarioDAO.updateLogin(idUsuario, usuario, senha);
        }

        dispose();
    }

    private boolean validarCampos() {
        if (tfNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo nome não pode estar vazio!");
            return false;
        }

        if (tfCPF.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo CPF não pode estar vazio!");
            return false;
        }

        if (tfDataDeNascimento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo data de nascimento não pode estar vazio!");
            return false;
        }

        if (tfEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo email não pode estar vazio!");
            return false;
        }

        if (tfTelefone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo telefone não pode estar vazio!");
            return false;
        }

        if (tfUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo usuário não pode estar vazio!");
            return false;
        }

        if (tfSenha.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "O campo senha não pode estar vazio!");
            return false;
        }

        if (tfConfirmarSenha.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "O campo confirmar senha não pode estar vazio!");
            return false;
        }

        if (tfCep.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo CEP não pode estar vazio!");
            return false;
        }

        if (tfRua.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo rua não pode estar vazio!");
            return false;
        }

        if (tfNumero.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo número não pode estar vazio!");
            return false;
        }

        if (tfBairro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo bairro não pode estar vazio!");
            return false;
        }

        if (tfCidade.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo cidade não pode estar vazio!");
            return false;
        }

        if (Objects.requireNonNull(cbEstado.getSelectedItem()).toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo estado não pode estar vazio!");
            return false;
        }

        return true;
    }

    private void onCancel() {
        dispose();
    }

    private Long pegarIdUsuario() {
        List<Usuario> ListaDeUsuarios = usuarioDAO.findAllUsuarios();
        StringBuilder usuarios = new StringBuilder();
        for (Usuario usuario : ListaDeUsuarios) {
            usuarios.append(usuario.getIdUsuario()).append(" - ").append(usuario.getNome()).append("\n");
        }
        return Long.parseLong(JOptionPane.showInputDialog("Digite o ID do usuário que deseja alterar: \n" + usuarios));
    }

    private void preencherCampos() {
        Optional<Usuario> usuario = usuarioDAO.findUsuarioById(idUsuario);
        if (usuario.isPresent()) {
            Usuario usuario1 = usuario.get();
            tfNome.setText(usuario1.getNome());
            tfCPF.setText(usuario1.getCpf());
            tfDataDeNascimento.setText(String.valueOf(usuario1.getDataDeNascimento()));
            tfEmail.setText(usuario1.getEmail());
            cbTipoDeUsuario.setSelectedItem(usuario1.getTipoDeUsuario());


            Optional<Endereco> endereco = usuarioDAO.FindEnderecoByIdUsuario(idUsuario);
            if (endereco.isPresent()) {
                Endereco endereco1 = endereco.get();
                tfCep.setText(endereco1.cep());
                tfRua.setText(endereco1.rua());
                tfNumero.setText(endereco1.numero());
                tfBairro.setText(endereco1.bairro());
                tfCidade.setText(endereco1.cidade());
                cbEstado.setSelectedItem(endereco1.estado());

                Optional<Telefone> telefone = usuarioDAO.FindTelefoneByIdUsuario(idUsuario);
                if (telefone.isPresent()) {
                    Telefone telefone1 = telefone.get();
                    tfTelefone.setText(telefone1.numero());

                    Optional<Logins> logins = usuarioDAO.FindLoginByIdUsuario(idUsuario);
                    if (logins.isPresent()) {
                        Logins logins1 = logins.get();
                        tfUsuario.setText(logins1.usuario());
                        tfSenha.setText(logins1.senha());
                        tfConfirmarSenha.setText(logins1.senha());
                    }
                }
            }
        }
    }

    private void converterDataSQLToString() {
        String data = tfDataDeNascimento.getText();
        String[] dataSeparada = data.split("-");
        String dataFormatada = dataSeparada[2] + "/" + dataSeparada[1] + "/" + dataSeparada[0];
        tfDataDeNascimento.setText(dataFormatada);
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
        MainPanel.setLayout(new GridLayoutManager(28, 10, new Insets(0, 0, 0, 0), -1, -1));
        tfAlteracaoDeUsuario = new JLabel();
        Font tfAlteracaoDeUsuarioFont = this.$$$getFont$$$(null, -1, 22, tfAlteracaoDeUsuario.getFont());
        if (tfAlteracaoDeUsuarioFont != null) tfAlteracaoDeUsuario.setFont(tfAlteracaoDeUsuarioFont);
        tfAlteracaoDeUsuario.setText("Alteração de usuário");
        MainPanel.add(tfAlteracaoDeUsuario, new GridConstraints(2, 4, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        MainPanel.add(spacer1, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("");
        MainPanel.add(label1, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        MainPanel.add(spacer2, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 16, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Informações Pessoais:");
        MainPanel.add(label2, new GridConstraints(4, 4, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, -1, 16, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Endereço");
        MainPanel.add(label3, new GridConstraints(4, 6, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Cep:");
        MainPanel.add(label4, new GridConstraints(5, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfCep = new JTextField();
        MainPanel.add(tfCep, new GridConstraints(5, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfNome = new JTextField();
        MainPanel.add(tfNome, new GridConstraints(5, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Nome:");
        MainPanel.add(label5, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        MainPanel.add(spacer3, new GridConstraints(3, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("");
        MainPanel.add(label6, new GridConstraints(3, 9, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        MainPanel.add(spacer4, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("");
        MainPanel.add(label7, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        MainPanel.add(spacer5, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("");
        MainPanel.add(label8, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        MainPanel.add(spacer6, new GridConstraints(6, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("CPF:");
        MainPanel.add(label9, new GridConstraints(7, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        MainPanel.add(spacer7, new GridConstraints(8, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Data de nascimento:");
        MainPanel.add(label10, new GridConstraints(9, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        MainPanel.add(spacer8, new GridConstraints(10, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Email:");
        MainPanel.add(label11, new GridConstraints(11, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        MainPanel.add(spacer9, new GridConstraints(12, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Telefone:");
        MainPanel.add(label12, new GridConstraints(13, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        MainPanel.add(spacer10, new GridConstraints(14, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("Usuário:");
        MainPanel.add(label13, new GridConstraints(15, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        MainPanel.add(spacer11, new GridConstraints(16, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("Tipo de usuário:");
        MainPanel.add(label14, new GridConstraints(17, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        MainPanel.add(spacer12, new GridConstraints(18, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("Senha:");
        MainPanel.add(label15, new GridConstraints(19, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        MainPanel.add(spacer13, new GridConstraints(20, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("Confirmar senha:");
        MainPanel.add(label16, new GridConstraints(21, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        MainPanel.add(spacer14, new GridConstraints(22, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        btAlterar = new JButton();
        btAlterar.setText("ALTERAR");
        MainPanel.add(btAlterar, new GridConstraints(23, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btCancelar = new JButton();
        btCancelar.setText("CANCELAR");
        MainPanel.add(btCancelar, new GridConstraints(23, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer15 = new Spacer();
        MainPanel.add(spacer15, new GridConstraints(24, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("");
        MainPanel.add(label17, new GridConstraints(25, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer16 = new Spacer();
        MainPanel.add(spacer16, new GridConstraints(26, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setText("");
        MainPanel.add(label18, new GridConstraints(27, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfCPF = new JTextField();
        MainPanel.add(tfCPF, new GridConstraints(7, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfDataDeNascimento = new JTextField();
        MainPanel.add(tfDataDeNascimento, new GridConstraints(9, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfEmail = new JTextField();
        MainPanel.add(tfEmail, new GridConstraints(11, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfTelefone = new JTextField();
        MainPanel.add(tfTelefone, new GridConstraints(13, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfUsuario = new JTextField();
        MainPanel.add(tfUsuario, new GridConstraints(15, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cbTipoDeUsuario = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Usuário Comum");
        defaultComboBoxModel1.addElement("Professor");
        defaultComboBoxModel1.addElement("Aluno");
        cbTipoDeUsuario.setModel(defaultComboBoxModel1);
        MainPanel.add(cbTipoDeUsuario, new GridConstraints(17, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfSenha = new JPasswordField();
        MainPanel.add(tfSenha, new GridConstraints(19, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfConfirmarSenha = new JPasswordField();
        MainPanel.add(tfConfirmarSenha, new GridConstraints(21, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfNumero = new JTextField();
        MainPanel.add(tfNumero, new GridConstraints(7, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfRua = new JTextField();
        MainPanel.add(tfRua, new GridConstraints(9, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfBairro = new JTextField();
        MainPanel.add(tfBairro, new GridConstraints(11, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfCidade = new JTextField();
        MainPanel.add(tfCidade, new GridConstraints(13, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cbEstado = new JComboBox();
        cbEstado.setEditable(false);
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
        MainPanel.add(cbEstado, new GridConstraints(15, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label19 = new JLabel();
        label19.setText("Número:");
        MainPanel.add(label19, new GridConstraints(7, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label20 = new JLabel();
        label20.setText("Rua:");
        MainPanel.add(label20, new GridConstraints(9, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label21 = new JLabel();
        label21.setText("Bairro:");
        MainPanel.add(label21, new GridConstraints(11, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label22 = new JLabel();
        label22.setText("Cidade:");
        MainPanel.add(label22, new GridConstraints(13, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label23 = new JLabel();
        label23.setText("Estado:");
        MainPanel.add(label23, new GridConstraints(15, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return MainPanel;
    }

}
