package com.smithdrug.sms.emailService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.smithdrug.sms.model.fpsEmailModel;

@Service
public class emailService {
	@Autowired
    private SpringTemplateEngine templateEngine;
	@Autowired
	private JavaMailSender emailSender;
	
	@Value("${email.from.address}")
	private String fromEmailAddress;
	
	@Value("${egate.base.url}")
	private String egateBaseURL;
	
	@Value("${integral.base.url}")
	private String integralBaseURL;
	
	@Value("${integral.reset.password.url}")
	private String integralResetPasswordURL;
	
	@Value("${egate.reset.password.url}")
	private String egateResetPasswordURL;
	
	public boolean sendPasswordResetEmailForEgate(String emailAddress, String uniqueId,String user,HttpServletRequest request) {
		fpsEmailModel mail = new fpsEmailModel();
        mail.setFrom(fromEmailAddress);
        mail.setTo(emailAddress);
        mail.setSubject("Your password request has been received");

        Map<String, Object> model = new HashMap<>();
        model.put("token", uniqueId);
        model.put("user", user);
        model.put("signature", egateBaseURL);
       // String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("resetUrl", egateBaseURL+egateResetPasswordURL + "?token=" + uniqueId+"&acctNo="+user);
        mail.setFpsModel(model);
        boolean emailMessage = sendEmail(mail,"egate");
		return emailMessage;
	}
	
	public boolean sendPasswordResetEmailForIntegral(String emailAddress, String uniqueId,String user,HttpServletRequest request) {
		fpsEmailModel mail = new fpsEmailModel();
        mail.setFrom(fromEmailAddress);
        mail.setTo(emailAddress);
        mail.setSubject("Your password request has been received");

        Map<String, Object> model = new HashMap<>();
        model.put("token", uniqueId);
        model.put("user", user);
        model.put("signature", integralBaseURL);
        //String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("resetUrl", integralBaseURL+integralResetPasswordURL + "?token=" + uniqueId+"&acctNo="+user);
        mail.setFpsModel(model);
        boolean emailMessage = sendEmail(mail,"integral");
		return emailMessage;
	}
	
	public boolean sendPasswordResetSuccessEmailForEgate(String emailAddress, String user, HttpServletRequest request) {
		fpsEmailModel mail = new fpsEmailModel();
        mail.setFrom(fromEmailAddress);
        mail.setTo(emailAddress);
        mail.setSubject("Your password has been reset");

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("signature", egateBaseURL);
        //String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("baseUrl", egateBaseURL);
        mail.setFpsModel(model);
        boolean emailMessage = sendSuccessEmail(mail,"egate");
		return emailMessage;
	}
	
	public boolean sendPasswordResetSuccessEmailForIntegral(String emailAddress, String user, HttpServletRequest request) {
		fpsEmailModel mail = new fpsEmailModel();
        mail.setFrom(fromEmailAddress);
        mail.setTo(emailAddress);
        mail.setSubject("Your password has been reset");

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("signature", integralBaseURL);
        //String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("baseUrl", integralBaseURL);
        mail.setFpsModel(model);
        boolean emailMessage = sendSuccessEmail(mail,"integral");
		return emailMessage;
	}
	
	public boolean sendSuccessEmail(fpsEmailModel mail,String company) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(mail.getFpsModel());
            String html = "";
            if(company.equalsIgnoreCase("egate"))
            {
            	html = templateEngine.process("email/email-success-template-egate", context);
            }
            if(company.equalsIgnoreCase("integral"))
            {
            	html = templateEngine.process("email/email-success-template-integral", context);
            }

            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            emailSender.send(message);
            return true;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
	
	public boolean sendEmail(fpsEmailModel mail,String company) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(mail.getFpsModel());
            String html = "";
            if(company.equalsIgnoreCase("egate"))
            {
            	System.out.println("emailService.sendEmail() inside egate");
            	html = templateEngine.process("email/email-template-egate", context);
            }
            if(company.equalsIgnoreCase("integral"))
            {
            	System.out.println("emailService.sendEmail() inside integral");
            	html = templateEngine.process("email/email-template-integral", context);
            }

            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            emailSender.send(message);
            return true;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
