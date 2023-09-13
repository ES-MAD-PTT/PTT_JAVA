package com.atos.utils.email;

/*
 * @(#)MailSession. 22/04/2004 Copyright (c) 2004 Atos Origin. All Rights
 * Reserved. This software is the confidential and proprietary information of
 * Atos Origin. You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Atos Origin.
 */
import java.io.File;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * La clase <code>MailSession</code> proporciona servicios para notificaciones
 * a traves de correo electronico.
 * @author Atos Origin Spain - Daniel Reviriego (daniel.reviriego)
 */
public class MailSession {

    /**
     * La sesion con el servidor de correo.
     */
	
	private static final Logger log = LogManager.getLogger(MailSession.class);
	
    private Session session = null;

    private String user = "";
    private String address = "";
    private String pass = "";
    private String protocol = "imap";
    private String server = "";
    private String transport = "smtp";
    private String port = "25";
    private String TRUE = "true";
    private String FALSE = "false";

    private String MAIL_HOST = "mail.host";
    private String MAIL_SMTP_PORT = "mail.smtp.port";
    private String MAIL_STORE_PROTOCOL = "mail.store.protocol";
    private String MAIL_STORE_TRANSPORT = "mail.store.transport";
    private String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private String MAIL_SMTP_EHLO = "mail.smtp.ehlo";

    /**
     * Metodo que establece las propiedades basicas para poder abrir una
     * conexion con un servidor de correo.
     * @return Las propiedades basicas para establecer una sesion con el
     *         servidor de correo.
     */
    private Properties setBasicproperties() {
        Properties config = new Properties();
        config.setProperty(this.MAIL_SMTP_PORT, this.port);
        config.setProperty(this.MAIL_STORE_PROTOCOL, this.protocol);
        config.setProperty(this.MAIL_STORE_TRANSPORT, this.transport);
        config.setProperty(this.MAIL_HOST, this.server);
        return config;
    }

    /**
     * Constructor 1.
     * @param mailServer
     *        Nombre del servidor de correo.
     * @param user
     *        Nombre de usuario del usuario emisor.
     * @param address
     *        Direccion de correo electronico del usuario emisor.
     * @throws Exception
     *         Si ocurre un problema al obtener la instancia de sesion con el
     *         servidor.
     */
    public MailSession(String mailServer, String user, String address) throws Exception {
        try {
            this.user = user;
            this.address = address.trim();
            this.server = mailServer.trim();

            Properties config = setBasicproperties();

            this.session = Session.getInstance(config, null);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Constructor 2.
     * @param mailServer
     *        Nombre del servidor de correo.
     * @param user
     *        Nombre de usuario del usuario emisor.
     * @param address
     *        Direccion de correo electronico del usuario emisor.
     * @param pass
     *        Contrasenya del usuario emisor.
     * @exception IllegalArgumentException
     *            Una excepcion de argumento no valido si alguno de los
     *            parametros es nulo.
     */
    public MailSession(String mailServer, String user, String address, String pass) throws IllegalArgumentException {

        if (mailServer == null || mailServer.trim().equals("")) {
            throw new IllegalArgumentException("El nombre del servidor de correo debe tener valor.");
        }
        
        if (address == null || address.trim().equals("")) {
            throw new IllegalArgumentException("La cuenta de correo para abrir la sesion debe tener valor.");
        }
        
        if (user == null || user.trim().equals("")) {
            throw new IllegalArgumentException("El usuario para abrir la sesion debe tener valor.");
        }
        
        if (pass == null || pass.trim().equals("")) {
            throw new IllegalArgumentException("El servidor requiere autenticacion, la contrasenya para abrir la sesion debe tener valor.");
        }
        
        this.address = address.trim();
        this.server = mailServer.trim();
        this.user = user;
        this.pass = pass;

        Properties config = setBasicproperties();
        config.setProperty("mail.debug", "true");
       // config.setProperty(this.MAIL_SMTP_AUTH, this.TRUE);
        config.setProperty(this.MAIL_SMTP_EHLO, this.FALSE);

        MyAuthenticator authenticator = new MyAuthenticator(this.user, this.pass);
        this.session = Session.getInstance(config, authenticator);// .getInstance(config, authenticator);
    }

    /**
     * Constructor 3.
     * @param mailServer
     *        Nombre del servidor de correo.
     * @param user
     *        Nombre de usuario del usuario emisor.
     * @param address
     *        Direccion de correo electronico del usuario emisor.
     * @param protocol
     *        Protocolo de envio de correo.
     * @param pass
     *        Contrasenya del usuario emisor.
     * @throws Exception
     *         Si ocurre un problema al obtener la instancia de sesion con el
     *         servidor o con el autenticador.
     */
    public MailSession(String mailServer, String user, String address, String protocol, String pass) throws Exception {
        try {
            this.server = mailServer.trim();
            this.address = address.trim();
            this.protocol = protocol;
            this.user = user;
            this.pass = pass;

            Properties config = setBasicproperties();
            config.setProperty(this.MAIL_SMTP_AUTH, this.TRUE);

            MyAuthenticator authenticator = new MyAuthenticator(this.user, this.pass);
            this.session = Session.getInstance(config, authenticator);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Metodo para enviar un mensaje simple de texto.
     * @param to
     *        Direcciones de destino.
     * @param cc
     *        Direcciones de destino con copia.
     * @param bcc
     *        Direcciones de destino con copia oculta.
     * @param subject
     *        Asunto del mensaje.
     * @param message
     *        Contenido del mensaje.
     * @param mime
     *        Tipo MIME.
     * @param charset
     *        Set de caracteres.
     * @throws Exception
     */
    public void sendTextMail(Enumeration to, Enumeration cc, Enumeration bcc, String subject, String message, String mime, String charset) throws SendFailedException, MessagingException, Exception {
        try {
            this.session.setDebug(true);

            // Creating MIME message
            MimeMessage mimeMsg = this.createMessage(to, cc, bcc, subject);

            Multipart multipart = new MimeMultipart();
            BodyPart textBodyPart = new MimeBodyPart();

            // Setting message content
            textBodyPart.setContent(message, mime + "; charset=\"" + charset + "\"");
            multipart.addBodyPart(textBodyPart);

            // Building final message
            mimeMsg.setContent(multipart);
            mimeMsg.setSentDate(Calendar.getInstance().getTime());
            mimeMsg.saveChanges();

            // Sending message
            sendMessage(mimeMsg);
        } catch (SendFailedException e) {
        	log.error(e.getMessage(), e);
            throw new SendFailedException("Error enviando mail de texto: " + e.getMessage());
        } catch (MessagingException e) {
        	log.error(e.getMessage(), e);
            throw new SendFailedException("Error enviando mail de texto: " + e.getMessage());
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
            throw new Exception("Error enviando mail de texto: " + e.getMessage());
        }

    } // end sendTextMail

    /**
     * Envia un mensaje Mime
     * @param mimeMsg
     *        El objeto mensaje a enviar.
     * @throws Exception
     *         Si ocurre algun problema en el envio.
     */
    private void sendMessage(MimeMessage mimeMsg) throws Exception {
        Transport transport = this.session.getTransport(this.transport);
        transport.connect(this.server, this.user, this.pass);
        transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
        transport.close();
    }

    /**
     * Metodo para enviar un correo electronico con mensaje adjunto.
     * @param to
     *        Direcciones de destino.
     * @param cc
     *        Direcciones de destino con copia.
     * @param bcc
     *        Direcciones de destino con copia oculta.
     * @param subject
     *        Asunto del mensaje.
     * @param message
     *        Cuerpo del mensaje.
     * @param mime
     *        Tipo MIME.
     * @param charset
     *        Set de caracteres.
     * @param filenames
     *        Nombre de los ficheros a adjuntar.
     * @param fileTypes
     *        Tipo de los ficheros a adjuntar.
     * @param fileContents
     *        Contenido de los ficheros a adjuntar.
     */
    public void sendMailWithAttach(Enumeration to, Enumeration cc, Enumeration bcc, String subject, String message, String mime, String charset, Enumeration filenames, Enumeration fileTypes,
                    Enumeration fileContents) throws Exception {
        try {

            // Creating MIME message
            MimeMessage mimeMsg = this.createMessage(to, cc, bcc, subject);

            MimeMultipart multipart = new MimeMultipart();
            BodyPart textBodyPart = new MimeBodyPart();

            // Setting message content
            textBodyPart.setContent(message, mime + "; charset=\"" + charset + "\"");
            multipart.addBodyPart(textBodyPart);

            // Attachment for every file received
            BodyPart pdgBodyPart = null;

            String name = "";
            String type = "";
            Object content = "";

            while (filenames.hasMoreElements()) {

                pdgBodyPart = new MimeBodyPart();

                name = (String) filenames.nextElement();
                type = (String) fileTypes.nextElement();
                content = fileContents.nextElement();

                pdgBodyPart.setFileName(name);
                DataSource dataSource = new ByteArrayDataSource((byte[])content, type);
                pdgBodyPart.setDataHandler(new javax.activation.DataHandler(dataSource));
                
                multipart.addBodyPart(pdgBodyPart);
            }

            // Building final message
            mimeMsg.setContent(multipart);
            mimeMsg.setSentDate(Calendar.getInstance().getTime());
            mimeMsg.saveChanges();

            
            
            // Sending message
            sendMessage(mimeMsg);
            // Transport.send(mimeMsg);

        } catch (Exception e) {
        	log.error(e.getMessage(), e);
            throw new Exception("Error enviando mail de texto con attachments");
        }
    } // end sendMailWithAttach

    /**
     * Metodo para enviar un mensaje con fichero adjunto desde disco.
     * @param to
     *        Direcciones de destino.
     * @param cc
     *        Direcciones de destino con copia.
     * @param bcc
     *        Direcciones de destino con copia oculta.
     * @param subject
     *        Asunto del mensaje.
     * @param message
     *        Cuerpo del mensaje.
     * @param mime
     *        Tipo MIME.
     * @param charset
     *        Set de caracteres.
     * @param filepaths
     *        Path de origen de los ficheros a adjuntar.
     */
    public void sendMailWithDiskAttach(Enumeration to, Enumeration cc, Enumeration bcc, String subject, String message, String mime, String charset, Enumeration filepaths) throws Exception {
        try {

            // Creating MIME message
            MimeMessage mimeMsg = this.createMessage(to, cc, bcc, subject);

            Multipart multipart = new MimeMultipart();
            BodyPart textBodyPart = new MimeBodyPart();

            // Setting message content
            textBodyPart.setContent(message, mime + "; charset=\"" + charset + "\"");
            multipart.addBodyPart(textBodyPart);

            // Attachment for every file received
            BodyPart pdgBodyPart = null;
            File file = null;
            String path = "";

            while (filepaths.hasMoreElements()) {
                path = (String) filepaths.nextElement();
                file = new File(path);

                pdgBodyPart = new MimeBodyPart();

                pdgBodyPart.setFileName(file.getName());
                pdgBodyPart.setDataHandler(new javax.activation.DataHandler(new javax.activation.FileDataSource(path)));

                multipart.addBodyPart(pdgBodyPart);
            }

            // Building final message
            mimeMsg.setContent(multipart);
            mimeMsg.setSentDate(Calendar.getInstance().getTime());
            mimeMsg.saveChanges();

            // Sending message
            sendMessage(mimeMsg);
            // Transport.send(mimeMsg);

        } catch (Exception e) {
        	log.error(e.getMessage(), e);
            throw new Exception("Error enviando mail de texto con attachments en disco");
        }
    } // end sendMailWithDiskAttach

    /**
     * Metodo para crear el mensaje de correo.
     * @param to
     *        Direcciones de destino.
     * @param cc
     *        Direcciones de destino con copia.
     * @param bcc
     *        Direcciones de destino con copia oculta.
     * @param subject
     *        Asunto del mensaje.
     * @return MimeMessage Mensaje a enviar.
     */
    private MimeMessage createMessage(Enumeration to, Enumeration cc, Enumeration bcc, String subject) throws Exception {

        MimeMessage mimeMsg = new MimeMessage(this.session);
        mimeMsg.setFrom(new InternetAddress(this.user + "<" + this.address + ">"));

        if (to != null) {
            while (to.hasMoreElements()) {
                mimeMsg.addRecipient(Message.RecipientType.TO, new InternetAddress(((String) to.nextElement()).trim()));
            }
        }

        if (cc != null) {
            while (cc.hasMoreElements()) {
                mimeMsg.addRecipient(Message.RecipientType.CC, new InternetAddress(((String) cc.nextElement()).trim()));
            }
        }

        if (bcc != null) {
            while (bcc.hasMoreElements()) {
                mimeMsg.addRecipient(Message.RecipientType.BCC, new InternetAddress(((String) bcc.nextElement()).trim()));
            }
        }

        mimeMsg.setSubject(subject, "UTF-8");
        return mimeMsg;
    } // end createMessage

} // end MailSession
