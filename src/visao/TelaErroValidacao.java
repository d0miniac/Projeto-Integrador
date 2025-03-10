package visao;

import javax.swing.*;
import java.awt.*;

public class TelaErroValidacao extends JFrame {

    public TelaErroValidacao(String mensagem) {
        setTitle("Erro de Validação");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JLabel lblMensagem = new JLabel("<html><center>" + mensagem + "</center></html>", SwingConstants.CENTER);
        lblMensagem.setFont(new Font("Arial", Font.BOLD, 14));
        lblMensagem.setForeground(Color.RED);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnFechar.addActionListener(e -> dispose());

        contentPane.add(lblMensagem, BorderLayout.CENTER);
        contentPane.add(btnFechar, BorderLayout.SOUTH);
    }
}
