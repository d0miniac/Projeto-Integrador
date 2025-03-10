package visao;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controle.VendaDAO;
import modelo.Funcionario;
import modelo.HVendasTableModel;
import modelo.Produto;
import modelo.Venda;
import net.miginfocom.swing.MigLayout;


public class TelaHistoricoVendas extends JFrame {


    private JPanel contentPane;
    private JTable table;
    private HVendasTableModel vtm;
    private ArrayList<Venda> listaVendas;
    private JComboBox<String> comboFiltrar;
    private List<Object[]> vendas;
    
    public static void main(String[] args) {
	    EventQueue.invokeLater(() -> {
	        try {
	        	
	        	Produto prod = new Produto();
                Funcionario funcionario = new Funcionario(); 
	            String mensagem = "Bem-vindo ao sistema!";
	            TelaHistoricoVendas frame = new TelaHistoricoVendas(funcionario, mensagem, prod);
	            frame.setVisible(true);
	            frame.setSize(657, 425);
	            frame.setLocationRelativeTo(null);
	        } catch (Exception e) {

	            TelaErro telaErro = new TelaErro("Erro crítico: " + e.getMessage());
	            telaErro.setVisible(true);
	        }
	    });
	}

    public TelaHistoricoVendas(Funcionario func, String mensagem, Produto prod) {
     
        setTitle("Histórico de Vendas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1215, 850);
        setLocationRelativeTo(null);
        setResizable(false);


     
        contentPane = new ImagePanel("src/img/bgTelaHistorico.png");
        contentPane.setBackground(new Color(243, 244, 240));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[183px][276px][4px][422px][4px][292px]", "[120px][][][][25px][523px]"));


     
        JPanel panelVazio = new JPanel();
        panelVazio.setOpaque(false);
        contentPane.add(panelVazio, "cell 0 0,grow");
        panelVazio.setLayout(null);


       
        JLabel lblSeta = new JLabel();
        lblSeta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSeta.setIcon(new ImageIcon(new ImageIcon(TelaCadastroProdutos.class.getResource("/img/de-volta.png"))
                .getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
        lblSeta.setBounds(0, 0, 110, 100);
        panelVazio.add(lblSeta);
		lblSeta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaMenu tela = new TelaMenu(prod,func, mensagem);
				dispose();
				tela.setVisible(true);
			}
		});



        


//        comboFiltrar = new JComboBox<>();
//        comboFiltrar.setFont(new Font("Tahoma", Font.PLAIN, 20));
//        comboFiltrar.setBackground(new Color(209, 209, 233));
//        contentPane.add(comboFiltrar, "cell 1 3 1 2,growx,aligny center");
//        comboFiltrar.addItem("Todos");
//        comboFiltrar.addItem("Camisa");
//        comboFiltrar.addItem("Calça");
//        comboFiltrar.addItem("Blusa");
//        comboFiltrar.addItem("Jaqueta");
//        comboFiltrar.addItem("Saia/Vestido");
//        comboFiltrar.addItem("Bermudas/Shorts");
//        comboFiltrar.addItem("Roupa Íntima");


     
//        JButton btnBuscar = new JButton("Buscar");
//        btnBuscar.setForeground(Color.WHITE);
//        btnBuscar.setBackground(new Color(32, 60, 115));
//        btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 20));
//        contentPane.add(btnBuscar, "cell 3 4,alignx left,aligny top");


//        btnBuscar.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                buscarVendas();
//            }
//        });


        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(new LineBorder(new Color(123, 150, 212), 2, true));
        contentPane.add(scrollPane, "cell 0 5 6 1,grow");
        
        VendaDAO vdao= new VendaDAO();
        listaVendas=vdao.selecionarVendas();
        
        vtm = new HVendasTableModel(listaVendas);
        
        table = new JTable();
        table.setBackground(new Color(243, 244, 240));
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        table.setModel(vtm);
        scrollPane.setViewportView(table);


     
        


       
        //atualizarTabela(vendas);
        theader();
    }


   
//    protected void buscarVendas() {
//        String categoriaSelecionada = (String) comboFiltrar.getSelectedItem();
//        List<Object[]> vendasFiltradas = new ArrayList<>();
//
//
//        for (Object[] venda : vendas) {
//            String categoria = (String) venda[1];
//            if (categoriaSelecionada.equals("Todos") || categoria.equals(categoriaSelecionada)) {
//                vendasFiltradas.add(venda);
//            }
//        }
//
//
//       
//        atualizarTabela(vendasFiltradas);
//    }


 
//    private void atualizarTabela(List<Object[]> dados) {
//        NonEditableTableModel modelo = new NonEditableTableModel(
//                dados.toArray(new Object[0][0]),
//                new String[]{"ID", "Título", "Categoria", "Marca", "Cor", "Tamanho", "Quantidade", "Preço"});
//        table.setModel(modelo);
//    }


   
    private void theader() {
        JTableHeader thead = table.getTableHeader();
        thead.setForeground(new Color(123, 150, 212));
        thead.setBackground(Color.WHITE);
        thead.setFont(new Font("Tahoma", Font.PLAIN, 20));
    }


   
//    private class NonEditableTableModel extends DefaultTableModel {
//        public NonEditableTableModel(Object[][] data, String[] columnNames) {
//            super(data, columnNames);
//        }


        
//        public boolean isCellEditable(int row, int column) {
//            return false;
//        }
    //}
}


