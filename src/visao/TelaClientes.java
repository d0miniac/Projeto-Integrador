package visao;

import modelo.Clientes;
import modelo.Funcionario;
import modelo.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.*;
import java.util.regex.Pattern;
import net.miginfocom.swing.MigLayout;

public class TelaClientes extends JFrame {

    private JTextField txtFiltro;
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;
    private TableRowSorter<DefaultTableModel> sorter;
   // private List<Clientes> listaClientes = new ArrayList<>();

    private Produto prod;
    private Funcionario func;
    private String mensagem;

    public TelaClientes(Produto prod, Funcionario func, String mensagem) {
        this.prod = prod;
        this.func = func;
        this.mensagem = mensagem;

        setTitle("Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1215, 850);
        setLocationRelativeTo(null);

        // carrega dados

        // fundo
        ImagePanel painelFundo = new ImagePanel("/img/Fundo.png");
        painelFundo.setLayout(new BorderLayout());
        setContentPane(painelFundo);

        // topo igual TelaProdutos
        JPanel topPanel = new JPanel(new MigLayout(
            "", "[87px][][grow][160px]", "[grow]"));
        topPanel.setBackground(new Color(33,64,154));
        topPanel.setPreferredSize(new Dimension(0,100));

        JLabel lblUser = new JLabel(new ImageIcon(getClass().getResource("/img/icone.png")));
        topPanel.add(lblUser, "cell 3 0,alignx right,aligny center");

        JLabel lblTitulo = new JLabel("Clientes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo, "cell 1 0 2 1,alignx center,aligny center");

        JLabel lblVoltar = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/de-volta.png"))
                .getImage().getScaledInstance(70,70,Image.SCALE_SMOOTH)
        ));
        lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblVoltar.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                dispose();
                new TelaMenu(prod, func, mensagem)
                    .setVisible(true);
            }
        });
        topPanel.add(lblVoltar, "cell 0 0,alignx left,aligny center");

        painelFundo.add(topPanel, BorderLayout.NORTH);

        // corpo
        JPanel panel = new JPanel(new MigLayout("","[grow]","[60px][grow]"));
        panel.setOpaque(false);

        txtFiltro = new JTextField();
        txtFiltro.setUI(new HintTextFieldUI("Filtrar por nome...", true));
        txtFiltro.setFont(new Font("Tahoma",Font.PLAIN,18));
        txtFiltro.setBorder(BorderFactory.createLineBorder(new Color(123,150,212),2,true));
        txtFiltro.setPreferredSize(new Dimension(450,45));
        panel.add(txtFiltro,"cell 0 0,split 2");
        JButton btnPesquisa = new JButton("Pesquisar");
        styleButton(btnPesquisa);
        panel.add(btnPesquisa,"wrap");
        btnPesquisa.addActionListener(e -> aplicarFiltro());

        // tabela
        modeloTabela = new DefaultTableModel(new Object[]{"Nome","Email","Telefone"},0);
        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.setFont(new Font("Tahoma",Font.PLAIN,16));
        tabelaClientes.setBackground(new Color(123,150,212));
        tabelaClientes.setForeground(Color.WHITE);
        JTableHeader hd = tabelaClientes.getTableHeader();
        hd.setFont(new Font("Tahoma",Font.PLAIN,20));
        sorter = new TableRowSorter<>(modeloTabela);
        tabelaClientes.setRowSorter(sorter);
        panel.add(new JScrollPane(tabelaClientes),"cell 0 1,grow");

        painelFundo.add(panel, BorderLayout.CENTER);

        // botões de ação no mesmo estilo
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setOpaque(false);

        JButton btnAdd = new JButton("Cadastrar");
        styleButton(btnAdd);
        btnAdd.addActionListener(e -> {
            dispose();
            new TelaCadastroClientes(prod, func, mensagem).setVisible(true);
        });

        JButton btnEdit = new JButton("Alterar");
        styleButton(btnEdit);
        btnEdit.addActionListener(e -> {
            int row = tabelaClientes.getSelectedRow();
            if(row<0) {
                JOptionPane.showMessageDialog(this,"Selecione um cliente","Aviso",JOptionPane.WARNING_MESSAGE);
                return;
            }
            int modelRow = tabelaClientes.convertRowIndexToModel(row);
            Clientes c = listaClientes.get(modelRow);
            dispose();
            new TelaEditarClientes(prod, func, mensagem, c).setVisible(true);
        });

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        painelFundo.add(btnPanel, BorderLayout.SOUTH);

        // popula tabela
        atualizarTabela();

        setVisible(true);
    }

    private void styleButton(JButton b){
        b.setBackground(new Color(243,244,240));
        b.setFont(new Font("Tahoma",Font.PLAIN,24));
        b.setBorder(BorderFactory.createLineBorder(new Color(123,150,212),2,true));
    }

    private void aplicarFiltro(){
        String txt = txtFiltro.getText().trim();
        if(txt.isEmpty()) sorter.setRowFilter(null);
        else sorter.setRowFilter(RowFilter.regexFilter("(?i)"+Pattern.quote(txt),0));
    }

    private void atualizarTabela(){
        modeloTabela.setRowCount(0);
        for(Clientes c: listaClientes){
            modeloTabela.addRow(new Object[]{c.getNome(),c.getEmail(),c.getTelefone()});
        }
    }


    @Override
    public void dispose(){
        super.dispose();
    }

}