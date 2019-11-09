package test;

import entity.Email;
import service.EmailService;

public class emailtest {
    public static void main(String[] args) throws Exception {

        EmailService es = new EmailService("379949419@qq.com","A6B4");
        es.sendEmail();
    }
}
