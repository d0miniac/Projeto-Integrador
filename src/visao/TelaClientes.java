package visao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import modelo.Clientes;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TelaClientes extends JFrame {
    private JTextField txtNome, txtEmail, txtTelefone;
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;
    private List<Clientes> listaClientes = new ArrayList<>();

    public TelaClientes() {
        setTitle("Gerenciar Clientes");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelCampos = new JPanel(new MigLayout("", "[][grow]", "[][][]"));
        painelCampos.setBorder(BorderFactory.createTitledBorder("Informações do Cliente"));

        painelCampos.add(new JLabel("Nome:"), "cell 0 0,alignx trailing");
        txtNome = new JTextField();
        painelCampos.add(txtNome, "cell 1 0,growx");

        painelCampos.add(new JLabel("Email:"), "cell 0 1,alignx trailing");
        txtEmail = new JTextField();
        painelCampos.add(txtEmail, "cell 1 1,growx");

        painelCampos.add(new JLabel("Telefone:"), "cell 0 2,alignx trailing");
        txtTelefone = new JTextField();
        painelCampos.add(txtTelefone, "cell 1 2,growx");

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

        modeloTabela = new DefaultTableModel(new Object[]{"Nome", "Email", "Telefone"}, 0);
        tabelaClientes = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);

        add(painelCampos, BorderLayout.NORTH);
        add(painelBotoes, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Ações dos botões
        btnAdicionar.addActionListener(e -> adicionarCliente());
        btnEditar.addActionListener(e -> editarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());

        tabelaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carregarClienteSelecionado();
            }
        });

        setVisible(true);
    }

    private void adicionarCliente() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();

        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Clientes cliente = new Clientes(nome, email, telefone);
        listaClientes.add(cliente);
        atualizarTabela();
        limparCampos();
    }

    private void editarCliente() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada >= 0) {
            Clientes cliente = listaClientes.get(linhaSelecionada);
            cliente.setNome(txtNome.getText());
            cliente.setEmail(txtEmail.getText());
            cliente.setTelefone(txtTelefone.getText());
            atualizarTabela();
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void excluirCliente() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada >= 0) {
            listaClientes.remove(linhaSelecionada);
            atualizarTabela();
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void carregarClienteSelecionado() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada >= 0) {
            Clientes cliente = listaClientes.get(linhaSelecionada);
            txtNome.setText(cliente.getNome());
            txtEmail.setText(cliente.getEmail());
            txtTelefone.setText(cliente.getTelefone());
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Clientes c : listaClientes) {
            modeloTabela.addRow(new Object[]{c.getNome(), c.getEmail(), c.getTelefone()});
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
    }
}