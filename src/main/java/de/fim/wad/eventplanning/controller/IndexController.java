package de.fim.wad.eventplanning.controller;

import de.fim.wad.eventplanning.db.JavaH2Server;
import de.fim.wad.eventplanning.models.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class IndexController {

    @RequestMapping("/")
    String index(){
        return "index";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    String test(HttpServletRequest request, Model model){
        String name = request.getParameter("name");
        String iString = request.getParameter("nr");
        int i = 0;
        try{
            i = Integer.parseInt(iString);
        }
        catch (NumberFormatException e){

        }
        String [] array = new String[i];
        for (int j = 0; j<array.length; j++) {
            array[j] = "Item " + j;
        }

        if(name==null)
            name = "DEPP";
        ArrayList<Event> events = new ArrayList<>();
        JavaH2Server server = new JavaH2Server("jdbc:h2:~/test", "sa", "");
        ResultSet eventResult = server.getEvents();

        if(eventResult != null){
            try{
                while(eventResult.next()){
                    events.add(new Event(eventResult.getString("name"),
                            eventResult.getString("date"),
                            eventResult.getString("location"),
                            eventResult.getString("description"),
                            eventResult.getString("type"),
                            eventResult.getInt("likes"),
                            eventResult.getInt("dislikes")));
                }

            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }

        model.addAttribute("name", name);
        model.addAttribute("array", array);
        model.addAttribute("events", events);

        return "test";
    }
}
