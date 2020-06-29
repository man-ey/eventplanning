package de.fim.wad.eventplanning.controller;

import de.fim.wad.eventplanning.model.Event;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String homepage(){
        System.out.println("called homepage");
        return "index";
    }

    @RequestMapping("/createEvent")
    public String createNewEvent(Model model){
        model.addAttribute("createdEvent", new Event());
        return "CreateEvent";
    }

    @RequestMapping("/topTwenty")
    public String topTwenty(){
        return "TopTwenty";
    }

    @RequestMapping("/newestEvents")
    public String newestEvents(){
        return "index";
    }
}
