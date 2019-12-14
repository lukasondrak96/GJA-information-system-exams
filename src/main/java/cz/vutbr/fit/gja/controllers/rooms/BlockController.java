package cz.vutbr.fit.gja.controllers.rooms;

import cz.vutbr.fit.gja.dto.BlocksCreationDto;
import cz.vutbr.fit.gja.models.Block;
import cz.vutbr.fit.gja.models.Room;
import cz.vutbr.fit.gja.repositories.BlockRepository;
import cz.vutbr.fit.gja.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BlockController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    BlockRepository blockRepository;

    @PostMapping("/logged/rooms/new_room/create")
    public ModelAndView createNewRoom(@Valid BlocksCreationDto blocks, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();
        // Check for the validations
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("bindingResult", bindingResult);
            modelAndView.addObject("blocks", new Room());
            modelAndView.setViewName("pages/logged/new_room");
            modelAndView.addObject("room", blocks.getRoomReference());
            modelAndView.addObject("blocks", blocks);
        } else {
            Room room = blocks.getRoomReference();
            roomRepository.save(room);

            for (List<Block> blockRow : blocks.getBlocks()) {
                for (Block blockInRow : blockRow) {
                    blockRepository.save(blockInRow);
                }
            }
            for (int i = 0; i < room.getNumberOfRows(); i++) {
                for (int j = 0; j < room.getNumberOfColumns(); j++) {

                }
            }

            modelAndView.addObject("successMessage", "Místnost " + room.getRoomNumber() + " byla úspěšně vytvořena.");
            modelAndView.addObject("room", room);
            modelAndView.addObject("blocks", blocks);
            modelAndView.setViewName("pages/logged/new_room");
        }
        return modelAndView;
    }
}
