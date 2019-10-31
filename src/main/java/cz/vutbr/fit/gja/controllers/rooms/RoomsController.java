package cz.vutbr.fit.gja.controllers.rooms;

import cz.vutbr.fit.gja.Exceptions.RoomNotFoundException;
import cz.vutbr.fit.gja.models.Room;
import cz.vutbr.fit.gja.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoomsController {

    @Autowired
    RoomRepository roomRepository;

    @GetMapping("/rooms")
    public ModelAndView rooms() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/rooms");
        modelAndView.addObject("rooms", roomRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/logged/rooms")
    public ModelAndView loggedRooms() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/rooms");
        modelAndView.addObject("rooms", roomRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/logged/rooms/{id}")
    public ModelAndView loggedRoomsRoomId(@PathVariable(value = "id") String roomId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/room");
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("číslem", roomId));
        modelAndView.addObject("room", room);
        return modelAndView;
    }

    @GetMapping("/rooms/{id}")
    public ModelAndView roomsRoomId(@PathVariable(value = "id") String roomId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/room");
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("číslem", roomId));
        modelAndView.addObject("room", room);
        return modelAndView;
    }
}
