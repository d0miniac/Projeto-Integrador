package visao;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;

import controle.FuncionarioDAO;
import modelo.Funcionario;
import modelo.FuncionarioTableModel;
import modelo.Produto;
import net.miginfocom.swing.MigLayout;

// ... [importações e pacote iguais]

public class TelaFuncionarios extends JFrame {

    private JPanel contentPane;
    private JTextField txtFiltro;
    private JTable table;
    private FuncionarioTableModel futm;
    private ArrayList<Funcionario> listarFuncionarios;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Produto prod = new Produto();
                Funcionario funcionario = new Funcionario();
                String mensagem = "Bem-vindo ao sistema!";
                TelaFuncionarios frame = new TelaFuncionarios(prod, funcionario, mensagem);
                frame.setVisible(true);
                frame.setSize(1190, 750);
                frame.setLocationRelativeTo(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaFuncionarios(Produto prod, Funcionario func, String mensagem) throws SQLException {
        setTitle("Funcionários");

        FuncionarioDAO fu = new FuncionarioDAO();
        listarFuncionarios = fu.selecionarFuncionarios();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new ImagePanel(getClass().getResource("/img/bgFuncionarios.png"));
        contentPane.setBackground(new Color(243, 244, 240));
        setSize(1215, 850);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[grow,fill]", "[120px][grow]"));

        JPanel panelVazio = new JPanel();
        panelVazio.setOpaque(false);
        contentPane.add(panelVazio, "cell 0 0,grow");
        panelVazio.setLayout(null);

        JPanel panelComponentes = new JPanel();
        panelComponentes.setOpaque(false);
        contentPane.add(panelComponentes, "cell 0 1,grow");
        panelComponentes.setLayout(new MigLayout("", "[][][][][grow][]", "[][][grow]"));

        txtFiltro = new JTextField();
        txtFiltro.setUI(new HintTextFieldUI("Pesquise por Nome Funcionario, CPF, Email, ou ID"));
        txtFiltro.setFont(new Font("Tahoma", Font.PLAIN, 18));
        txtFiltro.setBorder(new LineBorder(new Color(123, 150, 212), 2, true));
        panelComponentes.add(txtFiltro, "cell 4 0,alignx left");
        txtFiltro.setColumns(90);
        txtFiltro.setPreferredSize(new Dimension(450, 45));

        JButton btnAdd = new JButton("Adicionar");
        btnAdd.addActionListener(e -> {
            dispose();
            TelaCadastroFuncionario tela = new TelaCadastroFuncionario(prod, mensagem, func);
            tela.setVisible(true);
            tela.setLocationRelativeTo(null);
        });
        configurarBotao(btnAdd);
        panelComponentes.add(btnAdd, "cell 4 1,alignx left,growy");

        JButton btnUpdate = new JButton("Alterar");
        btnUpdate.addActionListener(e -> {
            int i = table.getSelectedRow();
            if (i != -1) {
                Funcionario funcionario = listarFuncionarios.get(i);
                TelaEditarFuncionario telaEditar = new TelaEditarFuncionario(prod, funcionario, func, mensagem);
                dispose();
                telaEditar.setVisible(true);
                telaEditar.setSize(657, 425);
                telaEditar.setLocationRelativeTo(null);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um funcionário para alterar.");
            }
        });
        configurarBotao(btnUpdate);
        panelComponentes.add(btnUpdate, "cell 4 1,alignx left,growy");

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(new LineBorder(new Color(123, 150, 212), 2, true));
        panelComponentes.add(scrollPane, "cell 4 2,grow");

        table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        table.setGridColor(Color.BLACK);
        table.setBackground(new Color(123, 150, 212));
        table.setForeground(Color.WHITE);
        scrollPane.setViewportView(table);

        theader();
        futm = new FuncionarioTableModel(listarFuncionarios);
        table.setModel(futm);

        JButton btnDelete = new JButton("Deletar");
        btnDelete.addActionListener(e -> {
            TelaErro telaErro = new TelaErro("Deseja realmente excluir este funcionário?");
            int resposta = telaErro.getResposta();
            if (resposta == 0) {
                int i = table.getSelectedRow();
                if (i == -1) {
                    new TelaErro("Selecione um funcionário para excluir!", 1);
                    return;
                }

            Object idObj = table.getModel().getValueAt(i, 0);
            Long id = null;
            if (idObj instanceof Integer) {
                id = ((Integer) idObj).longValue();
            } else if (idObj instanceof Long) {
                id = (Long) idObj;
            } else {
                new TelaErro("Erro ao excluir funcionário!", 0);
                return;
            }

            try {
                FuncionarioDAO dao = new FuncionarioDAO();
                boolean sucesso = dao.excluirFuncionario(id);  // ✅ Agora retorna boolean

                if (sucesso) {
                    listarFuncionarios = dao.selecionarFuncionarios();
                    futm.atualizarDados(listarFuncionarios);
                    JOptionPane.showMessageDialog(null, "Funcionário excluído com sucesso!");
                } else {
                    new TelaErro("Não foi possível excluir. Verifique se o ID está correto ou há restrição no banco.", 0);
                }

        } catch (SQLException e1) {
            e1.printStackTrace();
            new TelaErro("Erro ao excluir funcionário!", 0);
        }
    }
});

        configurarBotao(btnDelete);
        panelComponentes.add(btnDelete, "cell 4 1");

        JLabel lblSeta = new JLabel();
        lblSeta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSeta.setIcon(new ImageIcon(new ImageIcon(
                TelaCadastroProdutos.class.getResource("/img/de-volta.png"))
                .getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
        lblSeta.setBounds(0, 0, 110, 100);
        panelVazio.add(lblSeta);
        lblSeta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TelaMenu tela = new TelaMenu(prod, func, mensagem);
                dispose();
                tela.setVisible(true);
            }
        });

        JButton btnPesquisa = new JButton("PESQUISAR");
        btnPesquisa.addActionListener(e -> {
            FuncionarioDAO dao = new FuncionarioDAO();
            if (!txtFiltro.getText().trim().isEmpty()) {
                String filtro = txtFiltro.getText();
                listarFuncionarios = dao.pesquisarFuncionarios(filtro);
            } else {
                listarFuncionarios = dao.selecionarFuncionarios();
            }
            futm.atualizarDados(listarFuncionarios);
        });
        configurarBotao(btnPesquisa);
        panelComponentes.add(btnPesquisa, "cell 4 0,alignx center,aligny center");
    }

    private void configurarBotao(JButton botao) {
        botao.setBackground(new Color(243, 244, 240));
        botao.setFont(new Font("Tahoma", Font.PLAIN, 24));
        botao.setMinimumSize(new Dimension(150, 30));
        botao.setMaximumSize(new Dimension(150, 30));
        botao.setBorder(new LineBorder(new Color(123, 150, 212), 2, true));
    }

    private void theader() {
        JTableHeader thead = table.getTableHeader();
        thead.setForeground(new Color(123, 150, 212));
        thead.setBackground(Color.WHITE);
        thead.setFont(new Font("Tahoma", Font.PLAIN, 20));
    }
}