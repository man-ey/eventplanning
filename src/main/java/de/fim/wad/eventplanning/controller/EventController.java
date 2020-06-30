package de.fim.wad.eventplanning.controller;

import de.fim.wad.eventplanning.APIs.GeoAPI;
import de.fim.wad.eventplanning.APIs.WeatherAPI;
import de.fim.wad.eventplanning.dto.EventCreationDTO;
import de.fim.wad.eventplanning.dto.EventDTO;
import de.fim.wad.eventplanning.dto.EventTypeDTO;
import de.fim.wad.eventplanning.model.Event;
import de.fim.wad.eventplanning.model.EventType;
import de.fim.wad.eventplanning.service.EventService;
import de.fim.wad.eventplanning.service.EventTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void config() throws IOException, ParseException {
        readEventTypes();
        initDB();
    }

    private void readEventTypes() throws IOException {
        FileReader fileReader = new FileReader("EventTypes.txt");

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String typesString = bufferedReader.readLine();

        bufferedReader.close();

        typesString = typesString.replace(" ", "");

        String[] types = typesString.split(",");

        for (String type : types) {
            saveEventType(new EventTypeDTO(type));
        }
    }

    private boolean isInit() throws IOException {
        FileReader fileReader = new FileReader("InitDB.txt");

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        boolean isInit = Boolean.parseBoolean(
                bufferedReader.readLine().replace(" ", "" ));

        bufferedReader.close();
        return isInit;

    }

    private EventDTO convertToEventDTO(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }

    private EventTypeDTO convertToEventTypeDTO(EventType eventType) {
        return modelMapper.map(eventType, EventTypeDTO.class);
    }

    private Event convertToEvent(EventDTO eventDTO) {
        return modelMapper.map(eventDTO, Event.class);
    }

    private EventType convertToEventType(EventTypeDTO eventTypeDTO) {
        return modelMapper.map(eventTypeDTO, EventType.class);
    }

    private Event convertToEvent(EventCreationDTO eventCreationDTO) {
        Event event = modelMapper.map(eventCreationDTO, Event.class);

        event.setLikes(0);
        event.setDislikes(0);
        return event;
    }

    @GetMapping("/init")
    private void initDB() throws ParseException, IOException {
        if (isInit()) {
            EventTypeDTO typ1 = new EventTypeDTO("Type_A");

            EventTypeDTO typ2 = new EventTypeDTO("Type_B");

            EventTypeDTO typ3 = new EventTypeDTO("Type_C");

            saveEventType(typ1);
            saveEventType(typ2);
            saveEventType(typ3);

            EventCreationDTO event1 = new EventCreationDTO("Event_1",
                    "First Event", "01.07.2020", "Passau",
                    eventTypeService.find(typ2.getEventType()).getEventType());

            EventCreationDTO event2 = new EventCreationDTO("Event_2",
                    "Second Event", "02.07.2020", "Munich",
                    eventTypeService.find(typ1.getEventType()).getEventType());


            EventCreationDTO event3 = new EventCreationDTO("Event_3",
                    "Event in past.", "31.12.2000",
                    eventTypeService.find(typ2.getEventType()).getEventType(),
                    13.389191, 52.50536);


            saveEvent(event1);
            saveEvent(event2);
            saveEvent(event3);


            for (int i = 0; i < 42; i++) {
                like(event1.getName());
            }

            for (int i = 0; i < 23; i++) {
                dislike(event1.getName());
            }

            for (int i = 0; i < 10; i++) {
                like(event2.getName());
            }

            for (int i = 0; i < 2; i++) {
                dislike(event2.getName());
            }

            for (int i = 0; i < 5; i++) {
                like(event3.getName());
            }

            for (int i = 0; i < 1; i++) {
                dislike(event3.getName());
            }
        }
    }

    // Interface
    @GetMapping("/api/events")
    public List<EventDTO> getAllEvents() {
        List<EventDTO> result = new ArrayList<>();

        for (Event event : eventService.getAll()) {
            result.add(convertToEventDTO(event));
        }
        return result;
    }

    @GetMapping("/api/eventtypes")
    public List<EventTypeDTO> getAllEventsTypes() {
        List<EventTypeDTO> result = new ArrayList<>();

        for (EventType eventType : eventTypeService.getAll()) {
            result.add(convertToEventTypeDTO(eventType));
        }
        return result;
    }

    @GetMapping("/api/newest20")
    public List<EventDTO> newestTwenty() {
        List<EventDTO> result = new ArrayList<>();

        for (Event event : eventService.newestN(20)) {
            result.add(convertToEventDTO(event));
        }
        return result;
    }

    @RequestMapping("like")
    public void like(@RequestParam("name") String eventName) {
        Event event = eventService.find(eventName);

        if (event != null) {
            event.like();
            eventService.update(event);
        } else {
            throw new IllegalArgumentException("Event does not exist.");
        }
    }

    @RequestMapping("dislike")
    public void dislike(@RequestParam("name") String eventName) {
        Event event = eventService.find(eventName);

        if (event != null) {
            event.dislike();
            eventService.update(event);
        } else {
            throw new IllegalArgumentException("Event does not exist.");
        }
    }

    @GetMapping("/api/topThree")
    public List<EventDTO> topThree() {
        List<EventDTO> result = new ArrayList<>();

        for (Event event : eventService.top(3)) {
            result.add(convertToEventDTO(event));
        }
        return result;
    }

    @GetMapping("/api/top")
    public List<EventDTO> topTwenty() {
        List<EventDTO> result = new ArrayList<>();

        for (Event event : eventService.top(20)) {
            result.add(convertToEventDTO(event));
        }
        return result;
    }



    public void saveEventType(EventTypeDTO eventType) {
        if (!eventTypeService.existsByID(eventType.getEventType())) {
            eventTypeService.save(convertToEventType(eventType));
        } else {
            throw new IllegalArgumentException("Event already exists!");
        }
    }

    public boolean saveEvent(EventCreationDTO event) {
        if (!eventService.existsByID(event.getName())) {
            eventService.save(convertToEvent(event));
            return true;
        } else {
            return false;
        }
    }

    public List<EventDTO> filterType(EventTypeDTO eventTypeDTO, int amount) {
        List<EventDTO> result = new ArrayList<>();

        for (Event event : eventService
                .filter(eventTypeDTO.getEventType(), amount)) {
            result.add(convertToEventDTO(event));
        }
        return result;
    }


    public List<EventDTO> search(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Empty query");
        }

        String[] queryWords = query.split(" ");

        Set<Event>[] seperateQueryResults = new HashSet[queryWords.length];

        for (int i = 0; i < queryWords.length; i++) {
            seperateQueryResults[i] = eventService.search(queryWords[i]);
        }

        Set<Event> intersection = new HashSet<>(seperateQueryResults[0]);

        for (int i = 0; i < queryWords.length; i++) {
            intersection.retainAll(seperateQueryResults[i]);
        }

        List<EventDTO> result = new ArrayList<>();

        for (Event event : intersection) {
            result.add(convertToEventDTO(event));
        }
        return result;
    }

    @RequestMapping("/events")  // Bonus REST
    public List<EventDTO> getLastN(@RequestParam("n") int n) {
        List<EventDTO> result = new ArrayList<>();

        for (Event event : eventService.newestN(n)) {
            result.add(convertToEventDTO(event));
        }
        return result;
    }


    @RequestMapping("/")
    public String homepage(Model model, HttpServletRequest request){
        model.addAttribute("topThree", topThree());
        String query = request.getParameter("q");
        if (query == null) {
            model.addAttribute("topTwenty", newestTwenty());
        } else {
            List<Event> events = eventService.filter(query, 20);
            model.addAttribute("topTwenty", events);
        }
        model.addAttribute("allTypes", getAllEventsTypes());
        return "index";
    }


    @RequestMapping("/createEvent")
    public String createNewEvent(Model model, Boolean success, Boolean pastDate){
        model.addAttribute("allTypes", getAllEventsTypes());
        model.addAttribute("topThree", topThree());
        if (success!=null&&!success) {
            String errorMessage = "Eventname schon vergeben!";
            model.addAttribute("error", errorMessage);
            return "CreateEvent";
        } else if (pastDate!=null&&pastDate) {
            String errorMessage = "Datum liegt in der Vergangenheit!";
            model.addAttribute("error", errorMessage);
            return "CreateEvent";
        }
        model.addAttribute("createdEvent", new EventCreationDTO());
        return "CreateEvent";
    }


    @RequestMapping("/topTwenty")
    public String topTwenty(Model model){
        model.addAttribute("topThree", topThree());
        model.addAttribute("topTwenty", topTwenty());
        return "TopTwenty";
    }


    @RequestMapping("/detail")
    public String detail(Model model, HttpServletRequest request){
        model.addAttribute("topThree", topThree());
        String eventName = request.getParameter("name");
        Event event = eventService.find(eventName);
        model.addAttribute("event", event);

        //cookies
        boolean cookieLike = false;
        boolean cookieDislike = false;

        Cookie[] cookies = request.getCookies();

        if(cookies!= null){
            for(Cookie c:cookies){
                System.out.println(c.getName());
                System.out.println(c.getValue());
                System.out.println("----");

                if(c.getName().equals(eventName + "Like") && c.getValue().equals("true")){
                    cookieLike = true;
                }

                if(c.getName().equals(eventName + "Dislike") && c.getValue().equals("true")){
                    cookieDislike = true;
                }
            }
        }
        model.addAttribute("cookieLike", cookieLike);
        model.addAttribute("cookieDislike", cookieDislike);

        //weather
        WeatherAPI weatherAPI = new WeatherAPI();
        WeatherAPI.WeatherForecast[] weather = {};
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        boolean weatherFound = false;
        if(event.getLocation() != null && event.getLocation().length() != 0){
            try {
                weather = weatherAPI.getWeatherByName(event.getLocation());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                weather = weatherAPI.getWeatherByLatLng(event.getLatitude(), event.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        for(WeatherAPI.WeatherForecast w : weather){
            if(w.getDate().equals(format.format(event.getDate()))){
                String weatherString = w.getWeatherTextDe() + ", " + w.getTempMin() + " °C bis " + w.getTempMax() + " °C";
                model.addAttribute("weather", weatherString);
                weatherFound = true;
            }
        }
        if(!weatherFound){
            model.addAttribute("weather", "Keine Wetterdaten gefunden");
        }

        return "EventDetail";
    }


    @RequestMapping("/search")
    public String searching(Model model, HttpServletRequest request){
        model.addAttribute("topThree", topThree());
        String query = request.getParameter("q");
        List<EventDTO> events = search(query);
        model.addAttribute("events", events);
        model.addAttribute("query", query);
        return "SearchingEvent";
    }

    @RequestMapping(value="/detail", method=RequestMethod.POST, params="action=dislike")
    public String dislike(Model model, HttpServletRequest request, HttpServletResponse response) {
        String eventName = request.getParameter("name");
        Event event = eventService.find(eventName);

        Cookie[] cookies = request.getCookies();
        boolean foundCookie = false;
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(eventName + "Dislike") && cookie.getValue().equals("true")) {
                    if (event != null) {
                        event.removeDislike();
                        eventService.update(event);
                    } else {
                        throw new IllegalArgumentException("Event does not exist.");
                    }
                    Cookie c = new Cookie(eventName + "Dislike", null);
                    c.setMaxAge(0);
                    response.addCookie(c);
                    foundCookie = true;
                }
            }
        }
        if(!foundCookie){
            if (event != null) {
                event.dislike();
                eventService.update(event);
            } else {
                throw new IllegalArgumentException("Event does not exist.");
            }
            response.addCookie(new Cookie(eventName + "Dislike", "true"));
        }

        return detail(model, request);
    }

    @RequestMapping(value="/detail", method=RequestMethod.POST, params="action=like")
    public String like(Model model, HttpServletRequest request, HttpServletResponse response) {
        String eventName = request.getParameter("name");
        Event event = eventService.find(eventName);

        Cookie[] cookies = request.getCookies();
        boolean foundCookie = false;
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(eventName + "Like") && cookie.getValue().equals("true")) {
                    if (event != null) {
                        event.removeLike();
                        eventService.update(event);
                    } else {
                        throw new IllegalArgumentException("Event does not exist.");
                    }
                    Cookie c = new Cookie(eventName + "Like", null);
                    c.setMaxAge(0);
                    response.addCookie(c);
                    foundCookie = true;
                }
            }
        }
        if(!foundCookie){
            if (event != null) {
                event.like();
                eventService.update(event);
            } else {
                throw new IllegalArgumentException("Event does not exist.");
            }
            response.addCookie(new Cookie(eventName + "Like", "true"));
        }
        /*
        try {
            String url = "http://localhost:8080/detail?name=" + eventName;
            URL urlForGetRequest = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
            connection.setRequestMethod("GET");
        }
        catch (Exception e){
            e.printStackTrace();
        }

         */
        return detail(model, request);
    }

    @RequestMapping(value="/createEvent", method=RequestMethod.POST, params="action=confirmCreate")
    public String createEvent(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(value = "createdEvent") EventCreationDTO eventCreationDTO,
            BindingResult bindingResult) {
        double lat = eventCreationDTO.getLatitude();
        double lng = eventCreationDTO.getLongitude();
        //String location = eventCreationDTO.getLocation();

        //check if user entered coords or city name
        if(lat != 0.0 && lng != 0.0){ // user entered coords
            String city = GeoAPI.latLngToCity(lat, lng);
            eventCreationDTO.setLocation(city);
        }

        Date entered = eventCreationDTO.getDate();
        Date today = new Date();
        boolean pastDate = entered.before(today);
        if(pastDate) {
            return createNewEvent(model, true, pastDate);
        }
        boolean success = saveEvent(eventCreationDTO);
        if (!success) {
            return createNewEvent(model, success, false);
        }
        return homepage(model, request);
    }
}
