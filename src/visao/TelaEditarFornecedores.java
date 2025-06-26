package visao;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import modelo.Fornecedor;
import modelo.Funcionario;
import modelo.Produto;
import controle.FornecedorDAO;
import javax.swing.SwingConstants;

public class TelaEditarFornecedores extends JFrame {

    private JPanel contentPane;
    private JTextField txtID;
    private JTextField txtNomeFornecedor;
    private JTextField txtNomeContato;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private static Fornecedor fornecedor;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	Produto prod = new Produto();
                Funcionario funcionario = new Funcionario(); 
                String mensagem = "Bem-vindo ao sistema!";
                TelaEditarFornecedores frame = new TelaEditarFornecedores(prod,fornecedor, funcionario, mensagem);
                frame.setVisible(true);
                frame.setSize(657, 425);
                frame.setLocationRelativeTo(null);
            } catch (Exception e) {

            }
        });
    }
    
    public TelaEditarFornecedores(Produto prod,Fornecedor fornecedor, Funcionario func, String mensagem) {
        this.fornecedor = fornecedor;
        setSize(657, 425);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //contentPane = new ImagePanel("src/img/bgEditarFornecedores.png");
        contentPane = new ImagePanel(getClass().getResource("/img/bgEditarFornecedores.png"));

        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[grow]", "[70px][][grow][][100px][150px][150px][100px]"));

        JPanel vazio = new JPanel();
        vazio.setOpaque(false);
        contentPane.add(vazio, "cell 0 0,alignx left,growy");

        JLabel lblVoltar = new JLabel("");
        lblVoltar.setHorizontalAlignment(SwingConstants.LEFT);
        lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ImageIcon seta = new ImageIcon(TelaEditarFornecedores.class.getResource("/img/de-volta.png"));
        Image voltar = seta.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        lblVoltar.setIcon(new ImageIcon(voltar));
        vazio.add(lblVoltar);
        lblVoltar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TelaFornecedores tela = new TelaFornecedores(prod,func, mensagem);
                dispose();
                tela.setVisible(true);
            }
        });

        JLabel lblTitulo = new JLabel("Alteração das informações do fornecedor");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(153, 162, 209));
        contentPane.add(lblTitulo, "cell 0 3");

        JPanel topo = new JPanel();
        topo.setBorder(new MatteBorder(0, 0, 5, 0, new Color(32, 60, 115, 124)));
        topo.setOpaque(false);
        contentPane.add(topo, "cell 0 5,grow");
        topo.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]", "[][][]"));

        JLabel lblID = new JLabel("ID Fornecedor");
        topo.add(lblID, "cell 0 2,alignx center");

        txtID = new JTextField(String.valueOf(fornecedor.getIdFornecedor()));
        txtID.setEnabled(false);
        topo.add(txtID, "cell 1 2,alignx center");
        txtID.setColumns(10);

        JLabel lblNomeFornecedor = new JLabel("Nome do Fornecedor");
        topo.add(lblNomeFornecedor, "cell 2 2,alignx center");

        txtNomeFornecedor = new JTextField(fornecedor.getNomeFornecedor());
        topo.add(txtNomeFornecedor, "cell 3 2,alignx center");
        txtNomeFornecedor.setColumns(10);

        JPanel topo_1 = new JPanel();
        topo_1.setOpaque(false);
        topo_1.setBorder(new MatteBorder(0, 0, 5, 0, new Color(32, 60, 115, 124)));
        contentPane.add(topo_1, "cell 0 6,grow");
        topo_1.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow]", "[][]"));

        JLabel lblNomeContato = new JLabel("Nome do Contato");
        topo_1.add(lblNomeContato, "cell 0 1,alignx center");

        txtNomeContato = new JTextField(fornecedor.getNomeCtt());
        topo_1.add(txtNomeContato, "cell 1 1,alignx center");
        txtNomeContato.setColumns(10);

        JLabel lblEmail = new JLabel("E-mail");
        topo_1.add(lblEmail, "flowx,cell 2 1,alignx center");

        txtEmail = new JTextField(fornecedor.getEmail());
        topo_1.add(txtEmail, "cell 3 1,alignx center");
        txtEmail.setColumns(10);

        JLabel lblTelefone = new JLabel("Telefone");
        topo_1.add(lblTelefone, "flowx,cell 4 1,alignx center");

        txtTelefone = new JTextField(fornecedor.getTelefone());
        topo_1.add(txtTelefone, "cell 5 1,alignx center");
        txtTelefone.setColumns(10);

        JPanel inferior = new JPanel();
        inferior.setOpaque(false);
        contentPane.add(inferior, "cell 0 7,grow");
        inferior.setLayout(new MigLayout("", "[100px][100px][grow][grow][]", "[][][][][][][]"));

        JButton btnAlterar = new JButton("Alterar");
        btnAlterar.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnAlterar.setBackground(new Color(32, 60, 115));
        btnAlterar.setForeground(Color.WHITE);
        btnAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (txtEmail.getText().isEmpty() || txtNomeFornecedor.getText().isEmpty() ||
                    txtNomeContato.getText().isEmpty() || txtTelefone.getText().isEmpty()) {
                    
                    TelaErro telaErro = new TelaErro("Preencha todos os campos obrigatórios!", 0);
                    telaErro.setVisible(true);
                } else {
                    fornecedor.setNomeFornecedor(txtNomeFornecedor.getText());
                    fornecedor.setNomeCtt(txtNomeContato.getText());
                    fornecedor.setEmail(txtEmail.getText());
                    fornecedor.setTelefone(txtTelefone.getText());

                    FornecedorDAO dao = new FornecedorDAO();
                    try {
                        dao.alterarFornecedor(fornecedor); 
                        TelaErro telaErro = new TelaErro("Fornecedor alterado com sucesso!", 3);  
                        TelaFornecedores tela = new TelaFornecedores(prod,func, mensagem);
                        dispose();
                        tela.setVisible(true);

                    } catch (Exception ex) {
                        TelaErro telaErro = new TelaErro("Erro ao alterar fornecedor!", 0);  
                    }
                }
            }
        });
        inferior.add(btnAlterar, "cell 0 4,growx");

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBackground(new Color(255, 0, 0));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaFornecedores tela = new TelaFornecedores(prod,func, mensagem);
                dispose();
                tela.setVisible(true);
            }
        });
    }
}
