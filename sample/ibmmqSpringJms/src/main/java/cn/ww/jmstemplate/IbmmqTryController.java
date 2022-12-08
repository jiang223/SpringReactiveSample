package cn.ww.jmstemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IbmmqTryController {

    @Autowired
    private JmsTemplate jmsTemplate;

    // 字符串消息的发送和接收

    @GetMapping("send")
    String send() {
        try {
            jmsTemplate.convertAndSend("SVW.S1120001B.MSG.OUT", "我是测试消息");
            return "OK";
        } catch (JmsException ex) {
            ex.printStackTrace();
            return "FAIL";
        }
    }

    @GetMapping("recv")
    String recv() {
        try {
            return jmsTemplate.receiveAndConvert("SVW.S1120001B.MSG.OUT").toString();
        } catch (JmsException ex) {
            ex.printStackTrace();
            return "FAIL";
        }
    }

    // javaBean消息的发送和接收
    
    @GetMapping("send1")
    String send1() {
        User user = new User();
        user.setName("张三");
        try {
            jmsTemplate.convertAndSend("SVW.S1120001B.MSG.OUT", user);
            return "OK";
        } catch (JmsException ex) {
            ex.printStackTrace();
            return "FAIL";
        }
    }

    @GetMapping("recv1")
    String recv1() {
        try {
            User u = (User) jmsTemplate.receiveAndConvert("SVW.S1120001B.MSG.OUT");
            System.out.println(u.getName());
            return u.toString();
        } catch (JmsException ex) {
            ex.printStackTrace();
            return "FAIL";
        }
    }
    //设置等待时间
    @GetMapping("recv2")
    String recv2() {
        try {
            jmsTemplate.setReceiveTimeout(9000);
            jmsTemplate.receiveAndConvert("SVW.S1120001B.MSG.OUT");
            return "";
        } catch (JmsException ex) {
            ex.printStackTrace();
            return "FAIL";
        }
    }

}
