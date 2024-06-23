package food_management.demo.web.controller;


import food_management.demo.persistance.entity.PedidoEntity;
import food_management.demo.persistance.repository.PedidoRepository;
import food_management.demo.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/whatsapp")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private TwilioService twilioService;

    private Map<String, String> menu;

    public PedidoController() {
        menu = new HashMap<>();
        menu.put("1", "Milanesas");
        menu.put("2", "Pizza");
        menu.put("3", "Pasta");
        // Agrega más opciones de menú aquí
    }

    @PostMapping
    public void handleIncomingMessage(@RequestParam("Body") String body, @RequestParam("From") String from) {
        String incomingMessage = body.trim();
        String responseMessage;

        if (menu.containsKey(incomingMessage)) {
            String item = menu.get(incomingMessage);
            PedidoEntity pedido = new PedidoEntity();
            pedido.setFromNumber(from);
            pedido.setItem(item);
            pedidoRepository.save(pedido);

            responseMessage = "Has pedido: " + item + ". Tu pedido está en proceso.";
        } else {
            StringBuilder menuMessage = new StringBuilder("Opción inválida. Por favor, selecciona una opción del menú: \n");
            menu.forEach((key, value) -> menuMessage.append(key).append(". ").append(value).append("\n"));
            responseMessage = menuMessage.toString();
        }

        twilioService.sendMessage(from, responseMessage);
    }
}