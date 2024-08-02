package utez.edu.mx.market.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {
    //Se definen las variables para el correo de su sistema, se escriben la dirección y la contraseña de aplicación
    //NOTA: recomiendo que no usen uno de sus correos institucionales o personales, de preferencia creen uno
    //nuevo para uso exclusivo del sistema.
    //NOTA 2: La contraseña de aplicación se genera al activar la verificación de dos pasos de la cuenta de gmail
    //posterior a eso se debe acceder a esta liga: https://myaccount.google.com/apppasswords
    //dentro de esa vista, de debe de dar un nombre(puede ser cualquiera) y generar la contraseña

    private final String SYSTEM_EMAIL = "market.racoon07@gmail.com";// Dirección del correo electrónico
    private final String SYSTEM_EMAIL_PASSWORD = "xjsh wwjl ibaq lajg ";// Contraseña de aplicación

    //METODO QUE ENVÍA EL CORREO DE RECUPERACIÓN
    public void sendRecoveryEmail(String userEmail, String username, String code) {
        //Asunto del correo
        String subject = "Recuperación de contraseña | La tienda del mapache";

        //Propiedades de configuración del correo
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        //Atributo que crea la sesión para el envío del correo
        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SYSTEM_EMAIL, SYSTEM_EMAIL_PASSWORD);
            }
        });

        //Creación del mensaje
        try {
            Message message = new MimeMessage(mailSession); //Objeto para crear el mensaje
            message.setFrom(new InternetAddress(SYSTEM_EMAIL)); //Establecer de donde viene el correo
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail)); //Establecer a quien va dirigido el correo
            message.setSubject(subject); //Establecer el asunto del correo

            //Establecer el cuerpo del correo
            String htmlBody = "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "</head>\n" +
                            "<body style=\"font-family: Arial, Helvetica, sans-serif; margin: 0px;\">\n" +
                            "    <div style=\"width: 100vw; height: 100vh;\">\n" +
                            "        <table style=\"padding: 16px; height: fit-content; border: 1px solid #cbcbcb; width: 550px; border-radius: 16px;\">\n" +
                            "            <tr>\n" +
                            "                <td>\n" +
                            "                    <h1 style=\"margin-top: 0px; font-weight: lighter;\">La tienda del mapache</h1>\n" +
                            "                    <h3 style=\"margin: 0px; font-weight: lighter;\">Recuperación de contraseña</h3>\n" +
                            "                    <hr style=\"width: 100%; border: 1px solid #ededed;\">\n" +
                            "                    <p>Hola <strong>" + username +"</strong></p>\n" +
                            "                    <p style=\"text-align: justify;\">Hemos recibido una solicitud para un cambio de contraseña, en caso de no haber solicitado dicho cambio solo hacer caso omiso a este correo. De igual manera le pedimos que no responda a este correo.</p>\n" +
                            "                    <br>\n" +
                            "                    <p>Para restablecer tu contraseña, da click en el siguiente botón:</p>\n" +
                            "                    <a href=\"http://localhost:8080/market_war_exploded/RedirectToRecoveryFormServlet?recoveryCode=" + code + "\" style=\"background-color: #ededed; text-decoration: none; border: none; border-radius: 8px; padding: 10px 15px 10px 15px; color: #000;\">Restablecer contraseña</a>\n" +
                            "                    <br><br>\n" +
                            "                    <hr style=\"width: 100%; border: 1px solid #ededed;\">\n" +
                            "                    <p style=\"text-align: center; margin: 5px 0px 5px 0px;\">\n" +
                            "                        <small style=\"color: #797979;\">La tienda del mapache® | 2024</small>\n" +
                            "                    </p>\n" +
                            "                </td>\n" +
                            "            </tr>\n" +
                            "        </table>\n" +
                            "    </div>\n" +
                            "</body>\n" +
                            "</html>";
            message.setContent(htmlBody, "text/html");

            Transport.send(message); //Envío del correo
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
