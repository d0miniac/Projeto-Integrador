package visao;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;

import controle.FornecedorDAO;
import modelo.Fornecedor;
import modelo.Funcionario;
import modelo.Produto;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Font;

public class TelaCadastroFornecedores extends JFrame {

    private JPanel contentPane;
    private JTextField txtID;
    private JTextField textEMAIL;
    private JTextField textNOMECONTATO;
    private JTextField textNOME_FORNECEDOR; 
    private JTextField textTELEFONE;
    private static TelaFornecedores telaFornecedores;  

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	Produto prod= new Produto();
                Funcionario funcionario = new Funcionario(); 
                String mensagem = "Bem-vindo ao sistema!";
                TelaCadastroFornecedores frame = new TelaCadastroFornecedores(prod, telaFornecedores, funcionario, mensagem);
                frame.setVisible(true);
                frame.setSize(657, 425);
                frame.setLocationRelativeTo(null);
            } catch (Exception e) {

            }
        });
    }

    public TelaCadastroFornecedores(Produto prod,TelaFornecedores telaFornecedores, Funcionario func, String mensagem) {
        this.telaFornecedores = telaFornecedores;
        setResizable(false);
        setTitle("Cadastro de Fornecedores");
        contentPane = new ImagePanel("src/img/bgCadastroFornecedores.png");
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[grow]", "[70px][][grow][][100px][150px][150px][100px]"));

        JPanel vazio = new JPanel();
        FlowLayout flowLayout = (FlowLayout) vazio.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        vazio.setBackground(new Color(255, 0, 0));
        contentPane.add(vazio, "cell 0 0,grow");
        vazio.setOpaque(false);

        JLabel lblNewLabel_7 = new JLabel("");
        lblNewLabel_7.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_7.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ImageIcon seta = new ImageIcon(TelaCadastroFornecedores.class.getResource("/img/de-volta.png"));
        Image voltar = seta.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        lblNewLabel_7.setIcon(new ImageIcon(voltar));
        vazio.add(lblNewLabel_7);
        lblNewLabel_7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	TelaFornecedores telaFornecedores = new TelaFornecedores(prod,func, mensagem);
                dispose();
                telaFornecedores.setSize(1215, 850);
                telaFornecedores.setLocationRelativeTo(null);
                telaFornecedores.setVisible(true);
            }
        });

        JLabel lblNewLabel = new JLabel("Informações do fornecedor");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setForeground(new Color(153, 162, 209));
        contentPane.add(lblNewLabel, "cell 0 3");

        JPanel topo = new JPanel();
        topo.setBorder(new MatteBorder(0, 0, 5, 0, new Color(32, 60, 115, 124)));
        contentPane.add(topo, "cell 0 5,grow");
        topo.setOpaque(false);
        topo.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]", "[]"));

        JLabel lblID = new JLabel("ID");
        lblID.setEnabled(false);
		topo.add(lblID, "flowx,cell 0 0,alignx center");

		txtID = new JTextField();
		txtID.setEnabled(false);
		topo.add(txtID, "cell 0 0,alignx center");
		txtID.setColumns(10);
		
        JLabel lblEMAIL = new JLabel("EMAIL");
        topo.add(lblEMAIL, "flowx,cell 2 0,alignx center");

        textEMAIL = new JTextField();
        textEMAIL.setColumns(10);
        topo.add(textEMAIL, "cell 2 0");
        
        JLabel lblNOME_FORNECEDOR = new JLabel("NOME DO FORNECEDOR"); 
        topo.add(lblNOME_FORNECEDOR, "flowx,cell 4 0,alignx center");

        textNOME_FORNECEDOR = new JTextField(); 
        topo.add(textNOME_FORNECEDOR, "cell 4 0");
        textNOME_FORNECEDOR.setColumns(10);

        JPanel topo_1 = new JPanel();
        topo_1.setOpaque(false);
        topo_1.setBorder(new MatteBorder(0, 0, 5, 0, new Color(32, 60, 115, 124)));
        contentPane.add(topo_1, "cell 0 6,grow");
        topo_1.setLayout(new MigLayout("", "[70px][100px][][][70px][70px][][][][][][][][]", "[][]"));

        JLabel lblNOMECONTATO = new JLabel("NOME P/ CONTATO");
        topo_1.add(lblNOMECONTATO, "cell 1 1,alignx trailing");

        textNOMECONTATO = new JTextField();
        textNOMECONTATO.setColumns(10);
        topo_1.add(textNOMECONTATO, "cell 3 1");

        JLabel lblTELEFONE = new JLabel("TELEFONE");
        topo_1.add(lblTELEFONE, "cell 6 1");
        
        try {
            MaskFormatter format_textFieldTelefone = new MaskFormatter("(##) #####-####");
            textTELEFONE = new JFormattedTextField(format_textFieldTelefone);
            topo_1.add(textTELEFONE, "cell 7 1,alignx center");
            textTELEFONE.setColumns(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel inferior = new JPanel();
        inferior.setBackground(new Color(128, 0, 255));
        contentPane.add(inferior, "cell 0 7,grow");
        inferior.setOpaque(false);
        inferior.setLayout(new MigLayout("", "[100px][100px][grow][grow][]", "[][][][][][]"));

        JButton btnCadastrar = new JButton("CADASTRAR");
        btnCadastrar.setBackground(new Color(32, 60, 115));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                
                if (textEMAIL.getText().isEmpty() || textNOME_FORNECEDOR.getText().isEmpty() ||
                    textNOMECONTATO.getText().isEmpty() || textTELEFONE.getText().isEmpty()) {
                    
                    TelaErro telaErro = new TelaErro("Preencha todos os campos obrigatórios!", 0);
                    telaErro.setVisible(true);
                } else {
                    
                    String telefoneNumerico = textTELEFONE.getText().replaceAll("[^\\d]", "");

                    
                    if (telefoneNumerico.length() != 11) {
                        TelaErro telaErro = new TelaErro("O telefone deve conter exatamente 11 dígitos!", 0);
                        telaErro.setVisible(true);
                        return;
                    }

                   
                    String email = textEMAIL.getText();
                    if (!email.contains("@") || !email.contains(".")) {
                        TelaErro telaErro = new TelaErro("O e-mail deve conter '@' e '.'!", 0);
                        telaErro.setVisible(true);
                        return;
                    }

                   
                    String nomeContato = textNOMECONTATO.getText();
                    if (!nomeContato.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                        TelaErro telaErro = new TelaErro("O nome para contato deve conter apenas letras!", 0);
                        telaErro.setVisible(true);
                        return;
                    }

                    
                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor.setEmail(textEMAIL.getText());
                    fornecedor.setNomeFornecedor(textNOME_FORNECEDOR.getText());
                    fornecedor.setNomeCtt(textNOMECONTATO.getText());
                    fornecedor.setTelefone(textTELEFONE.getText());

                    
                    FornecedorDAO dao = new FornecedorDAO();
                    int resultado = dao.cadastrarFornecedor(fornecedor);
                    if (resultado == 1) {
                        new TelaErro("Fornecedor cadastrado com sucesso!", 3);

                        telaFornecedores.adicionarFornecedor(fornecedor);
                        limparCampos();

                        dispose();
                        telaFornecedores.setVisible(true);
                    } else {
                        new TelaErro("Erro ao cadastrar o fornecedor", 0);
                    }
                }
            }
        });


        inferior.add(btnCadastrar, "cell 0 0");

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
        inferior.add(btnCancelar, "cell 1 0");
    }

    private void limparCampos() {
        txtID.setText("");
        textEMAIL.setText("");
        textNOME_FORNECEDOR.setText("");
        textNOMECONTATO.setText("");
        textTELEFONE.setText("");
    }
}
