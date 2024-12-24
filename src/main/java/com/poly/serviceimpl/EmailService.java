package com.poly.serviceimpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.poly.entity.User;

@Service
public class EmailService {
	private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendForgotPasswordEmail(User user, String newPassword) {
        String subject = "Forgot Password";
        String emailContent = "Hello " + user.getFullName() 
        + " bạn đã yêu cầu đặt lại mật khẩu. Mã của bạn là: " + newPassword + "\n\n"
        + " Cảm ơn bạn đã sử dụng sản phẩm của chúng tôi.\n\n"
        + " \n Trân trọng,\n";

        sendEmail(user.getEmail(), subject, emailContent);
    }

    private void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); 
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            
        }
    }
}
