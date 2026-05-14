package com.spring.pizzaspring.component;

import com.spring.pizzaspring.dto.OrdinePizzaDTO;
import com.spring.pizzaspring.model.Ordine;
import com.spring.pizzaspring.model.OrdinePizza;
import com.spring.pizzaspring.model.Pizza;
import com.spring.pizzaspring.repository.OrdinePizzaRepository;
import com.spring.pizzaspring.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaveOrdiniPizzaComponent {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OrdinePizzaRepository ordinePizzaRepository;

    public void saveOrdiniPizza(Ordine ordine, List<OrdinePizzaDTO> pizze) {
        for (OrdinePizzaDTO OPD : pizze) {
            OrdinePizza newOP = new OrdinePizza();
            Pizza pizza = pizzaRepository.findById(OPD.getIdPizza())
                    .orElseThrow(() -> new RuntimeException("Pizza non trovata"));

            newOP.setPizza(pizza);
            newOP.setQuantita(OPD.getQuantita());
            newOP.setOrdine(ordine);

            ordinePizzaRepository.save(newOP);
        }
    }
}
