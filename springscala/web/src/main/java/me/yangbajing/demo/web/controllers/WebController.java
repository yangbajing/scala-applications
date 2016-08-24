package me.yangbajing.demo.web.controllers;

import me.yangbajing.demo.data.domain.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Java WebController
 * Created by yangbajing on 16-8-24.
 */
@RestController
@RequestMapping("/web")
public class WebController {

    @RequestMapping(path = "message", method = RequestMethod.GET)
    public Message message() {
        return new Message("Yang Jing", 30);
    }

}
