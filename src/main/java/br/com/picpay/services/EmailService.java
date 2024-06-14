package br.com.picpay.services;

import br.com.picpay.domain.transaction.exceptions.EmailSenderFailureException;
import br.com.picpay.dtos.feign.EmailRequestDTO;
import br.com.picpay.feign.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailClient emailClient;

    public void sendEmail(EmailRequestDTO dto) {
        try {
            this.emailClient.sendEmail(dto);
        } catch (Exception e) {
            throw new EmailSenderFailureException("Error on email service");
        }
    }

    public String generateMessage(String payer, BigDecimal value) {
        return "<!DOCTYPE html>"
                + "<html lang='pt-BR'>"
                + "<head>"
                + "    <meta charset='UTF-8'>"
                + "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                + "    <title>Transação Bem-Sucedida</title>"
                + "    <style>"
                + "        body {"
                + "            font-family: Arial, sans-serif;"
                + "            background-color: #f4f4f4;"
                + "            margin: 0;"
                + "            padding: 0;"
                + "        }"
                + "        .container {"
                + "            width: 100%;"
                + "            max-width: 600px;"
                + "            margin: 0 auto;"
                + "            background-color: #ffffff;"
                + "            padding: 20px;"
                + "            border-radius: 8px;"
                + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
                + "        }"
                + "        .header {"
                + "            background-color: #1DBF73;"
                + "            color: #ffffff;"
                + "            text-align: center;"
                + "            padding: 20px 0;"
                + "            border-radius: 8px 8px 0 0;"
                + "        }"
                + "        .header img {"
                + "            width: 120px;"
                + "            margin: 0 auto;"
                + "        }"
                + "        .header h1 {"
                + "            margin: 0;"
                + "        }"
                + "        .content {"
                + "            padding: 20px;"
                + "            text-align: center;"
                + "        }"
                + "        .content p {"
                + "            font-size: 16px;"
                + "            color: #333333;"
                + "        }"
                + "        .footer {"
                + "            text-align: center;"
                + "            padding: 10px 0;"
                + "            font-size: 12px;"
                + "            color: #777777;"
                + "        }"
                + "        .footer a {"
                + "            color: #1DBF73;"
                + "            text-decoration: none;"
                + "        }"
                + "    </style>"
                + "</head>"
                + "<body>"
                + "    <div class='container'>"
                + "        <div class='header'>"
                + "            <img src='https://www.ecp.org.br/wp-content/uploads/2022/02/picpay-02.png' alt='PicPay Logo'>"
                + "            <h1>Obrigado por usar o PicPay!</h1>"
                + "        </div>"
                + "        <div class='content'>"
                + "            <p>Olá, " + payer + "</p>"
                + "            <p>Seu pagamento de R$ " + value + ",00 foi processado com sucesso.</p>"
                + "            <p>Obrigado por escolher o PicPay para suas transações financeiras. Estamos aqui para tornar seus pagamentos mais rápidos e seguros.</p>"
                + "            <p>Se você tiver qualquer dúvida ou precisar de assistência, não hesite em entrar em contato com nosso suporte.</p>"
                + "            <p>Atenciosamente,<br>Equipe PicPay</p>"
                + "        </div>"
                + "        <div class='footer'>"
                + "            <p>&copy; 2024 PicPay. Todos os direitos reservados.</p>"
                + "            <p><a href='https://www.picpay.com'>Visite nosso site</a> | <a href='mailto:support@picpay.com'>Contate o suporte</a></p>"
                + "        </div>"
                + "    </div>"
                + "</body>"
                + "</html>";
    }
}
