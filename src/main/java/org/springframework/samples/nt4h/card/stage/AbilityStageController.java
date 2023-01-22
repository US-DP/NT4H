package org.springframework.samples.nt4h.card.stage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Los pisbles escenarios son:
 * - Campo de batalla.
 * - Lágrimas de Aradiel.
 * - Lodazal de Kalern.
 * - Mercado de Lötharion.
 * - Montañas de Ur.
 * - Pantano Umbrío.
 * - Planicie de Skaàrg.
 * - Portal de Ulthar.
 * - Puerta de Eqùe.
 * - Ruinas de Brunmar.
 * - Yacimineto de Jade.
 * - Yermo de Cémenmar.
 */
@Controller
@RequestMapping("/abilities/stages")
public class AbilityStageController {


    // Campo de battala.
    @GetMapping("/battlefield")
    public String battlefield() {
        return "abilities/stages/battlefield";
    }

    // Lágrimas de Aradiel.
    @GetMapping("/tearsOfAradiel")
    public String tearsOfAradiel() {
        return "abilities/stages/tearsOfAradiel";
    }

    // Lodazal de Kalern.
    @GetMapping("/swampOfKalern")
    public String swampOfKalern() {
        return "abilities/stages/swampOfKalern";
    }

    // Mercado de Lötharion.
    @GetMapping("/marketOfLotharion")
    public String marketOfLotharion() {
        return "abilities/stages/marketOfLotharion";
    }

    // Montañas de Ur.
    @GetMapping("/mountainsOfUr")
    public String mountainsOfUr() {
        return "abilities/stages/mountainsOfUr";
    }

    // Pantano Umbrío.
    @GetMapping("/shadowySwamp")
    public String shadowySwamp() {
        return "abilities/stages/shadowySwamp";
    }

    // Planicie de Skaàrg.
    @GetMapping("/plainOfSkaarg")
    public String plainOfSkaarg() {
        return "abilities/stages/plainOfSkaarg";
    }

    // Portal de Ulthar.
    @GetMapping("/portalOfUlthar")
    public String portalOfUlthar() {
        return "abilities/stages/portalOfUlthar";
    }

    // Puerta de Eqùe.
    @GetMapping("/gateOfEqe")
    public String gateOfEqe() {
        return "abilities/stages/gateOfEqe";
    }

    // Ruinas de Brunmar.
    @GetMapping("/ruinsOfBrunmar")
    public String ruinsOfBrunmar() {
        return "abilities/stages/ruinsOfBrunmar";
    }

    // Yacimineto de Jade.
    @GetMapping("/jadeMine")
    public String jadeMine() {
        return "abilities/stages/jadeMine";
    }

    // Yermo de Cémenmar.
    @GetMapping("/wastelandOfCemenmar")
    public String wastelandOfCemenmar() {
        return "abilities/stages/wastelandOfCemenmar";
    }
}
