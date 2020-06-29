package de.fim.wad.eventplanning.controller;

import de.fim.wad.eventplanning.dto.EventDTO;
import de.fim.wad.eventplanning.model.Event;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String homepage(Model model){
 //       EventController dto = new EventController();
   //     model.addAttribute("topTwenty", dto.newestTwenty());
  //      System.out.println("called homepage");
        return "index";
    }

    @RequestMapping("/createEvent")
    public String createNewEvent(Model model){
        model.addAttribute("createdEvent", new EventDTO());
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
